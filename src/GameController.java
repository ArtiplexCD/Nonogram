import java.awt.*;
import java.util.Arrays;

public class GameController {
    private final Grid grid;
    private final ByteReader byteReader;
    private final GameComplete gameComplete;

    public GameController(Grid grid, ByteReader byteReader, GridLayout buttonGridLayout) {
        this.grid = grid;
        this.byteReader = byteReader;

        this.gameComplete = new GameComplete(this, buttonGridLayout);
    }

    public void checkCompletion() {
        if (isGameComplete()) {
            gameComplete.showCompletionMessage();
            grid.gameEnded();
        } else {
            gameComplete.showNotCompleteMessage();
            grid.gameEnded();
        }
    }

    public boolean isGameComplete() {
        return Arrays.equals(grid.getPixel(), byteReader.getByteArray());
    }
}