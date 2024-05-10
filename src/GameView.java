import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

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

    private JPanel colorPalette;
    private Color selectedColor = Color.BLACK; // Default color

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

    private void createColorPalette()
    {
        colorPalette = new JPanel(new FlowLayout());

        JButton colorButton1 = new JButton();

        colorButton1.setBackground(Color.BLACK);

        colorButton1.addActionListener(e -> selectedColor = Color.BLACK);

        JButton colorButton2 = new JButton();

        colorButton2.setBackground(Color.RED);

        colorButton2.addActionListener(e -> selectedColor = Color.RED);

        colorPalette.add(colorButton1);

        colorPalette.add(colorButton2);

        add(colorPalette, BorderLayout.SOUTH);
    }

    public int[] clueCounter(int[] gridLength)
    {
        boolean seperated = false;
        int[] clue = new int[this.xGridSize];
        int n = 0;

        for (int c : gridLength) {
            if (c == 1) {
                clue[n]++;
                seperated = false;
            } else if (c == 0 && clue[0] != 0 && !seperated) {
                seperated = true;
                n++;
            }
        }

        return clue;
    }

    public int[] getClueColumn(int yGridRow)
    {
        int[] column = new int[this.xGridSize];
        int[] byteArray = byteReader.getByteArray();

        System.arraycopy(byteArray, yGridRow * this.xGridSize, column, 0, this.xGridSize);

        return clueCounter(column);
    }

    public int[] getClueRow(int xGridRow)
    {
        int[] row = new int[this.xGridSize];
        int[] byteArray = byteReader.getByteArray();

        System.arraycopy(byteArray, xGridRow * this.yGridSize, row, 0, this.xGridSize);

        return clueCounter(row);
    }

    private JPanel createTopCluePanel()
    {
        JPanel topCluePanel = new JPanel(new GridLayout(1, yGridSize));

        // TODO Fix formating

        for (int i = 0; i < xGridSize; i++) {

            JPanel columnPanel = new JPanel(new GridLayout(xGridSize, 1));

            int[] column = getClueColumn(i);

            for (int c : column)
                if (c != 0)
                    columnPanel.add(new JLabel(String.valueOf(c)));

            topCluePanel.add(columnPanel);
        }
        return topCluePanel;
    }

    private JPanel createLeftCluePanel()
    {
        JPanel leftCluePanel = new JPanel(new GridLayout(xGridSize, 1));

        for (int i = 0; i < yGridSize; i++) {

            JPanel rowPanel = new JPanel(new GridLayout(1, yGridSize));

            int[] row = getClueRow(i);
            boolean insertedRow = false;

            for (int r : row)
                if (r != 0) {
                    if (insertedRow)
                        rowPanel.add(new JLabel(","));

                    rowPanel.add(new JLabel(String.valueOf(r)));

                    insertedRow = true;
                }


            leftCluePanel.add(rowPanel);
        }
        return leftCluePanel;
    }

    public void clueDisplay()
    {
        borderPanel.add(createTopCluePanel(), BorderLayout.NORTH);
        borderPanel.add(createLeftCluePanel(), BorderLayout.WEST);
    }
}