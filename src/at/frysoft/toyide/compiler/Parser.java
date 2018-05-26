package at.frysoft.toyide.compiler;

import at.frysoft.toyide.compiler.statement.Statement;

import java.util.Vector;

/**
 * ${FILE_NAME}
 * <p>
 * Created on : 26.05.2018
 * Last update: 26.05.2018
 * <p>
 * Contributors:
 * Stefan
 */
public class Parser {

    private static String[] getArgs(String line) {
        line = line.replace('\t', ' ');

        int commentStart = line.indexOf(';');
        if(commentStart != -1)
            line = line.substring(0, commentStart);

        String[] r = line.split(" ");
        Vector<String> args = new Vector<>(r.length);

        for(String s : r) {
            if(!s.isEmpty())
                args.add(s);
        }

        return args.toArray(new String[0]);
    }

    public static Statement getStatement(String... args) throws SyntaxException {
        boolean link = false;
        Statement stmt = Statement.create(args[0], false);

        if(stmt == null) {
            if(args.length == 1)
                throw new SyntaxException("Unknown Instruction!");

            link = true;
            stmt = Statement.create(args[1], true);

            if (stmt == null)
                throw new SyntaxException("Unknown Instruction!");
        }

        if((stmt.getParameterCount() + 1) != args.length)
            throw new SyntaxException("Invalid Parameter Count!");

        int i = 0;
        String[] params = new String[args.length - 1];

        if(link) {
            params[i] = args[i];
            ++i;
        }

        while(i < params.length) {
            params[i] = args[i + 1];
            ++i;
        }

        stmt.readParams(params);
        return stmt;
    }

    public static Statement parseLine(String line) throws SyntaxException {

        if(line.isEmpty())
            return null;

        String[] args = getArgs(line);

        if(args.length == 0)
            return null;

        return getStatement(args);
    }

}
