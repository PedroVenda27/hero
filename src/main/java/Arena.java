import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Arena {
    private int width;
    private int height;
    private Hero hero;
    private List<Wall> walls;
    private List<Coin> coins; // Lista de moedas
    private List<Monster> monsters; // Lista de monstros

    // Construtor da classe Arena
    public Arena(int width, int height) {
        this.width = width;
        this.height = height;
        this.hero = new Hero(10, 10); // Inicializando o herói na arena
        this.walls = createWalls(); // Cria as paredes
        this.coins = new ArrayList<>(); // Inicializa a lista de moedas como vazia
        this.coins = createCoins(); // Cria as moedas
        this.monsters = new ArrayList<>(); // Inicializa a lista de monstros como vazia
        this.monsters = createMonsters(); // Cria os monstros
    }

    // Metodo para criar as paredes ao redor da arena
    private List<Wall> createWalls() {
        List<Wall> walls = new ArrayList<>();
        // Paredes horizontais (superior e inferior)
        for (int c = 0; c < width; c++) {
            walls.add(new Wall(c, 0)); // Paredes superiores
            walls.add(new Wall(c, height - 1)); // Paredes inferiores
        }
        // Paredes verticais (esquerda e direita)
        for (int r = 1; r < height - 1; r++) {
            walls.add(new Wall(0, r)); // Paredes à esquerda
            walls.add(new Wall(width - 1, r)); // Paredes à direita
        }
        return walls;
    }

    // Metodo para criar moedas em posições aleatórias
    private List<Coin> createCoins() {
        Random random = new Random();
        List<Coin> coins = new ArrayList<>();
        while (coins.size() < 5) {
            int x = random.nextInt(width - 2) + 1;
            int y = random.nextInt(height - 2) + 1;
            Position coinPosition = new Position(x, y);

            // Verifica se a moeda está em cima do herói ou de outra moeda
            if (!coinPosition.equals(hero.getPosition()) && !isPositionOccupiedByCoin(coinPosition)) {
                coins.add(new Coin(x, y));
            }
        }
        return coins;
    }

    // Metodo para criar monstros em posições aleatórias
    private List<Monster> createMonsters() {
        Random random = new Random();
        List<Monster> monsters = new ArrayList<>();
        while (monsters.size() < 3) {
            int x = random.nextInt(width - 2) + 1;
            int y = random.nextInt(height - 2) + 1;
            Position monsterPosition = new Position(x, y);

            // Verifica se o monstro está em cima do herói ou de outra moeda ou monstro
            if (!monsterPosition.equals(hero.getPosition()) && !isPositionOccupiedByCoin(monsterPosition) && !isPositionOccupiedByMonster(monsterPosition)) {
                monsters.add(new Monster(x, y));
            }
        }
        return monsters;
    }

    // Verifica se a posição já está ocupada por outra moeda
    private boolean isPositionOccupiedByCoin(Position position) {
        for (Coin coin : coins) {
            if (coin.getPosition().equals(position)) {
                return true;
            }
        }
        return false;
    }

    // Verifica se a posição já está ocupada por outro monstro
    private boolean isPositionOccupiedByMonster(Position position) {
        for (Monster monster : monsters) {
            if (monster.getPosition().equals(position)) {
                return true;
            }
        }
        return false;
    }

    // Desenha a arena, as paredes, as moedas, os monstros e o herói
    public void draw(TextGraphics graphics) throws IOException {
        // Configura a cor do fundo da arena
        graphics.setBackgroundColor(TextColor.Factory.fromString("#336699")); // Cor de fundo da arena
        graphics.fillRectangle(new TerminalPosition(0, 0), new TerminalSize(width, height), ' '); // Preenche a arena com espaços vazios

        // Desenha as paredes
        for (Wall wall : walls) {
            wall.draw(graphics);
        }

        // Desenha as moedas
        for (Coin coin : coins) {
            coin.draw(graphics);
        }

        // Desenha os monstros
        for (Monster monster : monsters) {
            monster.draw(graphics);
        }

        // Desenha o herói
        hero.draw(graphics);
    }

    // Processa a tecla pressionada para mover o herói e move os monstros
    public void processKey(KeyStroke key) {
        switch (key.getKeyType()) {
            case ArrowUp:
                moveHero(hero.moveUp()); // Tentativa de mover para cima
                break;
            case ArrowDown:
                moveHero(hero.moveDown(height)); // Tentativa de mover para baixo
                break;
            case ArrowLeft:
                moveHero(hero.moveLeft()); // Tentativa de mover para a esquerda
                break;
            case ArrowRight:
                moveHero(hero.moveRight(width)); // Tentativa de mover para a direita
                break;
            default:
                break;
        }
        moveMonsters(); // Mover os monstros
        verifyMonsterCollisions(); // Verificar colisões com os monstros
    }

    // Verifica se o herói pode se mover para a nova posição e realiza a movimentação
    public void moveHero(Position newPosition) {
        if (canHeroMove(newPosition)) {
            hero.setPosition(newPosition); // Movimenta o herói se possível
            retrieveCoins(); // Verifica se o herói pegou alguma moeda
        }
    }

    // Remove uma moeda da lista se o herói estiver na mesma posição
    private void retrieveCoins() {
        for (int i = 0; i < coins.size(); i++) {
            if (coins.get(i).getPosition().equals(hero.getPosition())) {
                coins.remove(i); // Remove a moeda capturada
                break;
            }
        }
    }

    // Mover todos os monstros
    private void moveMonsters() {
        for (Monster monster : monsters) {
            Position newPosition = monster.move(width, height);
            if (!isPositionOccupiedByWall(newPosition) && !isPositionOccupiedByMonster(newPosition)) {
                monster.setPosition(newPosition); // Move o monstro para a nova posição se estiver livre
            }
        }
    }

    // Verifica se a posição está ocupada por uma parede
    private boolean isPositionOccupiedByWall(Position position) {
        for (Wall wall : walls) {
            if (wall.getPosition().equals(position)) {
                return true;
            }
        }
        return false;
    }

    // Verifica se o herói colidiu com algum monstro
    private void verifyMonsterCollisions() {
        for (Monster monster : monsters) {
            if (monster.getPosition().equals(hero.getPosition())) {
                System.out.println("Game Over! The hero was caught by a monster.");
                System.exit(0); // Encerra o jogo se o herói colidir com um monstro
            }
        }
    }

    // Verifica se a posição é válida dentro da arena e se não está ocupada por uma parede
    private boolean canHeroMove(Position position) {
        // Verifica se a posição está fora da arena
        if (position.getX() < 0 || position.getX() >= width || position.getY() < 0 || position.getY() >= height) {
            return false;
        }
        // Verifica se a posição está ocupada por uma parede
        for (Wall wall : walls) {
            if (wall.getPosition().equals(position)) {
                return false;
            }
        }
        return true;
    }
}
