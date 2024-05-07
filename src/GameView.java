import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class GameView extends JFrame
{
    private GameController gameController;
    private ByteReader byteReader;

    private final int xGridSize;
    private final int yGridSize;

    private Grid grid;

    private BorderLayout borderLayout;
    private GridLayout gridLayout;

    private JPanel borderPanel;
    private JPanel gridPanel;

    private JFrame frame;


    public GameView(ByteReader byteReader) {
        this.byteReader = byteReader;

        this.xGridSize = byteReader.getGridSize(true);
        yGridSize = byteReader.getGridSize(false);

        borderLayout = new BorderLayout();
        gridLayout = new GridLayout(this.xGridSize, this.yGridSize);

        borderPanel = new JPanel(borderLayout);
        gridPanel = new JPanel(gridLayout);

        borderPanel.setLayout(borderLayout);
        borderPanel.add(gridPanel, BorderLayout.CENTER);

        frame = new JFrame("Hanjie Puzzle Game");
        frame.setContentPane(borderPanel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(750, 750);

        grid = new Grid(xGridSize, yGridSize, this.gridPanel);

        gameController = new GameController(grid, byteReader,this);

        //gameController.checkCompletionButton(borderPanel);

        GameComplete gameComplete = new GameComplete(gameController, borderPanel);
        frame.setVisible(true);
    }

    public void showCompletionMessage()
    {
        JLabel label = new JLabel("Congratulations!");

        frame.setTitle("Complete Hanjie Puzzle Game");
    }

    public void showNotCompleteMessage() {
        JLabel label = new JLabel("Not Congratulations!");

        frame.setTitle("Not Complete Hanjie Puzzle Game");
    }
}
