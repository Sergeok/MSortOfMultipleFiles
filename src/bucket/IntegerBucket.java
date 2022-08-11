package bucket;

import java.util.logging.Logger;

public class IntegerBucket extends Bucket {

    public IntegerBucket(String filename) {
        super(filename);
    }

    @Override
    public Integer getData() {
        return (Integer) actualData;
    }

    @Override
    protected void setActualData(String line) {
        try {
            actualData = Integer.parseInt((line));
        } catch (NumberFormatException e) {

            Logger.getLogger("log")
                    .info("Wrong line '" + line + "' in file '" +
                            filename + "' (but expected an integer number)");
            updateData();
        }
    }
}
