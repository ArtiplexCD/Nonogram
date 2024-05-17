import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class GameController {
    private final Grid grid;
    private final ByteReader byteReader;
    private JFrame frame;

    public GameController(Grid grid, ByteReader byteReader) {
        this.grid = grid;
        this.byteReader = byteReader;
    }

    public void checkCompletion() {
        if (isGameComplete()) {
            showCompletionMessage();
            grid.gameEnded();
        } else {
            showNotCompleteMessage();
            grid.gameEnded();
        }
    }

    public boolean isGameComplete() {
        return Arrays.equals(grid.getPixel(), byteReader.getByteArray());
    }

    public void showCompletionMessage() {
        JOptionPane.showMessageDialog(frame, "Congratulations you got it right!");
    }

    public void showNotCompleteMessage() {
        JOptionPane.showMessageDialog(frame, "Not quite there");
    }
}