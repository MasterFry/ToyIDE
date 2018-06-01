package at.frysoft.toyide;

import at.frysoft.toyide.ressources.R;

/**
 * Created by Stefan on 20.05.2018.
 */
public class Utils {

    public static int hexCharToInt(char c) {
        switch(c) {
            case '0': return 0x0;
            case '1': return 0x1;
            case '2': return 0x2;
            case '3': return 0x3;
            case '4': return 0x4;
            case '5': return 0x5;
            case '6': return 0x6;
            case '7': return 0x7;
            case '8': return 0x8;
            case '9': return 0x9;
            case 'A':
            case 'a': return 0xA;
            case 'B':
            case 'b': return 0xB;
            case 'C':
            case 'c': return 0xC;
            case 'D':
            case 'd': return 0xD;
            case 'E':
            case 'e': return 0xE;
            case 'F':
            case 'f': return 0xF;
            default: return -1;
        }
    }

    public static String fileNameAsmToToy(String asmFileName) {
        if(!asmFileName.endsWith(".asm")) {
            Log.err.println(R.strings.file.NOT_ASM_FILE);
            return null;
        }
        return asmFileName.substring(0, asmFileName.length() - 4) + ".toy";
    }

}
