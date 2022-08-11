package sorter;

import container.GameResult;
import container.Node;
import datalist.DataList;
import util.Utility;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public abstract class KWaySort {

    protected DataList dataList;
    private final BufferedWriter output;

    public KWaySort(String[] inputs, String outputFileName) {
        try {
            output = new BufferedWriter(new FileWriter(outputFileName, true));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        dataListInit(inputs);
    }

    protected abstract void dataListInit(String[] inputs);

    protected abstract boolean isCorrectDirection(Object lastAddedElement, Object elementToAdd);

    public void merge() {
        if (dataList.isFinished()) {
            Logger.getLogger("log").info("Nothing to sort");
            return;
        }

        List<Node> indexLayer = new ArrayList<>();
        for (int i = 0; i < Utility.findNextPowerOf2(dataList.size()); i++) {
            indexLayer.add(new Node(i));
        }

        Object lastAddedElement, elementToAdd;
        int rootIndex;

        rootIndex = buildTree(indexLayer, indexLayer).getIndex();
        elementToAdd = dataList.getData(rootIndex);
        appendStringToOutputFile(elementToAdd + "");
        lastAddedElement = elementToAdd;
        dataList.updateData(rootIndex);

        while (!dataList.isFinished()) {
            Node newNode = new Node(rootIndex);
            Node oldNode = indexLayer.get(rootIndex);

            Node winner = replayGames(oldNode, newNode);
            rootIndex = winner.getIndex();
            elementToAdd = dataList.getData(rootIndex);

            if (isCorrectDirection(lastAddedElement, elementToAdd)) {
                appendStringToOutputFile("\n" + elementToAdd);
                lastAddedElement = elementToAdd;
            } else {
                Logger.getLogger("log").info("Element '" + elementToAdd +
                        "' from input file '" + dataList.getFilename(rootIndex) +
                        "' did not match the sort condition and was skipped");
            }
            dataList.updateData(rootIndex);
        }

        try {
            output.close();
        } catch (IOException e) {
            throw new RuntimeException("Failed to close output file", e);
        }
    }

    private Node replayGames(Node node, Node newNode) {
        GameResult result = playGame(node, newNode, dataList);
        Node winner =  new Node(result.getWinner());
        node.setIndex(result.getLoser());

        if (node.getParent() != null) {
            return replayGames(node.getParent(), winner);
        }
        return winner;
    }

    private Node buildTree(List<Node> loserLayer, List<Node> winnerLayer) {
        List<Node> nextLoserLayer = new ArrayList<>();
        List<Node> nextWinnerLayer = new ArrayList<>();

        for (int i = 0; i < winnerLayer.size(); i+=2) {
            Node el1 = winnerLayer.get(i);
            Node el2 = winnerLayer.get(i+1);

            GameResult result = playGame(el1, el2, dataList);
            Node parent = new Node(result.getLoser());
            loserLayer.get(i).setParent(parent);
            loserLayer.get(i+1).setParent(parent);

            nextLoserLayer.add(parent);
            nextWinnerLayer.add(new Node(result.getWinner()));
        }

        if (nextWinnerLayer.size() == 1) {
            return nextWinnerLayer.get(0);
        } else {
            return buildTree(nextLoserLayer, nextWinnerLayer);
        }
    }

    private void appendStringToOutputFile(String str) {
        try {
            output.write(str);
        } catch (IOException e) {
            throw new RuntimeException("Output file write error", e);
        }
    }

    private GameResult playGame(Node el1, Node el2, DataList dataList) {
        if (dataList.getData(el2.getIndex()) == null) {
            return new GameResult(el2.getIndex(), el1.getIndex());
        }
        if (dataList.getData(el1.getIndex()) == null) {
            return new GameResult(el1.getIndex(), el2.getIndex());
        }

        return playGameBetweenNonNullNodes(el1, el2, dataList);
    }

    abstract GameResult playGameBetweenNonNullNodes(Node el1, Node el2, DataList dataList);
}
