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

        // Construir a Classe Jogo
    public Game() throws IOException {

        // Definir o tamanho do terminal (40 colunas, 20 linhas)
        TerminalSize terminalSize = new TerminalSize(40, 20);
        DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory()
                .setInitialTerminalSize(terminalSize);

        // Criar o terminal e a Tela
        Terminal terminal = terminalFactory.createTerminal();
        screen = new TerminalScreen(terminal);

        // Configurar a Tela
        screen.setCursorPosition(null); // Não Precisamos do cursor
        screen.startScreen(); // Iniciar a tela
        screen.doResizeIfNecessary(); // Redimensionar a tela se necessário

        // Inicializando a arena com o Herói e as paredes dentro
        arena = new Arena(40, 20);
    }

    // Metodo responsável por desenhar na tela
    private void draw() throws IOException {
        screen.clear();
        TextGraphics graphics = screen.newTextGraphics();
        arena.draw(graphics); // Agora a arena desenha o herói e as paredes
        screen.refresh(); // Atualiza a tela para mostrar as mudanças
    }

    // Metodo responsável por processar as teclas pressionadas
    private void processKey(KeyStroke key) {
        arena.processKey(key); // A arena lida com o processo de movimentação do herói
    }

    // Metodo responsável por rodar o jogo
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

    // Metodo main para iniciar o jogo
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
