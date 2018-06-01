package at.frysoft.toyide.ressources;

import at.frysoft.toyide.ressources.settings.Settings;

import java.io.File;

/**
 * Created on : 01.06.2018
 * Last update: 01.06.2018
 * <p>
 * Contributors:
 * Stefan
 */
public class R {

    public static final Settings settings = new Settings();
    
    public static class strings {

        public static final String OPEN = "Open";
        public static final String SAVE = "Save";

        public static class toy {

            public static final String[] REGISTER_NAMES = {
                    "R0", "R1", "R2", "R3", "R4", "R5", "R6", "R7",
                    "R8", "R9", "RA", "RB", "RC", "RD", "RE", "RF"
            };

            public static class instruction {

                public static final String HLT = "HLT";
                public static final String ADD = "ADD";
                public static final String SUB = "SUB";
                public static final String AND = "AND";
                public static final String XOR = "XOR";
                public static final String SHL = "SHL";
                public static final String SHR = "SHR";
                public static final String LDA = "LDA";
                public static final String LD  = "LD";
                public static final String ST  = "ST";
                public static final String LDI = "LDI";
                public static final String STI = "STI";
                public static final String BZ  = "BZ";
                public static final String BP  = "BP";
                public static final String JR  = "JR";
                public static final String JL  = "JL";

                public static final String PUSH = "PUSH";
                public static final String POP  = "POP";
                public static final String CALL = "CALL";
                public static final String RET  = "RET";

            } // strings.toy.instruction

        } // strings.toy
        
        public static class compiler {

            public static final String COMPILER_NO_SYMBOLIC_LINK  = "Symbolic link not found!";

            public static final String ORG_MUST_NOT_HAVE_LINK = "ORG must not have a symbolic link!";
            public static final String DW_MUST_HAVE_LINK = "DW must have a symbolic link!";
            public static final String DUP_MUST_HAVE_LINK = "DW must have a symbolic link!";

            public static String COMPILING_FILE(String fileName) {
                return "Compiling file " + fileName;
            } // COMPILING_FILE

            public static String COMPILED_FILE(String fileName) {
                return "Successfully compiled to " + fileName;
            } // COMPILED_FILE

            public static String SYNTAX_ERROR(String errorMsg, int index, String line) {
                return String.format("Syntax error: %s\n\tOn line: %d: %s", errorMsg, index, line);
            } // SYNTAX_ERROR

            public static String LINKER_ERROR(String errorMsg, int index, String line) {
                return String.format("Linker error: %s\n\tOn line: %d: %s", errorMsg, index, line);
            } // LINKER_ERROR
            
            public static class instruction {
                
                public static final String ORG = "ORG";
                public static final String DW  = "DW";
                public static final String DUP = "DUP";
                
            } // strings.compiler.instruction


        } // strings.compiler

        public static class file {

            public static final String NOT_EXIST_OR_IS_DIR = "File does not exist or is a directory.";
            public static final String NOT_ASM_FILE = "File ending is not \".asm\".";
            public static final String OUT_IS_DIR  = "Specified destination file is already a directory file.";

            public static String LOADED(File file) {
                return "File loaded: " + file.getName();
            }

            public static String SAVED(File file) {
                return "File saved: " + file.getName();
            }

        } // strings.file
        
    } // strings
    
}
