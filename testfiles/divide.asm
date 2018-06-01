QUOT    DW 0x0000
REM     DW 0x0000

        ORG 0x10
        LDA R1 0x01

        ; Prepare to call MULT
        LDA R2 0x20 ; Push the first param
        PUSH R2
        LDA R2 0x03 ; Push the second Param
        PUSH R2

        CALL DIV

        POP R2        ; Pop the return value
        ST R2 QUOT ; Store the return value
        POP R2        ; Clean up
        ST R2 REM ; Store the return value

        HLT

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; DIV
;; A function for dividing two integers, returning the quotient and the
;; remainder.
;; It is required that R1 == 1.
;; @param  [1]DD: dividend
;; @param  [2]DS: divisor
;; @return [1]QT: quotient
;; @return [2]RE: remainder
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
DIV     LDI R2 2 RE  ; Load a
        LDI R3 1 RE  ; Load b

        LDA R4 0x00  ; Reset R4

DIV_1   SUB R2 R2 R3
        BP R2 DIV_2
        BZ R0 DIV_E
DIV_2   ADD R4 R4 R1
        BZ R0 DIV_1

DIV_E   BZ R2 DIV_Z
        ADD R2 R2 R3
DIV_Z   STI R4 1 RE ; Store g
        STI R2 2 RE ; Store r
        RET
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;


