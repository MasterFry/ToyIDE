package at.frysoft.toyide;

import at.frysoft.toyide.ui.Console;

/**
 * Created by Stefan on 20.05.2018.
 */
public class Log {

    public static final boolean SYSTEM_OUTPUT = false;

    private static Console console;

    public static final Printer out;
    public static final Printer err;

    static {
        if(SYSTEM_OUTPUT) {
            out = new SystemOut();
            err = new SystemErr();
        }else {
            out = new ConsoleOut();
            err = new ConsoleErr();
        }
    }

    static void setConsole(Console console) {
        Log.console = console;
    }

    public static abstract class Printer {

        private String prefix;

        protected Printer() {
            prefix = "";
        }

        public void setPrefix(String prefix) {
            if(prefix == null)
                this.prefix = "";
            else
                this.prefix = prefix;
        }

        public void println() {
            write("\n");
        }

        public void println(String string) {
            write(prefix + string + "\n");
        }

        public void print(String string) {
            write(prefix + string);
        }

        protected abstract void write(String string);

    }

    private static class ConsoleOut extends Printer {

        protected ConsoleOut() {
        }

        @Override
        protected void write(String string) {
            if(console != null) {
                console.addText(string, null);
            }
        }

    }

    private static class ConsoleErr extends Printer {

        protected ConsoleErr() {
        }

        @Override
        protected void write(String string) {
            if(console != null) {
                console.addText(string, console.styleColorRed);
            }
        }

    }

    private static class SystemOut extends Printer {

        protected SystemOut() {
        }

        @Override
        protected void write(String string) {
            System.out.print(string);
            System.out.flush();
        }

    }

    private static class SystemErr extends Printer {

        protected SystemErr() {
        }

        @Override
        protected void write(String string) {
            System.err.print(string);
            System.err.flush();
        }

    }

}
