import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.TerminalPosition;

import java.util.Random;

public class Monster extends Element {

    // Construtor da classe Monster
    public Monster(int x, int y) {
        super(x, y); // Chama o construtor da classe Element
    }

    // Implementação do metodo abstrato draw
    @Override
    public void draw(TextGraphics graphics) {
        graphics.setForegroundColor(TextColor.Factory.fromString("#FF00FF")); // Cor roxa para o monstro
        graphics.putString(new TerminalPosition(position.getX(), position.getY()), "M"); // Desenha o monstro como 'M'
    }

    // Metodo move para mover o monstro para uma posição adjacente aleatória
    public Position move(int width, int height) {
        Random random = new Random();
        int x = position.getX();
        int y = position.getY();

        // Movimenta o monstro aleatoriamente para uma posição adjacente
        int direction = random.nextInt(4); // 0 = esquerda, 1 = direita, 2 = cima, 3 = baixo
        switch (direction) {
            case 0: // Esquerda
                x = Math.max(1, x - 1);
                break;
            case 1: // Direita
                x = Math.min(width - 2, x + 1);
                break;
            case 2: // Cima
                y = Math.max(1, y - 1);
                break;
            case 3: // Baixo
                y = Math.min(height - 2, y + 1);
                break;
        }
        return new Position(x, y);
    }
}


