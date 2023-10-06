package table;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class TableGrid {
    public static final String RESET = "\033[0m";
    private static final String GREEN_BACKGROUND = "\033[42m";  // GREEN
    private static final String YELLOW_BACKGROUND = "\033[43m"; // YELLOW
    private static final String PURPLE_BACKGROUND = "\033[45m"; // PURPLE
    public static final String GREEN_BACKGROUND_BRIGHT = "\033[0;102m";// GREEN
    public static final String BLUE_BACKGROUND_BRIGHT = "\033[0;104m";// BLUE


    private final List<LongestItem> listOfMaxLength = new ArrayList<>();
    private List<String> gridOfTable = new ArrayList<>();
    private int maxNumberOfColumns;
    private static final int DEFAULT_GAP = 5;
    private static final String DEFAULT_DELIMITER = ",";

    private class LongestItem {
        int length;
        boolean hasNextItem;

        public LongestItem(int length, boolean hasNextItem) {
            this.hasNextItem = hasNextItem;
            this.length = length;
        }
    }

    public TableGrid(List<List<Cell>> list) {
        updateMaxLength(list);
        makeGridOfTable(list);
    }

    private void updateMaxLength(List<List<Cell>> table) {
        int maxColumnCount = Math.max(table.get(0).size(), 0);

        for (int columnIterator = 0; columnIterator < maxColumnCount; columnIterator++) {
            for (List<Cell> row : table) {
                if (columnIterator >= row.size()) {
                    continue;
                }

                maxColumnCount = Math.max(row.size(), maxColumnCount);
                if (columnIterator >= listOfMaxLength.size()) {
                    listOfMaxLength.add(new LongestItem(row.get(columnIterator).getLength(), columnIterator != row.size() - 1));
                } else {
                    if (row.get(columnIterator).getLength() > listOfMaxLength.get(columnIterator).length) {
                        listOfMaxLength.get(columnIterator).length = row.get(columnIterator).getLength();
                        listOfMaxLength.get(columnIterator).hasNextItem = columnIterator != row.size() - 1;
                    }
                }
            }
        }
        maxNumberOfColumns = maxColumnCount;
    }

    private void  makeGridOfTable(List<List<Cell>> table) {
        makeTitleOfTable(table);
        int gapBeforeFirstColumn = (int) (Math.log10(table.size()) + 1) - 1;
        int rowNumeration = 1;
        int base = 10;
        for (List<Cell> row : table) {
            if (rowNumeration % base == 0) {
                base *= 10;
                gapBeforeFirstColumn -= 1;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(YELLOW_BACKGROUND).append(rowNumeration++).append(".").append(createSpaces(gapBeforeFirstColumn))
                    .append(RESET).append(" ").append("%s");
            for (int i = 1; i < row.size(); i++) {
                if (i == 1 && !row.get(0).getData().isEmpty()) {
                    stringBuilder.append(",");
                }
                int gap = listOfMaxLength.get(i - 1).length - row.get(i - 1).getLength();
                int marginLeft = gap + ((listOfMaxLength.get(i - 1).hasNextItem) ? 0 : -1) +
                        DEFAULT_GAP + row.get(i).getLength() + ((checkRow(row, row.get(i).getData())) ? 1 : 0);
                stringBuilder.append("%").append(marginLeft).append("s")
                        .append(((i == (row.size() - 1)) || row.get(i).getData().isEmpty()) ? " " : DEFAULT_DELIMITER);
            }
            stringBuilder.append("\n");
            gridOfTable.add(stringBuilder.toString());
            stringBuilder.delete(2, stringBuilder.length());
        }
    }

    private boolean checkRow(List<Cell> row, String item) {
        int i = 0;
        while (row.get(i).getData().isEmpty()) {
            i++;
        }
        if (i == 0) {
            return false;
        }
        return row.get(i).getData().equals(item);
    }

    private String createSpaces(int spaces) {
        return " ".repeat(Math.max(0, spaces));
    }
    private void makeTitleOfTable(List<List<Cell>> table) {
        int gapBeforeFirstColumn = (int) (Math.log10(table.size()) + 1) + 3;
        StringBuilder stringBuilderForNumeration = new StringBuilder(GREEN_BACKGROUND + "%" + gapBeforeFirstColumn + "s");
        int countWords = 0;
        for (int i = 0; i < maxNumberOfColumns - 1; i++) {

            int gap = DEFAULT_GAP + listOfMaxLength.get(i).length + ((listOfMaxLength.get(i).hasNextItem) ? 1 : 0) +
                    ((i == 24)? 1: 0) + ((i == 700)? 1: 0);
            stringBuilderForNumeration.append("%").append(gap).append("s");
        }
        int words = 1;
        if (maxNumberOfColumns > 702) {
            words = 3;
        } else if (maxNumberOfColumns > 26) {
            words = 2;
        }
        int spaces = listOfMaxLength.get(listOfMaxLength.size() - 1).length - words;
        stringBuilderForNumeration.append(createSpaces(spaces)).append(RESET).append("\n");
        gridOfTable.add(stringBuilderForNumeration.toString());
    }
//    private static int getCountOfWordsInTitle(int columns) {
//        double tmp = columns;
//        int count = 1;
//        int base = 26;
//        while(true) {
//            tmp /= base;
//            if (tmp <= 1) {
//                break;
//            } else {
//                base *= base;
//                count++;
//            }
//        }
//        return count;
//    }
    public int getMaxNumberOfColumns() {
        return maxNumberOfColumns;
    }

    public static void main(String[] args) {
//        System.out.println(getCountOfWordsInTitle(703));
    }

    public List<String> getGridOfTable() {
        return gridOfTable;
    }
}
