import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Arrays;

public class GameController {
    private final Grid grid;
    private final GameView gameView;
    private boolean isComplete;

    private ByteReader byteReader;

    public GameController(Grid grid, ByteReader byteReader,GameView gameView) {
        this.grid = grid;
        this.byteReader = byteReader;
        this.gameView = gameView;
        this.isComplete = false;
    }

    public void checkCompletion() {
        if (isGameComplete()) {
            isComplete = true;
            gameView.showCompletionMessage();
        }
        else {
            gameView.showNotCompleteMessage();
        }
    }

//    public void checkCompletionButton(JPanel borderPanel)  {
//        JButton checkCompletionButton = new JButton("Check If You Got It Right!");
//
//        checkCompletionButton.addActionListener(e -> {
//            checkCompletion();
//        });
//
//        borderPanel.add(checkCompletionButton, BorderLayout.SOUTH);
//    }

    public boolean isGameComplete() {
        return Arrays.equals(grid.getPixel(), byteReader.seeBMPImage());
    }
}