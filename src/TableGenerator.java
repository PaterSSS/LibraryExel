import java.util.ArrayList;
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
            "Зайцев", "Захаров", "Борисов", "Костин", "Крылов", "Королев"
    };
    public TableGenerator(int width, int height) {
        this.height = height;
        this.width = width;
    }

    public List<List<String>> createRandomTable() {
        List<List<String>> list = new ArrayList<>();
        for (int i = 0; i < height; i++) {
            list.add(new ArrayList<>());
            for (int j = 0; j < width; j++) {
                String element;
                double percent = Math.random();
                if (percent < 0.33) {
                    element = randomNumber();
                } else if (percent < 0.66) {
                    element = randomNumber();
                } else {
                    element = randomString();
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

        String d = (day < 10)? "0" + day: String.valueOf(day);
        String m = (month < 10) ? "0" + month: String.valueOf(month);
        String y = String.valueOf(year);

        return d + "/" + m + "/" + y;
    }
    private String randomString() {
        return surnames[random.nextInt(surnames.length - 1)];
    }
}
