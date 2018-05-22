VAL_A   DW 0x0001
VAL_B   DW 0x000F
SUM     DW 0x0000

        LD R1 VAL_A
        LD R2 VAL_B
        ADD R3 R1 R2
        ST R3 SUM
        HLT
