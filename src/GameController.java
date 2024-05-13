import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class GameController
{
    private final Grid grid;
    private boolean isComplete;

    private final ByteReader byteReader;
    private final GameComplete gameComplete;

    public GameController(Grid grid, ByteReader byteReader, JPanel panel, GridLayout buttonGridLayout)
    {
        this.grid = grid;
        this.byteReader = byteReader;
        this.isComplete = false;

        this.gameComplete = new GameComplete(this, buttonGridLayout);
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