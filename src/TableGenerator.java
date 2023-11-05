import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class TableGenerator {
    private int width;
    private int height;
    private final Random random = new Random();
    private final String[] surnames = {
            "Иванов", "Смирнов", "Кузнецов", "Попов", "Васильев", "Петров", "Соколов", "Михайлов",
            "Новиков", "Федоров", "Морозов", "Волков", "Алексеев", "Лебедев", "Семенов", "Егоров",
            "Павлов", "Козлов", "Степанов", "Николаев", "Орлов", "Андреев", "Макаров", "Никитин",
            "Зайцев", "Захаров", "Борисов", "Костин", "Крылов", "Королев",
            "Абрамов", "Воробьев", "Гаврилов", "Лазарев", "Медведев", "Романов", "Савельев",
            "Тихонов", "Чистяков", "Ширяев", "Яковлев", "Белов", "Антонов", "Архипов", "Быков",
            "Власов", "Герасимов", "Данилов", "Ефимов", "Зуев", "Исаев", "Князев", "Львов",
            "Муравьев", "Нестеров", "Осипов", "Панов", "Родионов", "Сидоров", "Тимофеев",
            "Усов", "Фомин", "Харитонов", "Цветков", "Шестаков", "Щербаков", "Юдин", "Ярошевич",
            "Александров", "Безруков", "Ваганов", "Громов", "Дорофеев", "Емельянов", "Жаров",
            "Зимин", "Игнатов", "Калашников", "Комаров", "Латышев", "Миронов", "Никифоров",
            "Олейников", "Панфилов", "Разумов", "Соловьев", "Терентьев", "Уваров", "Филатов",
            "Чернов", "Шилов", "Щукин", "Юрьев", "Яшин", "Агафонов", "Белозеров", "Васильев",
            "Горшков", "Дерябин", "Ефремов", "Журавлев", "Заборов", "Ильин", "Казаков", "Киреев",
            "Леонтьев", "Максимов", "Наумов", "Орехов", "Петухов", "Рогов", "Семин", "Трофимов"
    };

    public TableGenerator(int width, int height) {
        this.height = height;
        this.width = width;
    }


    public void writeTableToFile(String filePath) {
        List<List<String>> list = createRandomTable();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (List<String> row : list) {
                StringBuilder line = new StringBuilder();
                for (String cell : row) {
                    line.append(cell).append(",");
                }
                line.deleteCharAt(line.length() - 1); // Удаление последней запятой
                writer.write(line.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<List<String>> createRandomTable() {
        List<List<String>> list = new ArrayList<>();
        for (int i = 0; i < height; i++) {
            list.add(new ArrayList<>());
            for (int j = 0; j < width; j++) {
                String element;
                int percent = random.nextInt(3);
                if (percent == 0) {
                    element = randomNumber();
                } else if (percent == 1) {
                    element = randomString();
                } else {
                    element = randomDate();
                }
                list.get(i).add(element);
            }
        }
        return list;
    }

    private String randomNumber() {
        return String.valueOf(random.nextInt(1000000));
    }

    private String randomDate() {
        int day = random.nextInt(31) + 1;
        int month = random.nextInt(12) + 1;
        int year = random.nextInt(1500) + 1000;

        String d = (day < 10) ? "0" + day : String.valueOf(day);
        String m = (month < 10) ? "0" + month : String.valueOf(month);
        String y = String.valueOf(year);

        return d + "/" + m + "/" + y;
    }

    private String randomString() {
        return surnames[random.nextInt(surnames.length - 1)];
    }
}
