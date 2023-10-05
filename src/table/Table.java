package table;

import java.util.*;

public class Table {
    private List<List<Cell>> table = new ArrayList<>();
    TableGrid grid;

    public void readTableFromConsole() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your table: ");
        try {
            String line;
            while (scanner.hasNextLine() && !(line = scanner.nextLine()).isEmpty()) {
                String[] tmp = line.split("[ ,;]");
                List<Cell> cellList = new ArrayList<>();
                for (String item : tmp) {
                    cellList.add(new Cell(item));
                }
                table.add(cellList);
            }
        } finally {
            scanner.close();
        }
        grid = new TableGrid(table);
    }
    public void printTableInConsole() {
        List<String> listOfPaddings = grid.makeGridOfTable(table);
        List<Character> colNumeration = new LinkedList<>();
        for (int i = 0; i < grid.getMaxNumberOfColumns(); i++) {
            colNumeration.add((char) (i + 97));
        }
        System.out.printf(listOfPaddings.get(0), colNumeration.toArray());
        listOfPaddings.remove(0);
        int i = 0;
        for (String item : listOfPaddings) {
            List<String> row = getRow(i++);
            System.out.printf(item, row.toArray());
        }
    }
    private List<String> getRow(int rowNumber) {
        List<String> resultList = new ArrayList<>();
        for (int i = 0; i < table.get(rowNumber).size(); i++) {
            resultList.add(table.get(rowNumber).get(i).getData());
        }
        return resultList;
    }
    public void sortTableByRows() {
        for (List<Cell> row : table) {
            Collections.sort(row);
        }
        grid = new TableGrid(table);
    }
    public void sortTableByColumns() {
        for (int columnIterator = 0; columnIterator < grid.getMaxNumberOfColumns(); columnIterator++) {
            List<Cell> column = new ArrayList<>();
            for (List<Cell> row : table) {
                column.add(row.get(columnIterator));
            }
            Collections.sort(column);
            int i = 0;
            for (List<Cell> row : table) {
                if (columnIterator >= row.size()) {
                    continue;
                }
                row.set(columnIterator, column.get(i++));
            }
        }
    }
    public static void main(String[] args) {
        Table table1 = new Table();
        table1.readTableFromConsole();
        table1.sortTableByRows();
        table1.printTableInConsole();

    }
}
