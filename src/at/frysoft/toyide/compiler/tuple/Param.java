package at.frysoft.toyide.compiler.tuple;

/**
 * Created by Stefan on 20.05.2018.
 */
public abstract class Param {

    public abstract String getString();

    public static class Integer extends Param {

        public final int value;

        public Integer(int value) {
            this.value = value;
        }

        @Override
        public String getString() {
            return "" + value;
        }

    }

    public static class Register extends Param {

        public final int value;

        public Register(int value) {
            this.value = value;
        }

        @Override
        public String getString() {
            return "R" + value;
        }

    }

    public static class SymbolicLink extends Param {

        public final String link;

        public SymbolicLink(String link) {
            this.link = link;
        }

        @Override
        public String getString() {
            return link;
        }

    }

}
