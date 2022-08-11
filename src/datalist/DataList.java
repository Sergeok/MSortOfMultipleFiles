package datalist;

import bucket.Bucket;

import java.util.ArrayList;
import java.util.List;

public abstract class DataList {

    final protected List<Bucket> list;

    public DataList() {
        list = new ArrayList<>();
    }

    public void addBucket(Bucket bucket) {
        list.add(bucket);
    }

    public abstract Object getData(int index);

    public String getFilename(int index) {
        return list.get(index).getFilename();
    }

    public int size() {
        return list.size();
    }

    public void updateData(int index) {
        list.get(index).updateData();
    }

    public boolean isFinished() {
        for (Bucket bucket : list) {
            if (bucket.getData() != null) {
                return false;
            }
        }
        return true;
    }
}
