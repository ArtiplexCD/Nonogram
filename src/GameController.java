import java.awt.*;
import java.util.Arrays;

public class GameController {
    private final Grid grid;
    private boolean isComplete;

    private final ByteReader byteReader;
    private final GameComplete gameComplete;

    public GameController(Grid grid, ByteReader byteReader, GridLayout buttonGridLayout) {
        this.grid = grid;
        this.byteReader = byteReader;

        this.isComplete = false;

        this.gameComplete = new GameComplete(this, buttonGridLayout);
    }

    public void checkCompletion() {
        if (isGameComplete()) {
            isComplete = true;
            gameComplete.showCompletionMessage();
            grid.gameComplete();
        } else {
            gameComplete.showNotCompleteMessage();
        }
    }

    // TODO if shaded it should be considered as unknown and just check if the marked are correct or not
    public boolean isGameComplete() {
        return Arrays.equals(grid.getPixel(), byteReader.getByteArray());
    }
}