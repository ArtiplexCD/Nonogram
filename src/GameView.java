import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Arrays;

public class GameView extends JFrame {
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
    private Color[] colors;
    private Color selectedColor;

    public GameView(ByteReader byteReader) {
        this.byteReader = byteReader;
        this.colors = byteReader.getColors();

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

        grid = new Grid(yGridSize, xGridSize, gridPanel, this, byteReader);

        gameController = new GameController(grid, byteReader, buttonGridLayout);

        clueDisplay();
        buttonsDisplay();

        frame.setVisible(true);
    }

    public void buttonsDisplay() {
        createColorPalette();
        createCompletionButton();
        createResetButton();

        borderPanel.add(buttonPanel, BorderLayout.SOUTH);
    }

    // Button to check if game is complete
    public void createCompletionButton() {
        JButton completionButton = new JButton("See if you got it right!");

        completionButton.addActionListener(e -> gameController.checkCompletion());

        buttonPanel.add(completionButton);
    }

    // Button to reset the board
    public void createResetButton() {
        JButton resetButton = new JButton("Reset");

        resetButton.addActionListener(e -> grid.resetGrid());

        buttonPanel.add(resetButton);
    }

    public void setSelectedColor(Color color) {
        selectedColor = color;
    }

    public Color getSelectedColor() {
        return selectedColor;
    }

    public void createColorPalette() {
//        Color[] colors = byteReader.getColors();
        if (colors.length == 1) {
            return;
        }

        colorPalette = new JPanel(new FlowLayout());

        for (Color color : colors) {
            JButton button = new JButton();
            button.setBackground(color);

            selectedColor = color;

            button.addActionListener(e -> {
                JButton btn = (JButton) e.getSource();
                setSelectedColor(btn.getBackground());
            });

            button.setText(" ");
            colorPalette.add(button);

            // Had problems with seeing colors of the buttons on Mac and this fixed it
            String osName = System.getProperty("os.name");
            if (osName.toLowerCase().contains("mac")) {
                button.setOpaque(true);
            }
        }
        selectedColor = colors[0]; // Default color

        buttonPanel.add(colorPalette);
    }

    // Computes the clues into correct format
    public int[] clueCounter(int[] gridLength) {
        boolean separated = false;
        int[] clue = new int[gridLength.length];
        int n = 0;

        for (int clu : gridLength) {
            if (clu == 1) {
                clue[n]++;
                separated = false;
            } else if (clu == 0 && clue[0] != 0 && !separated) {
                separated = true;
                n++;
            }
        }

        return clue;
    }

    // Takes the placement of the column of which current needs the column for and returns the clue format
    public int[] getClueColumn(int yGridRow) {
        int[] column = new int[this.yGridSize];
        int[] byteArray = byteReader.getByteArray();

        for (int i = 0; i < this.xGridSize; i++) {
            column[i] = byteArray[i * this.yGridSize + yGridRow];
        }
        return clueCounter(column);
    }

    // Creates a panel for the top side of the panel
    public JPanel createTopCluePanel() {
        JPanel topCluePanel = new JPanel(new GridLayout(1, xGridSize));

        for (int i = 0; i < yGridSize; i++) {

            int[] row = getClueColumn(i);

            int count = 0;
            for (int r : row)
                if (r != 0)
                    count++;

            JPanel columnPanel = new JPanel(new GridLayout(count, 1));

            for (int r : row)
                if (r != 0)
                    columnPanel.add(new JLabel(String.valueOf(r)));

            topCluePanel.add(columnPanel);
        }

        Border border = new EmptyBorder(0, 80, 0, -10);
        topCluePanel.setBorder(border);
        return topCluePanel;
    }

    // Same as getClueColumn but gives a row
    public int[] getClueRow(int xGridRow) {
        int[] row = new int[this.yGridSize];
        int[] byteArray = byteReader.getByteArray();

        for (int i = 0; i < this.yGridSize; i++) {
            row[i] = byteArray[i + xGridRow * this.yGridSize];
        }

        //System.out.println(Arrays.toString(row));


        return clueCounter(row);
    }

    // Creates a panel for the left side of the panel
    public JPanel createLeftCluePanel() {
        JPanel leftCluePanel = new JPanel(new GridLayout(xGridSize, 1));

        for (int i = 0; i < xGridSize; i++) {

            int[] column = getClueRow(i);

            int count = 0;
            for (int col : column)
                if (col != 0)
                    count++;

            JPanel rowPanel = new JPanel(new GridLayout(1, count));

            boolean insertedComma = false;

            for (int c : column)
                if (c != 0) {
                    if (insertedComma)
                        rowPanel.add(new JLabel(","));

                    rowPanel.add(new JLabel(String.valueOf(c)));

                    insertedComma = true;
                }

            leftCluePanel.add(rowPanel);
        }
        Border border = new EmptyBorder(0, 10, 0, 10);
        leftCluePanel.setBorder(border);
        return leftCluePanel;
    }

    /*TODO It puts a negative number of the index of what the color is in order to color the text for the clues
    TODO / so if there is a -1 and the index 1 for color is black it will make the label text black, and if it is -2 and the index of 2 for color color is blue it will make it blue */
    public int[] clueCounterV2(int[] gridLength) {
        boolean separated = false;
        int[] clue = new int[gridLength.length];
        int n = 0;

        for (int clu : gridLength) {



            if (clu == 1) {
                clue[n]++;
                separated = false;
            }
            else if (clu == 0 && clue[0] != 0 && !separated) {
                separated = true;
                n++;
            }
        }

        return clue;
    }

    // TODO Get along with clueCounterV2
    public int[] getClueRowV2(int xGridRow) {
        int[] row = new int[this.yGridSize];
        int[] byteArray = byteReader.getByteArray();

        for (int i = 0; i < this.yGridSize; i++) {
            row[i] = byteArray[i + xGridRow * this.yGridSize];
        }

        System.out.println(Arrays.toString(row));


        return clueCounterV2(row);
    }

    // TODO work along with getClueRowV2
    public JPanel createLeftCluePanelV2() {
        JPanel leftCluePanel = new JPanel(new GridLayout(xGridSize, 1));

        for (int i = 0; i < xGridSize; i++) {

            int[] column = getClueRowV2(i);

            int count = 0;
            for (int col : column)
                if (col != 0)
                    count++;

            JPanel rowPanel = new JPanel(new GridLayout(1, count));

            boolean insertComma = false;

            for (int c : column)
                if (c != 0) {
                    if (insertComma)
                        rowPanel.add(new JLabel(","));

                    rowPanel.add(new JLabel(String.valueOf(c)));

                    insertComma = true;
                }

            leftCluePanel.add(rowPanel);
        }
        Border border = new EmptyBorder(0, 10, 0, 10);
        leftCluePanel.setBorder(border);
        return leftCluePanel;
    }

    public void clueDisplay() {
        borderPanel.add(createTopCluePanel(), BorderLayout.NORTH);
        borderPanel.add(createLeftCluePanel(), BorderLayout.WEST);
    }
}