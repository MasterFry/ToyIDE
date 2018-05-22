package at.frysoft.toyide.compiler.tuple;

import at.frysoft.toyide.toy.Instruction;

/**
 * Created by Stefan on 19.05.2018.
 */
public class Tuple {

    private int address;
    private int codeLine;

    private Instruction instruction;

    private String link;
    private String linkTo;

    public Tuple(Instruction instruction, String link, String linkTo) {
        this.instruction = instruction;
        this.link = link;
        this.linkTo = linkTo;
        address = -1;
    }

    public void link(Tuple tuple) {
        instruction.setImm(tuple.getAddress(), true);
    }

    public void setAddress(int address) {
        this.address = address;
    }

    public void setCodeLine(int codeLine) {
        this.codeLine = codeLine;
    }

    public boolean isCompilerInstruction() {
        return (instruction instanceof CompilerInstruction);
    }

    public int getCodeLine() {
        return codeLine;
    }

    public int getAddress() {
        return address;
    }

    public Instruction getInstruction() {
        return instruction;
    }

    public String getLink() {
        return link;
    }

    public String getLinkTo() {
        return linkTo;
    }

}
