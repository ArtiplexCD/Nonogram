import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class GameView extends JFrame {
    private GameController gameController;
    private final ByteReader byteReader;

    private int xGridSize;
    private int yGridSize;

    private Grid grid;

    private JFrame frame;
    private final int frameWidth = 750;
    private final int frameHeight = 750;

    private BorderLayout borderLayout;
    private GridLayout gridLayout;
    private GridLayout buttonGridLayout;

    private int buttonGridRows = 3;
    private final int buttonGridColumns = 1;

    private JPanel borderPanel;
    private JPanel gridPanel;
    private JPanel buttonPanel;

    private Color selectedColor;
    private final Color[] colors;
    private int[] clueColor;

    private JPanel leftCluePanel;

    public GameView(ByteReader byteReader) {
        this.byteReader = byteReader;
        this.colors = byteReader.getColors();

        xGridSize = byteReader.getGridWidth();
        yGridSize = byteReader.getGridHeight();

        initUI();
        initClasses();
        initClues();
        initButtons();

        frame.setVisible(true);
    }

    private void initUI() {
        borderLayout = new BorderLayout();
        gridLayout = new GridLayout(this.xGridSize, this.yGridSize);

        if (colors.length == 1) buttonGridRows = 2;
        buttonGridLayout = new GridLayout(buttonGridRows, buttonGridColumns);

        borderPanel = new JPanel(borderLayout);
        gridPanel = new JPanel(gridLayout);
        buttonPanel = new JPanel(buttonGridLayout);

        frame = new JFrame("Hanjie Puzzle Game");
        frame.setContentPane(borderPanel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(frameWidth, frameHeight);

        borderPanel.setLayout(borderLayout);
        borderPanel.add(gridPanel, BorderLayout.CENTER);
    }

    private void initClasses() {
        grid = new Grid(xGridSize, yGridSize, gridPanel, this, byteReader);
        gameController = new GameController(grid, byteReader);
    }

    private void initButtons() {
        initColorPalette();
        createCompletionButton();
        createResetButton();

        borderPanel.add(buttonPanel, BorderLayout.SOUTH);
    }

    // Button to check if game is complete
    private void createCompletionButton() {
        JButton completionButton = new JButton("See if you got it right!");

        completionButton.addActionListener(e -> gameController.checkCompletion());

        buttonPanel.add(completionButton);
    }

    // Button to reset the board
    private void createResetButton() {
        JButton resetButton = new JButton("Reset");

        resetButton.addActionListener(e -> grid.resetGrid());

        buttonPanel.add(resetButton);
    }

    private void initColorPalette() {
        if (colors.length == 1) return;

        JPanel colorPalette = new JPanel(new FlowLayout());

        for (Color color : colors) {
            JButton button = new JButton();
            button.setBackground(color);

            selectedColor = color;

            // Sets the selectedColor to selected color
            button.addActionListener(e -> {
                JButton btn = (JButton) e.getSource();
                setSelectedColor(btn.getBackground());
            });

            button.setText(" ");
            colorPalette.add(button);
            button.setOpaque(true);
        }
        selectedColor = colors[0]; // Default color

        buttonPanel.add(colorPalette);
    }

    // Computes the clues into correct format and gives color for the clues
    private int[] clueCounter(int[] gridLength) {
        clueColor = new int[gridLength.length];
        int[] clue = new int[gridLength.length];
        Color[] colors = byteReader.getColors();

        boolean separated = false;
        int n = 0;

        for (int i = 0; i < gridLength.length; i++) {

            // If not white
            if (gridLength[i] != 0) {

                // If gridLength[i] == gridLength[i-1]
                if (i != 0 && (gridLength[i] == gridLength[i - 1])) {
                    clue[n]++;
                    separated = false;

                    // if  gridLength[i] == gridLength[i-1] is not true checks
                } else if (i != 0){
                    separated = true;
                    n++;
                    clue[n]++;

                } else
                    clue[n]++;

            } else if (!separated && i != 0) {
                separated = true;
                n++;
            }

            for (Color color : colors)
                if (byteReader.getColorsIndex(color) == gridLength[i])
                    clueColor[n] = byteReader.getColorsIndex(color);


        }
        return clue;
    }

    // Takes the placement of the column of which current needs the column for and returns the clue format
    private int[] getClueColumn(int yGridRow) {
        int[] column = new int[this.yGridSize];
        int[] byteArray = byteReader.getByteArray();

        for (int i = 0; i < this.xGridSize; i++)
            column[i] = byteArray[i * this.yGridSize + yGridRow];

        return clueCounter(column);
    }

    // Creates a panel for the top side of the panel
    private JPanel initTopCluePanel() {
        JPanel topCluePanel = new JPanel(new GridLayout(1, xGridSize));

        for (int i = 0; i < yGridSize; i++) {

            int[] column = getClueColumn(i);

            // Removes white space between the topCluePanel and grid
            int count = 0;
            for (int col : column)
                if (col != 0)
                    count++;

            JPanel columnPanel = new JPanel(new GridLayout(count, 1));

            for (int j = 0; j < column.length; j++) {
                if (column[j] != 0) {
                    JLabel columnLabel = new JLabel(String.valueOf(column[j]));
                    columnLabel.setAlignmentY(BOTTOM_ALIGNMENT);
                    columnPanel.add(columnLabel);

                    for (int colorIndex = 0; colorIndex < colors.length; colorIndex++)
                        if (clueColor[j] == colorIndex + 1)
                            columnLabel.setForeground(colors[clueColor[j] - 1]);
                }
            }
            topCluePanel.add(columnPanel);
        }
        leftCluePanel.setVisible(true);
        Border border = new EmptyBorder(0, leftCluePanel.getPreferredSize().width + 18, 0, -18);
        topCluePanel.setBorder(border);

        return topCluePanel;
    }

    // Same as getClueColumn but gives a row
    private int[] getClueRow(int xGridRow) {
        int[] row = new int[this.yGridSize];
        int[] byteArray = byteReader.getByteArray();

        for (int i = 0; i < this.yGridSize; i++)
            row[i] = byteArray[i + xGridRow * this.yGridSize];

        return clueCounter(row);
    }

    // Creates a panel for the left side of the panel
    private JPanel initLeftCluePanel() {
        leftCluePanel = new JPanel(new GridLayout(xGridSize, 1));

        for (int i = 0; i < xGridSize; i++) {

            int[] row = getClueRow(i);

            int count = 0;
            for (int r : row)
                if (r != 0)
                    count++;

            JPanel rowPanel = new JPanel(new GridLayout(1, count));
            rowPanel.setAlignmentX(RIGHT_ALIGNMENT);
            rowPanel.setPreferredSize(new Dimension(count * 18, 0));

            boolean insertComma = false;

            for (int j = 0; j < row.length; j++) {
                if (row[j] != 0) {
                    JLabel rowLabel = new JLabel(String.valueOf(row[j]));

                    for (int colorIndex = 0; colorIndex < colors.length; colorIndex++)
                        if (clueColor[j] == colorIndex + 1)
                            rowLabel.setForeground(colors[clueColor[j] - 1]);

                    if (insertComma)
                        rowPanel.add(new JLabel(","));
                    insertComma = true;

                    rowPanel.add(rowLabel);
                }
            }
            leftCluePanel.add(rowPanel);
        }
        Border border = new EmptyBorder(0, 10, 0, 10);
        leftCluePanel.setBorder(border);

        return leftCluePanel;
    }

    private void initClues() {
        borderPanel.add(initLeftCluePanel(), BorderLayout.WEST);
        borderPanel.add(initTopCluePanel(), BorderLayout.NORTH);
    }

    public void setSelectedColor(Color color) {
        selectedColor = color;
    }

    public Color getSelectedColor() {
        return selectedColor;
    }
}