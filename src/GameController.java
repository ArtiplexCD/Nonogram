import javax.swing.*;
import java.util.Arrays;

public class GameController
{
    private final Grid grid;
    private boolean isComplete;

    private final ByteReader byteReader;
    private final GameComplete gameComplete;

    public GameController(Grid grid, ByteReader byteReader, JPanel panel, JFrame frame)
    {
        this.grid = grid;
        this.byteReader = byteReader;
        this.isComplete = false;

        this.gameComplete = new GameComplete(this, panel, frame);
    }

    public void checkCompletion()
    {
        if (isGameComplete()) {
            isComplete = true;
            gameComplete.showCompletionMessage();
            grid.gameComplete();
        }
        else {
            gameComplete.showNotCompleteMessage();
        }
    }

    public boolean isGameComplete()
    {
        return Arrays.equals(grid.getPixel(), byteReader.getByteArray());
    }
}