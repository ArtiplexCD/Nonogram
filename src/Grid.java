import javax.swing.*;
import java.awt.event.*;

public class Grid implements ActionListener, MouseListener
{
    private final int xGridSize;
    private final int yGridSize;
    private final JPanel panel;

    private Pixel[][] pixels;

    private int lastButtonPressed = -1; // -1 is none, 1 is left and 3 is right

    public Grid(int xGridSize, int yGridSize, JPanel panel)
    {
        this.xGridSize = xGridSize;
        this.yGridSize = yGridSize;
        this.panel = panel;
        renderGrid();
    }

    public void renderGrid()
    {
        pixels = new Pixel[xGridSize][yGridSize];

        for (int i = 0; i < xGridSize; i++) {
            for (int j = 0; j < yGridSize; j++) {

                pixels[i][j] = new Pixel();

                String osName = System.getProperty("os.name");
                if (osName.toLowerCase().contains("mac")) { pixels[i][j].setOpaque(true); }

                pixels[i][j].addActionListener(this);

                pixels[i][j].addMouseListener(this);
                    // Differentiating between left and right click

                panel.add(pixels[i][j]);
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
            Pixel.State state = pixels[y][x].getState();

            if (state == Pixel.State.marked)
                pixelArray[n] = 1;

            else if (state == Pixel.State.shaded)
                pixelArray[n] = 2;

            else
                pixelArray[n] = 0;

            x++;
            n++;

            if (x == yGridSize) {
                x = 0;
                y++;
            }
        }

        return pixelArray;
    }

//    public int[][] getPixelArray2D()
//    {
//        int[][] pixelArray2D = new int[xGridSize][yGridSize];
//
//        for (int xPixel = 0; xPixel < xGridSize; xPixel++) {
//            for (int yPixel = yGridSize - 1; yPixel >=0; yPixel--) {
//
//                Pixel.State state = pixels[xPixel][yPixel].getState();
//
//                if (state == Pixel.State.marked)
//                    pixelArray2D[xPixel][yPixel] = 1;
//
////                else if (state == Pixel.State.shaded)
////                    pixelArray2D[xPixel][yPixel] = 2;
//
//                else
//                    pixelArray2D[xPixel][yPixel] = 0;
//            }
//        }
//        return pixelArray2D;
//    }

    public void updateAction(Pixel pixel)
    {
        Pixel.State pState = pixel.getState();
        switch (pState)
        {
            case unknown:
                if (lastButtonPressed == MouseEvent.BUTTON1)
                    pixel.setState(Pixel.State.marked);

                else if (lastButtonPressed == MouseEvent.BUTTON3)
                     pixel.setState(Pixel.State.shaded);

                break;

            case marked:
                if (lastButtonPressed == MouseEvent.BUTTON1)
                    pixel.setState(Pixel.State.unknown);

                else if (lastButtonPressed == MouseEvent.BUTTON3)
                    pixel.setState(Pixel.State.shaded);

                break;

            case shaded:
                if (lastButtonPressed == MouseEvent.BUTTON1)
                    pixel.setState(Pixel.State.marked);

                else if (lastButtonPressed == MouseEvent.BUTTON3)
                    pixel.setState(Pixel.State.unknown);

                break;
        }
    }

    public void actionPerformed(ActionEvent e)
    {
        Pixel pixel = (Pixel) e.getSource();

        updateAction(pixel);
    }

    public void mouseClicked(MouseEvent e) {
        // TODO to make mouse dragged using this is very simple would just need to include left pressed to be manually started
       if (lastButtonPressed == MouseEvent.BUTTON1)
            actionPerformed(new ActionEvent(e.getSource(), ActionEvent.ACTION_PERFORMED, "Right Click"));
    }

    public void mousePressed(MouseEvent e) {
        if (SwingUtilities.isRightMouseButton(e))
            lastButtonPressed = MouseEvent.BUTTON3;

        else if (SwingUtilities.isLeftMouseButton(e))
            lastButtonPressed = MouseEvent.BUTTON1;

        // Not sure how this works tbh but it works
        if (lastButtonPressed == MouseEvent.BUTTON3)
            actionPerformed(new ActionEvent(e.getSource(), ActionEvent.ACTION_PERFORMED, "Right Click"));
    }

    public void mouseReleased(MouseEvent e) {
        lastButtonPressed = -1;
    }

    public void mouseEntered(MouseEvent e) {
        //TODO to make mouseDrag work
        if (lastButtonPressed == MouseEvent.BUTTON1)
            actionPerformed(new ActionEvent(e.getSource(), ActionEvent.ACTION_PERFORMED, "Right Click"));
    }

    public void mouseExited(MouseEvent e) {}
}