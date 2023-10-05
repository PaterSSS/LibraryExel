package table;

import java.time.LocalDate;

public class Cell implements Comparable<Cell> {
    private String data;
    private int length;
    private TypeOfData type;

    public Cell(String data) {
        this.data = data;
        setParams();
    }

    private void setParams() {
        length = data.length();
        if (data.matches("-?\\d+([,.])?\\d*")) {
            type = TypeOfData.Number;
        } else if (data.matches("\\d{2}[./]\\d{2}[./]\\d{4}")) {
            type = TypeOfData.Date;
        } else {
            type = TypeOfData.String;
        }
    }

    public String getData() {
        return data;
    }

    public int getLength() {
        return length;
    }

    public TypeOfData getType() {
        return type;
    }

    public void setData(String data) {
        this.data = data;
        setParams();
    }


    @Override
    public int compareTo(Cell tmp) {
        if (this.type == TypeOfData.Number && (tmp.type == TypeOfData.Date ||
                tmp.type == TypeOfData.String)) {
            return -1;
        } else if (this.type == TypeOfData.Date && tmp.type == TypeOfData.Number) {
            return 1;
        } else if (this.type == TypeOfData.Date && tmp.type == TypeOfData.String) {
            return -1;
        } else if (this.type == TypeOfData.String && (tmp.type == TypeOfData.Date || tmp.type == TypeOfData.Number)) {
            return 1;
        } else {
            if (type.equals(TypeOfData.String)) {
                return this.data.compareTo((tmp.data));
            } else if (type.equals(TypeOfData.Number)) {
                double d1 = Double.parseDouble(this.data);
                double d2 = Double.parseDouble(tmp.data);
                return Double.compare(d1, d2);
            } else {
                LocalDate ld1 = LocalDate.parse(this.data);
                LocalDate ld2 = LocalDate.parse(tmp.data);
                return ld1.compareTo(ld2);
            }
        }
    }
}