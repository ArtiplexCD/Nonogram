import javax.swing.*;
import java.awt.*;

public class GameController {
    private final Grid grid;
    private final GameView gameView;
    private boolean isComplete;

    public GameController(Grid grid, int gridSize, GameView gameView) {
        this.grid = grid;
        this.gameView = gameView;
        this.isComplete = false;
    }

    public void checkCompletion() {
        if (isGameComplete()) {
            isComplete = true;
            gameView.showCompletionMessage();
        }
    }

    public void checkCompletionButton(JPanel borderPanel) {
        JButton checkCompletionButton = new JButton("Check If You Got It Right!");
        checkCompletionButton.addActionListener(e -> {
            checkCompletion();
        });

        borderPanel.add(checkCompletionButton, BorderLayout.SOUTH);
    }

    public boolean isGameComplete() {
        // TODO make a checker for all rows and columns for the Nonogram



        return false;
    }
}