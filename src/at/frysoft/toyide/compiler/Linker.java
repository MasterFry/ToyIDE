package at.frysoft.toyide.compiler;

import at.frysoft.toyide.compiler.tuple.Link;
import at.frysoft.toyide.compiler.tuple.Tuple;

import java.util.HashMap;
import java.util.Vector;

/**
 * Created by Stefan on 19.05.2018.
 */
public class Linker {

    private HashMap<String, Tuple> linkedTuples;

    public Linker() {
        linkedTuples = new HashMap<>();
    }

    public void addTuple(Tuple tuple) {
        linkedTuples.put(tuple.getLink(), tuple);
    }

    public void link(Tuple tuple) throws SymbolicLinkNotFoundException {
        if(!linkedTuples.containsKey(tuple.getLinkTo()))
            throw new SymbolicLinkNotFoundException(tuple.getLinkTo());

        tuple.link(linkedTuples.get(tuple.getLinkTo()));
    }

}
