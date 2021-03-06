package at.frysoft.toyide.compiler;

import at.frysoft.toyide.compiler.statement.Address;
import at.frysoft.toyide.compiler.statement.Statement;

import java.util.HashMap;

/**
 * Created by Stefan on 26.05.2018.
 */
public class Linker {

    private HashMap<String, Statement> linkedStatements;

    public Linker() {
        linkedStatements = new HashMap<>();
    }

    public void add(Statement stmt) {
        linkedStatements.put(stmt.getLink(), stmt);
    }

    public void link(Statement stmt) throws LinkerException {
        Address addressParam = (Address) stmt.getAddressParam();

        if(!linkedStatements.containsKey(addressParam.getString()))
            throw new LinkerException(addressParam.getString());

        addressParam.setAddress(linkedStatements.get(addressParam.getString()).getAddress());
    }

}
