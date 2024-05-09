import javax.swing.*;
import java.awt.*;

public class GameView extends JFrame
{
    private final GameController gameController;
    private final ByteReader byteReader;

    private int xGridSize;
    private int yGridSize;

    private final Grid grid;

    private BorderLayout borderLayout;
    private GridLayout gridLayout;

    private JPanel borderPanel;
    private JPanel gridPanel;

    private JFrame frame;


    public GameView(ByteReader byteReader)
    {
        this.byteReader = byteReader;

        this.xGridSize = byteReader.getGridSize(true);
        yGridSize = byteReader.getGridSize(false);

        borderLayout = new BorderLayout();
        gridLayout = new GridLayout(this.xGridSize, this.yGridSize);

        borderPanel = new JPanel(borderLayout);
        gridPanel = new JPanel(gridLayout);

        borderPanel.setLayout(borderLayout);
        borderPanel.add(gridPanel, BorderLayout.CENTER);

        clueDisplay();

        frame = new JFrame("Hanjie Puzzle Game");
        frame.setContentPane(borderPanel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(750, 750);

        grid = new Grid(xGridSize, yGridSize, this.gridPanel);

        gameController = new GameController(grid, byteReader, borderPanel, frame);

        frame.setVisible(true);
    }

    public void clueDisplay()
    {
        JPanel topCluePanel = new JPanel(new GridLayout(1, this.xGridSize));
        JPanel leftCluePanel = new JPanel(new GridLayout(this.yGridSize, 1));
        int[] byteArray = byteReader.getByteArray();
        for (int i = 0; i < this.xGridSize; i++) {
            topCluePanel.add(new Label(byteArray[i*15] + ""));
            leftCluePanel.add(new Label(byteArray[i*15] + ""));
        }
    }
}
