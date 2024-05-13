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
    private GridLayout buttonGridLayout;

    private JPanel borderPanel;
    private JPanel gridPanel;
    private JPanel buttonPanel;

    private JFrame frame;

    private JPanel colorPalette;
    private Color selectedColor = Color.BLACK; // Default color

    public GameView(ByteReader byteReader)
    {
        this.byteReader = byteReader;

        xGridSize = byteReader.getGridWidth();
        yGridSize = byteReader.getGridHeight();

        borderLayout = new BorderLayout();
        gridLayout = new GridLayout(this.xGridSize, this.yGridSize);
        buttonGridLayout = new GridLayout(3, 1); // rows is how many buttons

        borderPanel = new JPanel(borderLayout);
        gridPanel = new JPanel(gridLayout);
        buttonPanel = new JPanel(buttonGridLayout);

        borderPanel.setLayout(borderLayout);
        borderPanel.add(gridPanel, BorderLayout.CENTER);

        frame = new JFrame("Hanjie Puzzle Game");
        frame.setContentPane(borderPanel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(750, 750);

        grid = new Grid(xGridSize, yGridSize, gridPanel, this, byteReader);

        gameController = new GameController(grid, byteReader, borderPanel, buttonGridLayout);

        clueDisplay();
        buttonsDisplay();

        frame.setVisible(true);
    }

    public void buttonsDisplay()
    {
        createColorPalette();
        createCompletionButton();
        createResetButton();

        borderPanel.add(buttonPanel, BorderLayout.SOUTH);
    }

    // Button to check if game is complete
    public void createCompletionButton()
    {
        JButton completionButton = new JButton("See if you got it right!");

        completionButton.addActionListener(e -> gameController.checkCompletion() );

        buttonPanel.add(completionButton);
    }

    // Button to reset the board
    public void createResetButton()
    {
        JButton resetButton = new JButton("Reset");

        resetButton.addActionListener(e -> grid.resetGrid());

        buttonPanel.add(resetButton);
    }

    public void setSelectedColor(Color color)
    {
        selectedColor = color;
    }

    public Color getSelectedColor()
    {
        return selectedColor;
    }

    public void createColorPalette()
    {
        Color[] colors = byteReader.getColors();
        if (colors.length == 1) {
            return;
        }

        colorPalette = new JPanel(new FlowLayout());

        for (Color color : colors) {
            JButton button = new JButton();
            button.setBackground(color);

            selectedColor = color;
            button.addActionListener(e -> setSelectedColor(selectedColor));
            colorPalette.add(button);

            // Had problems with seeing colors of the buttons on Mac and this fixed it
            String osName = System.getProperty("os.name");
            if (osName.toLowerCase().contains("mac")) { button.setOpaque(true); }
        }
        buttonPanel.add(colorPalette);
    }

    // Computes the clues into correct format
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

    // Takes the placement of the column of which current needs the column for and returns the clue format
    public int[] getClueColumn(int yGridRow)
    {
        int[] column = new int[this.xGridSize];
        int[] byteArray = byteReader.getByteArray();

        for (int i = 0; i < this.yGridSize; i++) {
            column[i] = byteArray[i * this.xGridSize + yGridRow];
        }

        return clueCounter(column);
    }

    // Creates a panel for the top side of the panel
    public JPanel createTopCluePanel()
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

    // Same as getClueColumn but gives a row
    public int[] getClueRow(int xGridRow)
    {
        int[] row = new int[this.xGridSize];
        int[] byteArray = byteReader.getByteArray();

        System.arraycopy(byteArray, xGridRow * this.yGridSize, row, 0, this.yGridSize);

        return clueCounter(row);
    }

    // Creates a panel for the left side of the panel
    public JPanel createLeftCluePanel()
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