import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Grid implements ActionListener, MouseListener {
    private int xGridSize;
    private int yGridSize;
    private final JPanel panel;

    private Pixel[][] pixels;

    private int lastButtonPressed = -1; // -1 is none, 1 is left and 3 is right

    private GameView gameView;
    private ByteReader byteReader;

    public Grid(int xGridSize, int yGridSize, JPanel panel, GameView gameView, ByteReader byteReader) {
        this.xGridSize = xGridSize;
        this.yGridSize = yGridSize;
        this.panel = panel;
        this.gameView = gameView;
        this.byteReader = byteReader;

        renderGrid();
    }

    public void renderGrid() {
        pixels = new Pixel[xGridSize][yGridSize];

        for (int i = 0; i < xGridSize; i++) {
            for (int j = 0; j < yGridSize; j++) {

                pixels[i][j] = new Pixel();

                // Had problems with seeing colors of the buttons on Mac and this fixed it
                String osName = System.getProperty("os.name");
                if (osName.toLowerCase().contains("mac")) {
                    pixels[i][j].setOpaque(true);
                }

                pixels[i][j].addActionListener(this);

                pixels[i][j].addMouseListener(this);
                // Differentiating between left and right click

                panel.add(pixels[i][j]);
            }
        }
    }

    public void resetGrid() {
        for (int x = 0; x < xGridSize; x++) {
            for (int y = 0; y < yGridSize; y++) {
                pixels[x][y].setState(Pixel.State.unknown);
                pixels[x][y].setBorder(BorderFactory.createLineBorder(Color.gray));
            }
        }
    }

    //Gets the byte map of marked game
    public int[] getPixel() {
        int[] pixelArray = new int[xGridSize * yGridSize];

        int n = 0;
        int x = 0;
        int y = 0;

        while (n <= xGridSize * yGridSize - 1) {
            /* Checks if it is one of the colors available and if not it returns 0
             if yes it gives appropriate index for color */
            pixelArray[n] = byteReader.getColorsIndex(pixels[x][y].getColor());

            y++;
            n++;

            if (y == yGridSize) {
                y = 0;
                x++;
            }
        }
        return pixelArray;
    }

    // Makes all of the yellow pixels into white
    // TODO should now give a red outline on the incorrect or missed pixels and show the correct solution
    public void gameEnded() {
        incorrectSquares();

        for (Pixel[] pixel : pixels) {
            for (Pixel p : pixel) {
                if (p.getState() == Pixel.State.unknown) {
                    p.setState(Pixel.State.shaded);
                }
            }
        }
    }

    // Replaces any incorrect squares with correct squares and give them a red border
    public void incorrectSquares() {
        Color[] colorByteArray = byteReader.getColorByteArray();

        int x = 0;
        int y = 0;

        for (int i = 0; i < xGridSize * yGridSize; i++) {
            if (colorByteArray[i] != pixels[x][y].getColor() && !colorByteArray[i].equals(Color.WHITE)) {
                pixels[x][y].setMarkedState(colorByteArray[i]);
                pixels[x][y].setBorder(BorderFactory.createLineBorder(Color.RED));
            }

            // printing it sideways as the array itself is sideways
            y++;
            if (y == yGridSize) {
                y = 0;
                x++;
            }
        }
    }

    // Updates pixels into appropriate State and color
    public void updateAction(Pixel pixel) {
        Pixel.State pState = pixel.getState();

        switch (pState) {
            case unknown:

                pixel.setBorder(BorderFactory.createLineBorder(Color.gray));

                if (lastButtonPressed == MouseEvent.BUTTON1) {


                    if (byteReader.getColors().length == 1)
                        pixel.setState(Pixel.State.marked);

                    else
                        pixel.setMarkedState(gameView.getSelectedColor());
                }
                else if (lastButtonPressed == MouseEvent.BUTTON3)
                    pixel.setState(Pixel.State.shaded);

                break;

            case marked:

                pixel.setBorder(BorderFactory.createLineBorder(Color.gray));

                if (lastButtonPressed == MouseEvent.BUTTON1) {

                    if (pixel.getColor() == gameView.getSelectedColor() || byteReader.getColors().length == 1)
                        pixel.setState(Pixel.State.unknown);

                    else
                        pixel.setMarkedState(gameView.getSelectedColor());
                }

                else if (lastButtonPressed == MouseEvent.BUTTON3)
                    pixel.setState(Pixel.State.shaded);

                break;

            case shaded:

                pixel.setBorder(BorderFactory.createLineBorder(Color.gray));

                if (lastButtonPressed == MouseEvent.BUTTON1)

                    if (byteReader.getColors().length == 1)
                        pixel.setState(Pixel.State.marked);

                    else
                        pixel.setMarkedState(gameView.getSelectedColor());

                else if (lastButtonPressed == MouseEvent.BUTTON3)
                    pixel.setState(Pixel.State.unknown);

                break;
        }
    }

    public void actionPerformed(ActionEvent e) {
        Pixel pixel = (Pixel) e.getSource();

        updateAction(pixel);
    }

    public void mouseClicked(MouseEvent e) {
        // TODO to make mouse dragged using this is very simple would just need to include left pressed to be manually started
//        if (lastButtonPressed == MouseEvent.BUTTON1)
//            actionPerformed(new ActionEvent(e.getSource(), ActionEvent.ACTION_PERFORMED, "Left Click"));
//
//        else if (lastButtonPressed == MouseEvent.BUTTON3)
//            actionPerformed(new ActionEvent(e.getSource(), ActionEvent.ACTION_PERFORMED, "Right Click"));
    }

    public void mousePressed(MouseEvent e) {
        if (SwingUtilities.isLeftMouseButton(e))
            lastButtonPressed = MouseEvent.BUTTON1;
        else if (SwingUtilities.isRightMouseButton(e))
            lastButtonPressed = MouseEvent.BUTTON3;

//        if (lastButtonPressed == MouseEvent.BUTTON1)
//            actionPerformed(new ActionEvent(e.getSource(), ActionEvent.ACTION_PERFORMED, "Left Click"));
        if (lastButtonPressed == MouseEvent.BUTTON3)
            actionPerformed(new ActionEvent(e.getSource(), ActionEvent.ACTION_PERFORMED, "Right Click"));
    }

    public void mouseReleased(MouseEvent e) {
        lastButtonPressed = -1;
    }

    public void mouseEntered(MouseEvent e) {
        //TODO to make mouseDrag work
//        if (lastButtonPressed == MouseEvent.BUTTON1)
//            actionPerformed(new ActionEvent(e.getSource(), ActionEvent.ACTION_PERFORMED, "Left Click"));
//
//        else if (lastButtonPressed == MouseEvent.BUTTON3)
//            actionPerformed(new ActionEvent(e.getSource(), ActionEvent.ACTION_PERFORMED, "Right Click"));
    }

    public void mouseExited(MouseEvent e) {}
}