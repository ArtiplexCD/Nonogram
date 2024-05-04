import javax.swing.*;
import java.awt.event.*;


public class Grid implements ActionListener
{
    private final int gridSize;
    private final JPanel panel;

    private int lastButtonPressed = -1; // -1 is none, 1 is left and 3 is right

    public Grid(int gridSize, JPanel panel)
    {
        this.gridSize = gridSize;
        this.panel = panel;
        renderGrid();
    }

    public void renderGrid()
    {
        Pixel[][] pixels = new Pixel[gridSize][gridSize];

        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {

                pixels[i][j] = new Pixel();

                pixels[i][j].addActionListener(this);

                pixels[i][j].addMouseListener(new MouseAdapter() {
                    // Differentiating between left and right click
                    public void mousePressed(MouseEvent e) {
                        if (SwingUtilities.isRightMouseButton(e))
                            lastButtonPressed = MouseEvent.BUTTON3;

                        else if (SwingUtilities.isLeftMouseButton(e))
                            lastButtonPressed = MouseEvent.BUTTON1;

                        // Not sure how this works tbh but it works
                        if (lastButtonPressed == MouseEvent.BUTTON3)
                            actionPerformed(new ActionEvent(e.getSource(), ActionEvent.ACTION_PERFORMED, "Right Click"));
                    }
                });

                panel.add(pixels[i][j]);
            }
        }
    }

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
}