package at.frysoft.toyide.compiler.tuple;

import at.frysoft.toyide.compiler.tuple.Tuple;

/**
 * Created by Stefan on 19.05.2018.
 */
public class Link {

    private Tuple tuple;

    private final String link;

    public Link(String link) {
        this.link = link;
        tuple = null;
    }

    public String getLink() {
        return link;
    }

    public Tuple getTuple() {
        return tuple;
    }

    public void link(Tuple tuple) {
        this.tuple = tuple;
    }

}
