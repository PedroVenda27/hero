public class Position {
    private int x;
    private int y;

    // Construtor da classe Position
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // Getters e Setters para x e y
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    // Sobrescrevendo o metodo equals para comparar posições
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return x == position.x && y == position.y;
    }

    // Sobrescrevendo o metodo hashCode (boa prática quando sobrescrevemos equals)
    @Override
    public int hashCode() {
        return 31 * x + y;
    }
}



