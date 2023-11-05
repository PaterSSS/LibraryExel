package table;

import table.structure.Cell;
import table.structure.PositionOfCell;

import java.io.*;
import java.util.*;


public class Table {
    private HashMap<PositionOfCell, Cell> table = new HashMap<>();
    private TableGrid grid;
    private List<Integer> gridOfRowsAndColumns = new ArrayList<>();

    public Table(List<List<String>> list) {
        for (int i = 0; i < list.size(); i++) {
            int iter = 0;
            for (int j = 0; j < list.get(i).size(); j++) {
                table.put(new PositionOfCell(j, i), new Cell(list.get(i).get(j)));
                iter = j;
            }
            gridOfRowsAndColumns.add(iter);
        }
        grid = new TableGrid(table, gridOfRowsAndColumns);
    }

    public Table() {
    }

    public void readTableFromConsole() {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Enter your table: ");
            int rowIterator = 0;

            String line;
            while (scanner.hasNextLine() && !(line = scanner.nextLine()).isEmpty()) {
                String[] tmp = line.split("[ ,;]");
                gridOfRowsAndColumns.add(tmp.length);
                int columnIterator = 0;
                for (String item : tmp) {
                    table.put(new PositionOfCell(columnIterator++, rowIterator), new Cell(item));
                }
                rowIterator++;
            }
        }
        grid = new TableGrid(table, gridOfRowsAndColumns);
    }

    public void printTableInConsole() {
        List<String> listOfPaddings = grid.getGridOfTable();
        List<String> colNumeration = generateStrings(grid.getMaxNumberOfColumns());
        System.out.printf(listOfPaddings.get(0), colNumeration.toArray());
        listOfPaddings.remove(0);
        int i = 0;
        for (String item : listOfPaddings) {
            List<String> row = getRow(i++);
            System.out.printf(item, row.toArray());
        }
        System.out.println();
    }

    private List<String> getRow(int rowNumber) {
        List<String> result = new ArrayList<>();
        for (int i = 0; i < gridOfRowsAndColumns.get(rowNumber); i++) {
            result.add(table.get(new PositionOfCell(i, rowNumber)).getData());
        }
        return result;
    }

    private List<String> generateStrings(int count) {
        List<String> strings = new ArrayList<>();

        for (int i = 1; i <= count; i++) {
            strings.add(getString(i));
        }

        return strings;
    }

    private String getString(int n) {
        StringBuilder builder = new StringBuilder();

        while (n > 0) {
            int remainder = (n - 1) % 26;
            char ch = (char) ('a' + remainder);
            builder.insert(0, ch);
            n = (n - 1) / 26;
        }

        return builder.toString();
    }

    public void readTableFromCSVFile(String pathToFile) {
        try (Scanner scanner = new Scanner(new File(pathToFile))) {
            int rowIterator = 0;
            String line;
            while (scanner.hasNextLine() && !(line = scanner.nextLine()).isEmpty()) {
                String[] tmp = line.split("[ ,;]");
                int columnIterator = 0;
                gridOfRowsAndColumns.add(tmp.length);
                for (String item : tmp) {
                    table.put(new PositionOfCell(columnIterator++, rowIterator), new Cell(item));
                }
                rowIterator++;
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        grid = new TableGrid(table, gridOfRowsAndColumns);
    }

    public static void main(String[] args) {
        Table table1 = new Table();
        table1.readTableFromConsole();
        table1.printTableInConsole();
        System.out.println();
    }
}
