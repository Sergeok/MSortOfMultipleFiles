package bucket;

public class StringBucket extends Bucket {

    public StringBucket(String filename) {
        super(filename);
    }

    @Override
    public String getData() {
        return (String) actualData;
    }

    @Override
    protected void setActualData(String line) {
        this.actualData = line;
    }
}
