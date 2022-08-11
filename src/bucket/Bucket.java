package bucket;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.logging.Logger;

public abstract class Bucket {

    protected final String filename;
    protected final Scanner scanner;
    protected Object actualData;
    protected boolean isNotEmpty = true;

    protected Bucket(String filename) {
        this.filename = filename;
        try {
            scanner = new Scanner(new File(filename));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        updateData();
    }
    public Object getData() {
        return actualData;
    }

    public String getFilename() {
        return filename;
    }

    public void updateData() {
        if (isNotEmpty) {
            if (scanner.hasNext()) {
                String line = scanner.nextLine();
                if (line.contains(" ")) {
                    Logger.getLogger("log")
                            .info("Wrong line '" + line + "' in file '" + filename + "'");
                    updateData();
                    return;
                }
                setActualData(line);
            } else {
                isNotEmpty = false;
                scanner.close();
                actualData = null;
            }
        }
    }

    protected abstract void setActualData(String line);
}
