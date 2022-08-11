package datalist;

import bucket.StringBucket;

public class StringDataList extends DataList {

    public StringDataList() {
        super();
    }

    @Override
    public String getData(int index) {
        if (index < list.size()) {
            return ((StringBucket) list.get(index)).getData();
        } else {
            return null;
        }
    }
}
