package at.frysoft.toyide.toy;

import at.frysoft.toyide.Log;
import at.frysoft.toyide.Strings;

import java.io.*;

public abstract class CPU {

    protected PC pc;
    protected Memory memory;
    protected Memory register;

    private Instruction currentInstruction;

    private int executedInstructions;

    public CPU() {
        memory = new Memory(256);
        register = new Memory(16);
        pc = new PC(0x10);
        currentInstruction = new Instruction();
        reset();
    }

    protected abstract void execute(Instruction instr);

    public void reset() {
        memory.reset();
        register.reset();
        pc.reset();
        executedInstructions = 0;
    }

    public void load(String fileName) {
        load(new File(fileName));
    }

    public boolean load(File file) {
        reset();

        if(!file.exists() || file.isDirectory()) {
            Log.err.println(Strings.FILE_NOT_EXIST_OR_DIR);
            return false;
        }

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));

            int addr;
            int data;
            String line;
            String[] r;

            Log.out.println("Reading File:");
            while((line = br.readLine()) != null) {
                r = line.split(": ");

                addr = Integer.parseInt(r[0], 16);
                data = Integer.parseInt(r[1], 16);
                Log.out.println(String.format("%02x", addr) + ": " + String.format("%04x", data));
                memory.write(addr, data);
            }
            Log.out.println("Done reading File.");

            br.close();
            return true;

        } catch (FileNotFoundException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean step() {
        currentInstruction.set(memory.read(pc.get()));
        pc.increment();

        execute(currentInstruction);
        ++executedInstructions;

        return !currentInstruction.isHalt();
    }

    public void run() {
        while(step());
    }

    public int getExecutedInstructions() {
        return executedInstructions;
    }

    private void printMemoryLine(Memory mem, int start, int end) {
        Log.out.print(String.format("%02x", start) + ": ");
        for(int i = start; i < end; ++i)
            Log.out.print(String.format("%04x", mem.read(i)) + " ");
        Log.out.println();
    }

    public void print(int memCount) {
        Log.out.println();
        Log.out.println("***********************************************************************************");
        Log.out.print("PC: " + String.format("%02x", pc.getPrev()) + " => ");
        Log.out.println("Instruction: " + currentInstruction.getName() + " = " +
                        String.format("%04x", currentInstruction.get()));

        Log.out.println("-----------------------------------------------------------------------------------");
        Log.out.println("Register:");
        printMemoryLine(register, 0, 16);

        Log.out.println("-----------------------------------------------------------------------------------");
        Log.out.println("Memory:");
        for(int i = 0; i < memCount; i += 16)
            printMemoryLine(memory, i, i + 16);
        Log.out.println("***********************************************************************************");
    }

    public void print() {
        print(0xFF);
    }

}
