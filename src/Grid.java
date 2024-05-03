import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class Grid implements ActionListener {
    private final int gridSize = 5;
    private final Pixel[][] pixels = new Pixel[gridSize][gridSize];
    private int lastButtonPressed = -1; // -1 is none, 1 is left and 3 is right

    public Grid()
    {
        JPanel panel = new JPanel();
        JFrame frame = new JFrame();
        GridLayout layout = new GridLayout(gridSize, gridSize);

        panel.setLayout(layout);
        frame.setContentPane(panel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        for (int i = 0; i < pixels.length; i++) {
            for (int j = 0; j < pixels.length; j++) {

                pixels[i][j] = new Pixel();
                pixels[i][j].setBackground(Color.yellow);

                //TODO Change if on mac to ture or false on windows because you can see what color it is on mac if true
                pixels[i][j].setOpaque(true);

                if (i != 0 && j != 0) {
                    panel.add(pixels[i][j]);
                }
                pixels[i][j].addActionListener(this);

                pixels[i][j].addMouseListener(new MouseAdapter() {
                    public void mousePressed(MouseEvent e) { lastButtonPressed = e.getButton(); }
                });
        }

        frame.setSize(750, 750);
        frame.setVisible(true);
        }
    }

    public void actionPerformed(ActionEvent e) {
        Pixel p = (Pixel) e.getSource();
        Pixel.State pState = p.getState();
        switch (pState) {
            // TODO Figure out how to differentiate between left and right click
            case unknown:
                if (lastButtonPressed == 1)
                    p.setState(Pixel.State.marked);
                else
                    p.setState(Pixel.State.shaded);
                break;
            case marked:
                if (lastButtonPressed == 1)
                    p.setState(Pixel.State.unknown);
                else
                    p.setState(Pixel.State.shaded);
                break;
            case shaded:
                if (lastButtonPressed == 1)
                    p.setState(Pixel.State.marked);
                else
                    p.setState(Pixel.State.unknown);
                break;
        }
    }
}