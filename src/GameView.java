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

        xGridSize = byteReader.getGridSize(true);
        yGridSize = byteReader.getGridSize(false);

        borderLayout = new BorderLayout();
        gridLayout = new GridLayout(this.xGridSize, this.yGridSize);
        buttonGridLayout = new GridLayout(2, 1);

        borderPanel = new JPanel(borderLayout);
        gridPanel = new JPanel(gridLayout);
        buttonPanel = new JPanel(buttonGridLayout);

        borderPanel.setLayout(borderLayout);
        borderPanel.add(gridPanel, BorderLayout.CENTER);

        frame = new JFrame("Hanjie Puzzle Game");
        frame.setContentPane(borderPanel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(750, 750);

        grid = new Grid(xGridSize, yGridSize, gridPanel);

        gameController = new GameController(grid, byteReader, borderPanel, buttonGridLayout);

        clueDisplay();

        createColorPalette();
        createCompletionButton();
        buttonsDisplay();

        frame.setVisible(true);
    }

    public void buttonsDisplay()
    {
        borderPanel.add(buttonPanel, BorderLayout.SOUTH);
    }

    public void createCompletionButton()
    {
        JButton completionButton = new JButton("See if you got it right!");

        completionButton.addActionListener(e -> gameController.checkCompletion() );

        buttonPanel.add(completionButton);
    }

    public void setSelectedColor(Color color)
    {
        selectedColor = color;
    }

    public void createColorPalette()
    {
        colorPalette = new JPanel(new FlowLayout());

        Color[] colors = byteReader.getColors();

        for (int i = 0; i < colors.length; i++) {
            JButton button = new JButton();
            button.setBackground(colors[i]);
            selectedColor = colors[i];
            button.addActionListener(e -> setSelectedColor(selectedColor));
            colorPalette.add(button);
        }

//        JButton colorButton1 = new JButton();
//
//        colorButton1.setBackground(Color.BLACK);
//        colorButton1.addActionListener(e -> selectedColor = Color.BLACK);
//
//        JButton colorButton2 = new JButton();
//
//        colorButton2.setBackground(Color.RED);
//        colorButton2.addActionListener(e -> selectedColor = Color.RED);
//
//        colorPalette.add(colorButton1);
//        colorPalette.add(colorButton2);

        buttonPanel.add(colorPalette);
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

        System.out.println("xGridSize" + xGridSize);
        System.out.println("yGridSize" + yGridSize);

        System.arraycopy(byteArray, yGridRow * xGridSize, column, 0, xGridSize);

//        for (int i = 0; i < this.yGridSize; i++) {
//            column[i] = byteArray[i * this.xGridSize + yGridRow];
//        }

        return clueCounter(column);
    }

    public int[] getClueRow(int xGridRow)
    {
        int[] row = new int[this.xGridSize];
        int[] byteArray = byteReader.getByteArray();

        System.arraycopy(byteArray, xGridRow * yGridSize, row, 0, yGridSize);

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