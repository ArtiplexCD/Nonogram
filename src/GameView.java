import javax.swing.*;
import java.awt.*;

public class GameView extends JFrame
{
    GameController gameController;

    private final int gridSize;

    private BorderLayout borderLayout;
    private GridLayout gridLayout;

    private JPanel borderPanel;
    private JPanel gridPanel;

    private JFrame frame;


    public GameView(int gridSize)
    {
        this.gridSize = gridSize;

        borderLayout = new BorderLayout();
        gridLayout = new GridLayout(this.gridSize, this.gridSize);

        borderPanel = new JPanel(borderLayout);
        gridPanel = new JPanel(gridLayout);

        borderPanel.setLayout(borderLayout);
        borderPanel.add(gridPanel, BorderLayout.CENTER);

        frame = new JFrame("Hanjie Puzzle Game");
        frame.setContentPane(borderPanel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(750, 750);

        Grid grid = new Grid(this.gridSize, gridPanel);

        gameController = new GameController(this.gridSize, this);

        gameController.checkCompletionButton(borderPanel);

        //GameComplete gameComplete = new GameComplete(gameController, panel);
        frame.setVisible(true);
    }

    public void showCompletionMessage()
    {
        JLabel label = new JLabel("Congratulations!");
        borderPanel.add(label, BorderLayout.CENTER);
    }
}
