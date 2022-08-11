package sorter;

import bucket.StringBucket;
import container.GameResult;
import container.Node;
import datalist.DataList;
import datalist.StringDataList;

public class StringSort extends KWaySort {

    private final boolean isAscDirectionSort;

    public StringSort(String[] inputs, String outputFileName, boolean isAscDirectionSort) {
        super(inputs, outputFileName);
        this.isAscDirectionSort = isAscDirectionSort;
    }

    @Override
    protected void dataListInit(String[] inputs) {
        dataList = new StringDataList();
        for (String input : inputs) {
            dataList.addBucket(new StringBucket(input));
        }
    }

    @Override
    protected boolean isCorrectDirection(Object lastAddedElement, Object elementToAdd) {
        int comparisonResult = ((String) lastAddedElement).compareTo((String) elementToAdd);
        if (comparisonResult == 0) {
            return true;
        }

        if (isAscDirectionSort) {
            return comparisonResult < 0;
        } else {
            return comparisonResult > 0;
        }
    }

    @Override
    protected GameResult playGameBetweenNonNullNodes(Node el1, Node el2, DataList dataList) {
        if (isCorrectDirection(
                ((StringDataList) dataList).getData(el1.getIndex()),
                ((StringDataList) dataList).getData(el2.getIndex())
        )) {
            return new GameResult(el2.getIndex(), el1.getIndex());
        } else {
            return new GameResult(el1.getIndex(), el2.getIndex());
        }
    }
}
