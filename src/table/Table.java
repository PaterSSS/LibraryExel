package table;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;


public class Table {
    private List<List<Cell>> table = new ArrayList<>();
     private TableGrid grid;
     private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

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
            int remainder = (n - 1) % 26; // Определение остатка от деления на 26
            char ch = (char) ('a' + remainder); // Восстановление символа с помощью остатка

            builder.insert(0, ch); // Вставка символа в начало строки
            n = (n - 1) / 26; // Переход к следующему разряду
        }

        return builder.toString();
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
                if (columnIterator >= row.size()) {
                    continue;
                }
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

    private <T> List<String> selectDataByRows(Predicate<T> predicate, String regex, Function<String, T> converter) {

        List<String> listOfStrings = new ArrayList<>();
        for (List<Cell> list: table) {
            for (Cell item: list) {
                if (!item.getData().matches(regex)) {
                    continue;
                }
                T tmp = converter.apply(item.getData());
                if (predicate.test(tmp)) {
                    listOfStrings.add(item.getData());
                }
            }
        }
        return listOfStrings;
    }
    private <T> List<String> selectDataByColumns(Predicate<T> predicate, String regex, Function<String, T> converter) {
        List<String> stringList = new ArrayList<>();
        for (int columnIterator = 0; columnIterator < grid.getMaxNumberOfColumns(); columnIterator++) {
            for (List<Cell> row : table) {
                if (columnIterator >= row.size()) {
                    continue;
                }
                String data = row.get(columnIterator).getData();
                if (!data.matches(regex)) {
                    continue;
                }
                T tmp = converter.apply(data);
                if (predicate.test(tmp)) {
                    stringList.add(data);
                }
            }
        }
        return stringList;
    }
    public List<String> selectNumberData(Predicate<Double> predicate, boolean searchingInRows) {
        return (searchingInRows) ? selectDataByRows(predicate,"-?\\d+([,.])?\\d*", Double::parseDouble):
                selectDataByColumns(predicate,"-?\\d+([,.])?\\d*", Double::parseDouble);
    }
    public List<String> selectDateData(Predicate<LocalDate> predicate, boolean searchingInRows) {
        return (searchingInRows) ? selectDataByRows(predicate, "\\d{2}[./]\\d{2}[./]\\d{4}", Table::convertDate):
                selectDataByColumns(predicate, "\\d{2}[./]\\d{2}[./]\\d{4}", Table::convertDate);
    }
    public List<String> selectStringData(Predicate<String> predicate, boolean searchingInRows) {
        return (searchingInRows) ? selectDataByRows(predicate, "^(?!-?\\d+[.,]?\\d*$|\\d{2}[./]\\d{2}[./]\\d{4}$).+", e -> e):
                selectDataByColumns(predicate, "^(?!-?\\d+[.,]?\\d*$|\\d{2}[./]\\d{2}[./]\\d{4}$).+", e -> e);
    }
    private static LocalDate convertDate(String s) {
        return LocalDate.parse(s, dateFormatter);
    }
    public static void main(String[] args) {
        Table table1 = new Table();
        table1.readTableFromConsole();
//        table1.sortTableByRows();
//        table1.printTableInConsole();
//        table1.sortTableByColumns();
        table1.printTableInConsole();
    }
}
