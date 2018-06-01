PRODUCT DW 0x00

        ORG 0x10
        LDA R1 0x01

        ; Prepare to call MULT
        LDA R2 0x20 ; Push the first param
        PUSH R2
        LDA R2 0x03 ; Push the second Param
        PUSH R2

        CALL MULT

        POP RA        ; Pop the return value
        ST RA PRODUCT ; Store the return value
        POP R2        ; Clean up

        HLT

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; MULT
;; A function for multiplication 2 positive integers.
;; It is faster if the second factor is the larger one.
;; It is required that R1 == 1.
;; @param  [1]F1: first factor
;; @param  [2]F2: second factor
;; @return [1]PR: product of the two factors
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
MULT    LDI R2 1 RE  ; Load F1
        LDI R3 2 RE  ; Load F2

        LDA R4 0x00  ; Reset R4

        AND R6 R3 R1 ; Check the first bit of F2
        SHR R3 R3 R1 ; and divide F2 by two
        BZ R6 MULT_1
        ADD R4 R4 R2 ; Add F1 to the product if the bit was 1

MULT_1  BZ R3 MULT_2 ; Stop if F2 is zero
        AND R6 R3 R1 ; Check the first bit of F2
        SHR R3 R3 R1 ; and divide F2 by two
        SHL R2 R2 R1 ; Multiply F1 by two
        BZ R6 MULT_1

        ADD R4 R4 R2 ; and add it to the product if the bit was 1
        BP R3 MULT_1 ; Repeat if F2 is not zero

MULT_2  STI R4 1 RE  ; Store the product in PR
        RET
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;


