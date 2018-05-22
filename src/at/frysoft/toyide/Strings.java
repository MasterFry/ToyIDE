package at.frysoft.toyide;

/**
 * Created by Stefan on 21.05.2018.
 */
public class Strings {

    public static final String INSTRUCTION_HLT = "HLT";
    public static final String INSTRUCTION_ADD = "ADD";
    public static final String INSTRUCTION_SUB = "SUB";
    public static final String INSTRUCTION_AND = "AND";
    public static final String INSTRUCTION_XOR = "XOR";
    public static final String INSTRUCTION_SHL = "SHL";
    public static final String INSTRUCTION_SHR = "SHR";
    public static final String INSTRUCTION_LDA = "LDA";
    public static final String INSTRUCTION_LD  = "LD";
    public static final String INSTRUCTION_ST  = "ST";
    public static final String INSTRUCTION_LDI = "LDI";
    public static final String INSTRUCTION_STI = "STI";
    public static final String INSTRUCTION_BZ  = "BZ";
    public static final String INSTRUCTION_BP  = "BP";
    public static final String INSTRUCTION_JR  = "JR";
    public static final String INSTRUCTION_JL  = "JL";

    public static final String COMPILER_INSTRUCTION_ORG = "ORG";
    public static final String COMPILER_INSTRUCTION_DW  = "DW";
    public static final String COMPILER_INSTRUCTION_DUP = "DUP";

    public static final String COMPILER_COMPILING_FILE  = "Compiling file ";
    public static final String COMPILER_SYNTAX_ERROR  = "Syntax error: %s";
    public static final String COMPILER_ON_LINE  = "\n\ton line: %d: %s";
    public static final String COMPILER_NO_SYMBOLIC_LINK  = "Symbolic link not found!";
    public static final String COMPILER_OUTFILE_IS_DIR  = "Specified destination file is already a directory file.";
    public static final String COMPILER_FILE_COMPILED_TO  = "Successfully compiled to ";

    public static final String FILE_NOT_EXIST_OR_DIR = "File does not exist or is a directory.";
    public static final String FILE_NOT_ASM_FILE = "File ending is not \".asm\".";
    public static final String FILE_X_LOADED = "File loaded: %s";
    public static final String FILE_X_SAVED = "File saved: %s";

    public static final String[] REGISTER_NAMES = {
            "R0", "R1", "R2", "R3", "R4", "R5", "R6", "R7",
            "R8", "R9", "RA", "RB", "RC", "RD", "RE", "RF"
    };
    
}
