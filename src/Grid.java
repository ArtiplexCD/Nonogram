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

    private final GameView gameView;
    private final ByteReader byteReader;

    // The x and y grid sizes are switched as it is being read vertically
    public Grid(int xGridSize, int yGridSize, JPanel panel, GameView gameView, ByteReader byteReader) {
        this.xGridSize = yGridSize;
        this.yGridSize = xGridSize;
        this.panel = panel;
        this.gameView = gameView;
        this.byteReader = byteReader;

        renderGrid();
    }

    private void renderGrid() {
        pixels = new Pixel[xGridSize][yGridSize];

        for (int i = 0; i < xGridSize; i++) {
            for (int j = 0; j < yGridSize; j++) {

                pixels[i][j] = new Pixel();

                pixels[i][j].addActionListener(this);

                pixels[i][j].addMouseListener(this);
                // Differentiating between left and right click

                pixels[i][j].setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
                pixels[i][j].setOpaque(true);

                panel.add(pixels[i][j]);
            }
        }
    }

    public void resetGrid() {
        for (int x = 0; x < xGridSize; x++) {
            for (int y = 0; y < yGridSize; y++) {
                pixels[x][y].setState(Pixel.State.unknown);
                pixels[x][y].setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
            }
        }
    }

    //Gets the byte map of current grid
    public int[] getPixel() {
        int[] pixelArray = new int[xGridSize * yGridSize];

        int n = 0;
        int x = 0;
        int y = 0;

        while (n <= xGridSize * yGridSize - 1) {
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

    // Makes all the yellow pixels into white
    public void gameEnded() {
        incorrectSquares();

        for (Pixel[] pixel : pixels)
            for (Pixel p : pixel)
                if (p.getState() == Pixel.State.unknown)
                    p.setState(Pixel.State.shaded);
    }

    // Replaces any incorrect squares with correct squares and give them a red border
    private void incorrectSquares() {
        Color[] colorByteArray = byteReader.getColorByteArray();

        int x = 0;
        int y = 0;

        for (int i = 0; i < xGridSize * yGridSize; i++) {
            if (!colorByteArray[i].equals(Color.WHITE) && colorByteArray[i] != pixels[x][y].getColor()) {

                pixels[x][y].setMarkedState(colorByteArray[i]);

                if (!colorByteArray[i].equals(Color.YELLOW)) {
                    pixels[x][y].setBorder(BorderFactory.createLineBorder(Color.decode("#FFDF00")));
                } else
                    pixels[x][y].setBorder(BorderFactory.createLineBorder(Color.decode("#E56997")));

            } else if (colorByteArray[i] == Color.WHITE)
                pixels[x][y].setState(Pixel.State.shaded);


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

                pixel.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));

                if (lastButtonPressed == MouseEvent.BUTTON1) {

                    if (byteReader.getColors().length == 1)
                        pixel.setState(Pixel.State.marked);

                    else
                        pixel.setMarkedState(gameView.getSelectedColor());

                } else if (lastButtonPressed == MouseEvent.BUTTON3)
                    pixel.setState(Pixel.State.shaded);

                break;

            case marked:

                pixel.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));

                if (lastButtonPressed == MouseEvent.BUTTON1) {

                    if (pixel.getColor() == gameView.getSelectedColor() || byteReader.getColors().length == 1)
                        pixel.setState(Pixel.State.unknown);

                    else
                        pixel.setMarkedState(gameView.getSelectedColor());

                } else if (lastButtonPressed == MouseEvent.BUTTON3)
                    pixel.setState(Pixel.State.shaded);

                break;

            case shaded:

                pixel.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));

                if (lastButtonPressed == MouseEvent.BUTTON1) {

                    if (byteReader.getColors().length == 1)
                        pixel.setState(Pixel.State.marked);

                    else
                        pixel.setMarkedState(gameView.getSelectedColor());

                } else if (lastButtonPressed == MouseEvent.BUTTON3)
                    pixel.setState(Pixel.State.unknown);

                break;
        }
    }

    public void actionPerformed(ActionEvent e) {
        Pixel pixel = (Pixel) e.getSource();

        updateAction(pixel);
    }

    public void mouseClicked(MouseEvent e) {
        // Makes mouse dragged work by manually starting actionPerformed
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

        // makes clicking no longer work
//        if (lastButtonPressed == MouseEvent.BUTTON1)
//            actionPerformed(new ActionEvent(e.getSource(), ActionEvent.ACTION_PERFORMED, "Left Click"));
        if (lastButtonPressed == MouseEvent.BUTTON3)
            actionPerformed(new ActionEvent(e.getSource(), ActionEvent.ACTION_PERFORMED, "Right Click"));
    }

    public void mouseReleased(MouseEvent e) {
        lastButtonPressed = -1;
    }

    public void mouseEntered(MouseEvent e) {
        // Makes mouseDrag work
//        if (lastButtonPressed == MouseEvent.BUTTON1)
//            actionPerformed(new ActionEvent(e.getSource(), ActionEvent.ACTION_PERFORMED, "Left Click"));
//
//        else if (lastButtonPressed == MouseEvent.BUTTON3)
//            actionPerformed(new ActionEvent(e.getSource(), ActionEvent.ACTION_PERFORMED, "Right Click"));
    }

    public void mouseExited(MouseEvent e) {}
}