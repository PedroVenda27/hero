import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;

public class Hero extends Element {

    // Construtor da classe Hero
    public Hero(int x, int y) {
        super(x, y); // Chama o construtor da classe Element
    }

    // Métodos de movimentação que retornam a nova posição desejada
    public Position moveUp() {
        return new Position(position.getX(), Math.max(0, position.getY() - 1)); // Move para cima
    }

    public Position moveDown(int maxRows) {
        return new Position(position.getX(), Math.min(maxRows - 1, position.getY() + 1)); // Move para baixo
    }

    public Position moveLeft() {
        return new Position(Math.max(0, position.getX() - 1), position.getY()); // Move para a esquerda
    }

    public Position moveRight(int maxCols) {
        return new Position(Math.min(maxCols - 1, position.getX() + 1), position.getY()); // Move para a direita
    }

    // Implementação do metodo abstrato draw
    @Override
    public void draw(TextGraphics graphics) {
        graphics.setForegroundColor(TextColor.Factory.fromString("#FFFF00")); // Cor amarela para o herói
        graphics.enableModifiers(SGR.BOLD); // Deixa o herói em negrito
        graphics.putString(new TerminalPosition(position.getX(), position.getY()), "X"); // Desenha o herói
    }
}
