package datalist;

import bucket.IntegerBucket;

public class IntegerDataList extends DataList {

    public IntegerDataList() {
        super();
    }

    @Override
    public Integer getData(int index) {
        if (index < list.size()) {
            return ((IntegerBucket) list.get(index)).getData();
        } else {
            return null;
        }
    }
}