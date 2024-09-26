import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.graphics.TextGraphics;

import java.io.IOException;

public class Game {
    private Screen screen;
    private Arena arena;

    // Construtor da classe Game
    public Game() throws IOException {
        // Definindo o tamanho do terminal (40 colunas, 20 linhas)
        TerminalSize terminalSize = new TerminalSize(40, 20);
        DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory()
                .setInitialTerminalSize(terminalSize);

        // Criando o terminal e a tela
        Terminal terminal = terminalFactory.createTerminal();
        screen = new TerminalScreen(terminal);

        // Configurando a tela
        screen.setCursorPosition(null); // Não precisamos do cursor
        screen.startScreen(); // Iniciar a tela
        screen.doResizeIfNecessary(); // Redimensionar a tela se necessário

        // Inicializando a arena com o herói e as paredes dentro
        arena = new Arena(40, 20);
    }

    // Método responsável por desenhar na tela
    private void draw() throws IOException {
        screen.clear();
        TextGraphics graphics = screen.newTextGraphics();
        arena.draw(graphics); // Agora a arena desenha o herói e as paredes
        screen.refresh(); // Atualiza a tela para mostrar as mudanças
    }

    // Método responsável por processar as teclas pressionadas
    private void processKey(KeyStroke key) {
        arena.processKey(key); // A arena lida com o processo de movimentação do herói
    }

    // Método responsável por rodar o jogo
    public void run() throws IOException {
        while (true) {
            draw(); // Desenha a arena e o herói na tela
            KeyStroke key = screen.readInput(); // Lê a tecla pressionada
            if (key.getKeyType() == KeyType.EOF || (key.getKeyType() == KeyType.Character && key.getCharacter() == 'q')) {
                screen.close(); // Fecha o jogo se a tecla 'q' for pressionada ou a janela for fechada
                break;
            }
            processKey(key); // Processa a tecla pressionada
        }
    }

    // Método main para iniciar o jogo
    public static void main(String[] args) {
        try {
            // Cria uma nova instância do jogo e executa
            Game game = new Game();
            game.run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
