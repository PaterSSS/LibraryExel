import table.Table;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.function.Predicate;

public class Main {
    public static void main(String[] args) {
        TableGenerator tb = new TableGenerator(250,250);
        List<List<String>> list = tb.createRandomTable();
//        tb.writeTableToFile("C:\\Материалы ВУЗ\\3й семестр\\ООП\\projects\\LibraryExelHashTable\\inputData.txt");
        Table table1 = new Table(list);
//        table1.readTableFromCSVFile("C:\\Материалы ВУЗ\\3й семестр\\ООП\\projects\\LibraryExelHashTable\\inputData.txt");
        table1.printTableInConsole();
    }
}