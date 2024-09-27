import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.TerminalPosition;

public class Wall extends Element {

    // Construtor da classe Wall
    public Wall(int x, int y) {
        super(x, y); // Chama o construtor da classe Element
    }

    // Implementação do metodo abstrato draw
    @Override
    public void draw(TextGraphics graphics) {
        graphics.setForegroundColor(TextColor.Factory.fromString("#FF0000")); // Cor vermelha para a parede
        graphics.putString(new TerminalPosition(position.getX(), position.getY()), "#"); // Desenha a parede como '#'
    }
}
