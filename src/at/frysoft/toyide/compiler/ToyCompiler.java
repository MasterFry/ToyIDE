package at.frysoft.toyide.compiler;

import at.frysoft.toyide.Log;
import at.frysoft.toyide.Strings;
import at.frysoft.toyide.Utils;
import at.frysoft.toyide.compiler.tuple.Dup;
import at.frysoft.toyide.compiler.tuple.Dw;
import at.frysoft.toyide.compiler.tuple.Org;
import at.frysoft.toyide.compiler.tuple.Tuple;

import java.io.*;
import java.util.Vector;

import static at.frysoft.toyide.Strings.COMPILER_COMPILING_FILE;

/**
 * Created by Stefan on 19.05.2018.
 */
public class ToyCompiler {

    private Vector<Tuple> tuples;

    private Linker linker;

    public ToyCompiler() {
    }

    public boolean compile(File srcFile) {
        String src = srcFile.getAbsolutePath();

        Log.out.println(COMPILER_COMPILING_FILE + src);
        Log.out.setPrefix("  ");
        Log.err.setPrefix("  ");

        if(!srcFile.exists() || srcFile.isDirectory()) {
            Log.err.println(Strings.FILE_NOT_EXIST_OR_DIR);
            return false;
        }

        String dst = Utils.fileNameAsmToToy(src);
        if(dst == null)
            return false;

        File dstFile = new File(dst);
        if(dstFile.isDirectory()) {
            Log.err.println(Strings.COMPILER_OUTFILE_IS_DIR);
            return false;
        }

        if(!dstFile.getParentFile().exists()) {
            dstFile.getParentFile().mkdirs();
        }

        return compile(srcFile, dstFile);
    }

    public boolean compile(String src, String dst) {
        Log.out.println(COMPILER_COMPILING_FILE + src);
        Log.out.setPrefix("  ");
        Log.err.setPrefix("  ");

        File srcFile = new File(src);
        if(!srcFile.exists() || srcFile.isDirectory()) {
            Log.err.println(Strings.FILE_NOT_EXIST_OR_DIR);
            return false;
        }

        File dstFile = new File(dst);
        if(dstFile.isDirectory()) {
            Log.err.println(Strings.COMPILER_OUTFILE_IS_DIR);
            return false;
        }

        if(!dstFile.getParentFile().exists()) {
            dstFile.getParentFile().mkdirs();
        }

        return compile(srcFile, dstFile);
    }

    public boolean compile(File srcFile, File dstFile) {
        tuples = new Vector<>();
        linker = new Linker();
        boolean error = false;


        // Parse code lines into Tuples
        try {
            BufferedReader br = new BufferedReader(new FileReader(srcFile));

            int currentLine = 1;
            Tuple tuple = null;
            String line;

            while((line = br.readLine()) != null) {

                try {
                    tuple = Parser.parseLine(line);
                } catch (SyntaxException ex) {
                    Log.err.println(String.format(Strings.COMPILER_SYNTAX_ERROR, ex.getMessage()) +
                                    String.format(Strings.COMPILER_ON_LINE, currentLine, line));
                    error = true;
                }

                if(tuple != null) {
                    tuple.setCodeLine(currentLine);
                    tuples.add(tuple);

                    if(tuple.getLink() != null)
                        linker.addTuple(tuple);
                }

                ++currentLine;
            }

            br.close();

        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
            return false;

        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }

        if(error)
            return false;

        // Assign address locations to tuples
        int address = 0;
        for(Tuple tuple : tuples) {
            if (tuple.getInstruction() instanceof Org) {
                address = tuple.getInstruction().get();

            }else if (tuple.getInstruction() instanceof Dup) {
                address += tuple.getInstruction().get();

            }else {
                if(!(tuple.getInstruction() instanceof Dw) && address < 0x10)
                    address = 0x10;
                tuple.setAddress(address);
                ++address;
            }
        }

        // Insert addresses of symbolic links
        for(Tuple tuple : tuples) {
            if(tuple.getLinkTo() != null) {
                try{
                    linker.link(tuple);
                }catch (SymbolicLinkNotFoundException ex) {
                    Log.err.println(Strings.COMPILER_NO_SYMBOLIC_LINK +
                                    String.format(Strings.COMPILER_ON_LINE, tuple.getCodeLine(), ex.getMessage()));
                    error = true;
                }
            }
        }

        if(error)
            return false;

        // Write compiled code
        try {
            FileWriter fw = new FileWriter(dstFile);

            for(Tuple tuple : tuples) {

                if(tuple.getInstruction() instanceof Org) {
                    continue;

                } else if(tuple.getInstruction() instanceof Dup) {
                    for(int i = 0; i < tuple.getInstruction().get(); ++i) {
                        fw.write(String.format("%02X", i + tuple.getAddress()) + ": 0000\n");
                    }

                }else {
                    fw.write(String.format("%02X", tuple.getAddress()) + ": " + String.format("%04X", tuple.getInstruction().get()) + "\n");
                }

            }

            fw.close();

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        Log.out.setPrefix(null);
        Log.err.setPrefix(null);
        Log.out.println(Strings.COMPILER_FILE_COMPILED_TO + dstFile.getAbsolutePath());

        return true;
    }

}
