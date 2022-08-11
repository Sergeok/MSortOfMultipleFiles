package sorter;

import container.*;
import bucket.IntegerBucket;
import datalist.DataList;
import datalist.IntegerDataList;

public class IntegerSort extends KWaySort {

    private final boolean isAscDirectionSort;

    public IntegerSort(String[] inputs, String outputFileName, boolean isAscDirectionSort) {
        super(inputs, outputFileName);
        this.isAscDirectionSort = isAscDirectionSort;
    }

    @Override
    protected void dataListInit(String[] inputs) {
        dataList = new IntegerDataList();
        for (String input : inputs) {
            dataList.addBucket(new IntegerBucket(input));
        }
    }

    @Override
    protected boolean isCorrectDirection(Object lastAddedElement, Object elementToAdd) {
        int comparisonResult = ((Integer) lastAddedElement).compareTo((Integer) elementToAdd);
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
                ((IntegerDataList) dataList).getData(el1.getIndex()),
                ((IntegerDataList) dataList).getData(el2.getIndex())
        )) {
            return new GameResult(el2.getIndex(), el1.getIndex());
        } else {
            return new GameResult(el1.getIndex(), el2.getIndex());
        }
    }
}
