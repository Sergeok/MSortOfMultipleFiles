package config;

import sorter.IntegerSort;
import sorter.KWaySort;
import sorter.StringSort;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

public class AppConfigParser {

    enum DataType {
        INTEGER,
        STRING
    }

    private Boolean isAscDirectionSort = null;
    private DataType dataType = null;
    private final String outFilename;
    private String[] inputs;

    public AppConfigParser(String[] args) {
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-s":
                    isFieldSet(dataType, args[i]);
                    dataType = DataType.STRING;
                    break;
                case "-i":
                    isFieldSet(dataType, args[i]);
                    dataType = DataType.INTEGER;
                    break;
                case "-a":
                    isFieldSet(isAscDirectionSort, args[i]);
                    isAscDirectionSort = true;
                    break;
                case "-d":
                    isFieldSet(isAscDirectionSort, args[i]);
                    isAscDirectionSort = false;
                    break;
                default:
                    outFilename = args[i];
                    inputs = Arrays.copyOfRange(args, i + 1, args.length);
                    if (isAscDirectionSort == null) {
                        isAscDirectionSort = true;
                    }
                    checkInputsCorrect();
                    return;
            }
        }
		throw new RuntimeException("No file was passed as a parameter");
    }

    public KWaySort sorterInit() {
        if (dataType == null) {
            throw new RuntimeException("The data type has not been set");
        }

        if (dataType.equals(DataType.INTEGER)) {
            return new IntegerSort(inputs, outFilename, isAscDirectionSort);
        } else if (dataType.equals(DataType.STRING)) {
            return new StringSort(inputs, outFilename, isAscDirectionSort);
        } else {
            throw new RuntimeException("The data type '" + dataType.name() + "' cannot be processed");
        }
    }

    private void checkInputsCorrect() {
        List<String> correctInputs = new ArrayList<>();
        for (String input : inputs) {
            File file = new File(input);
            if (!file.exists() || file.isDirectory()) {
                Logger.getLogger("log")
                        .info("Input file '" + input + "' does not exist or is a directory");
            } else {
                correctInputs.add(input);
            }
        }

        inputs = correctInputs.toArray(new String[0]);
    }

    private void isFieldSet(Object field, String key) {
        if (field != null) {
            throw new RuntimeException("Keys with the same logic cannot be repeated. Repeating key: " + key);
        }
    }
}
