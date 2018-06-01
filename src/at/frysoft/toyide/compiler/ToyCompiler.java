package at.frysoft.toyide.compiler;

import at.frysoft.toyide.Log;
import at.frysoft.toyide.Utils;
import at.frysoft.toyide.compiler.statement.OPC;
import at.frysoft.toyide.compiler.statement.Statement;
import at.frysoft.toyide.ressources.R;

import java.io.*;
import java.util.Vector;

/**
 * Created on : 26.05.2018
 * Last update: 26.05.2018
 * <p>
 * Contributors:
 * Stefan
 */
public class ToyCompiler {

    private static void setPrefix() {
        Log.out.setPrefix("[ToyCompiler]: ");
        Log.err.setPrefix("[ToyCompiler]: ");
    }

    private static void resetPrefix() {
        Log.out.setPrefix(null);
        Log.err.setPrefix(null);
    }

    public static boolean compile(File srcFile) {
        String src = srcFile.getAbsolutePath();

        setPrefix();
        Log.out.println(R.strings.compiler.COMPILING_FILE(srcFile.getName()));

        if(!srcFile.exists() || srcFile.isDirectory()) {
            Log.err.println(R.strings.file.NOT_EXIST_OR_IS_DIR);
            return false;
        }

        String dst = Utils.fileNameAsmToToy(src);
        if(dst == null)
            return false;

        File dstFile = new File(dst);
        if(dstFile.isDirectory()) {
            Log.err.println(R.strings.file.OUT_IS_DIR);
            return false;
        }

        if(!dstFile.getParentFile().exists()) {
            dstFile.getParentFile().mkdirs();
        }

        boolean result = compile(srcFile, dstFile);
        resetPrefix();
        return result;
    }

    public static boolean compile(String src, String dst) {
        setPrefix();
        Log.out.println(R.strings.compiler.COMPILING_FILE(src));

        File srcFile = new File(src);
        if(!srcFile.exists() || srcFile.isDirectory()) {
            Log.err.println(R.strings.file.NOT_EXIST_OR_IS_DIR);
            return false;
        }

        File dstFile = new File(dst);
        if(dstFile.isDirectory()) {
            Log.err.println(R.strings.file.OUT_IS_DIR);
            return false;
        }

        if(!dstFile.getParentFile().exists()) {
            dstFile.getParentFile().mkdirs();
        }

        boolean result = compile(srcFile, dstFile);
        resetPrefix();
        return result;
    }

    private static boolean compile(File srcFile, File dstFile) {
        Vector<Statement> statements = new Vector<>();
        Linker linker = new Linker();

        boolean error = false;
        int currentLine = 1;
        String line = "";

        // Read the file and compile a list of statements
        try {
            BufferedReader br = new BufferedReader(new FileReader(srcFile));
            Statement stmt;

            while((line = br.readLine()) != null) {
                stmt = Parser.parseLine(line);

                if(stmt != null) {
                    statements.add(stmt);
                    stmt.setLineIndex(currentLine);

                    if(stmt.getLink() != null)
                        linker.add(stmt);
                }

                ++currentLine;
            }

            br.close();

        } catch (IOException ex) {
            ex.printStackTrace();
            return false;

        } catch (SyntaxException ex) {
            Log.err.println(R.strings.compiler.SYNTAX_ERROR(ex.getMessage(), currentLine, line));
            error = true;
        }

        if(error)
            return false;

        // Assign addresses to statements
        int address = 0;
        OPC opc;
        for(Statement stmt : statements) {
            opc = stmt.getOPC();

            if(opc == OPC.ORG) {
                address = stmt.getParameter(0).getInt();

            }else if(opc == OPC.DUP) {
                stmt.setAddress(address);
                address += stmt.getParameter(1).getInt();

            }else {
                if(opc != OPC.DW && address < 0x10)
                    address = 0x10;
                stmt.setAddress(address);
                ++address;
            }
        }

        // Replace symbolic links with addresses
        for(Statement stmt : statements) {
            if(stmt.hasAddressParam()) {
                try {
                    linker.link(stmt);
                } catch (LinkerException ex) {
                    Log.err.println(R.strings.compiler.LINKER_ERROR(ex.getMessage(), stmt.getLineIndex(), line));
                }
            }
        }

        if(error)
            return false;

        // Write compiled code
        try {
            FileWriter fw = new FileWriter(dstFile);

            String str = "";
            for(Statement stmt : statements) {
                opc = stmt.getOPC();

                if(opc == OPC.DW) {
                    fw.write(String.format("%02X", stmt.getAddress()) + ": " +
                                     String.format("%04X", stmt.getParameter(1).getInt()) + '\n');
                    str = String.format("%02X", stmt.getAddress()) + ": " + String.format("%04X", stmt.getParameter(1).getInt()) + '\n';
                }else if(opc == OPC.DUP) {
                    for (int i = 0; i < stmt.getParameter(1).getInt(); ++i) {
                        fw.write(String.format("%02X", i + stmt.getAddress()) + ": 0000\n");
                        str = String.format("%02X", i + stmt.getAddress()) + ": 0000\n";
                    }

                }else if(opc != OPC.ORG) {
                    fw.write(String.format("%02X", stmt.getAddress()) + ": " + stmt.getBinaryString() + '\n');
                    str = String.format("%02X", stmt.getAddress()) + ": " + stmt.getBinaryString() + '\n';
                }

            }

            fw.close();

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        Log.out.println(R.strings.compiler.COMPILED_FILE(dstFile.getName()));

        return true;
    }

}
