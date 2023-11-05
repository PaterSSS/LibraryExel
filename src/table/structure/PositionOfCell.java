package table.structure;

public class PositionOfCell {
    private int x;
    private int y;
    public PositionOfCell(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }

    @Override
    public int hashCode() {
        int hash = 23;
        hash = hash * 31 + x;
        hash = hash * 31 + y;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        PositionOfCell ps = (PositionOfCell) obj;
        if (this.x != ps.getX()) {
            return false;
        }
        if (this.y != ps.getY()) {
            return false;
        }

        return true;
    }
}
