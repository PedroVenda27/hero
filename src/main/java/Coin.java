import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.TerminalPosition;

public class Coin extends Element {

    // Construtor da classe Coin
    public Coin(int x, int y) {
        super(x, y); // Chama o construtor da classe Element
    }

    // Implementação do método abstrato draw
    @Override
    public void draw(TextGraphics graphics) {
        graphics.setForegroundColor(TextColor.Factory.fromString("#FFD700")); // Cor dourada para a moeda
        graphics.putString(new TerminalPosition(position.getX(), position.getY()), "o"); // Desenha a moeda como 'o'
    }
}
