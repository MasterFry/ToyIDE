package at.frysoft.toyide.toy;

import at.frysoft.toyide.ressources.R;
import com.google.gson.stream.JsonReader;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

/**
 * Created on : 01.06.2018
 * Last update: 01.06.2018
 * <p>
 * Contributors:
 * Stefan
 */
public class ConfigReader {

    private String name = null;

    private String lastName = null;
    private JsonReader reader = null;

    public ConfigReader() {
    }

    public void loadConfig(String fileName) throws ConfigFileException, IOException {
        File file = new File(fileName);

        reader = new JsonReader(new FileReader(file));
        reader.beginObject(); // Begin JSON

        name = nextString(R.strings.config.NAME);

        int bitWidth = nextInt(R.strings.config.BIT_WIDTH);
        Memory memory = new Memory(bitWidth, nextInt(R.strings.config.MEMORY_SIZE));

        int pcStart = nextInt(R.strings.config.PC_START);

        checkName(R.strings.config.CPU);
        reader.beginObject(); // Begin CPU

        Memory register = new Memory(bitWidth, nextInt(R.strings.config.REGISTER_COUNT));

        checkName(R.strings.config.INSTRUCTION);
        reader.beginObject(); // Begin Instruction

        Vector<DataRange> dataRanges = new Vector<>();
        while(reader.hasNext())
            dataRanges.add(nextDataRange());

        reader.endObject(); // End Instruction

        checkName(R.strings.config.INSTRUCTION_SET);
        reader.beginObject(); // Begin Instruction Set

        Vector<NI> instructions = new Vector<>();
        while(reader.hasNext())
            instructions.add(nextInstruction(dataRanges));

        reader.endObject(); // End Instruction Set

        reader.endObject(); // End CPU

        reader.endObject(); // End JSON
        reader.close();
    }

    public void checkName(String name) throws ConfigFileException, IOException {
        if(!reader.nextName().equals(name))
            if(lastName == null)
                throw new ConfigFileException(name + " expected as first value!");
            else
                throw new ConfigFileException(name + " expected after " + lastName + '!');
        lastName = name;
    }

    public int nextInt(String name) throws ConfigFileException, IOException {
        checkName(name);
        return reader.nextInt();
    }

    public String nextString(String name) throws ConfigFileException, IOException {
        checkName(name);
        return reader.nextString();
    }

    public DataRange nextDataRange() throws IOException {
        String name = reader.nextName();
        reader.beginArray();
        DataRange dataRange = new DataRange(name, reader.nextInt(), reader.nextInt());
        reader.endArray();
        return dataRange;
    }

    public NI nextInstruction(Vector<DataRange> dataRanges) {

    }

    private Vector<DataRange> readInstructionDefinition(JsonReader reader) throws IOException {
        Vector<DataRange> dataRanges = new Vector<>();
        String name;
        int start, end;

        while(reader.hasNext()) {
            name = reader.nextName();

            reader.beginArray();
            start = reader.nextInt();
            end = reader.nextInt();
            reader.endArray();

            dataRanges.add(new DataRange(name, start, end));
        }

        return dataRanges;
    }

    private static class DataRange {

        private final String name;
        private final int start;
        private final int end;

        private DataRange(String name, int start, int end) {
            this.name = name;
            this.start = start;
            this.end = end;
        }

    }

}
