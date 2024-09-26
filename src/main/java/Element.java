import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.TerminalPosition;

public abstract class Element {
    protected Position position;

    // Construtor da classe Element
    public Element(int x, int y) {
        this.position = new Position(x, y);
    }

    // Getter e Setter para a posição
    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    // Método abstrato para desenhar o elemento
    public abstract void draw(TextGraphics graphics);
}
