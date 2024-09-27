import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;

public class Application {
    public static void main(String[] args) {
        try {
            // Definir o tamanho do terminal (40 colunas, 20 linhas)
            TerminalSize terminalSize = new TerminalSize(40, 20);
            DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory()
                    .setInitialTerminalSize(terminalSize);

            // Criar o terminal e a tela
            Terminal terminal = terminalFactory.createTerminal();
            Screen screen = new TerminalScreen(terminal);

            // Configurar a tela
            screen.setCursorPosition(null); // Não precisamos do cursor
            screen.startScreen(); // Iniciar a tela
            screen.doResizeIfNecessary(); // Redimensionar a tela se necessário

            // Limpar a tela, desenhar um caractere, e atualizar a tela
            screen.clear();
            screen.setCharacter(10, 10, TextCharacter.fromCharacter('X')[0]); // Desenha um 'X' na posição (10, 10)
            screen.refresh(); // Atualiza a tela para mostrar as mudanças

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
