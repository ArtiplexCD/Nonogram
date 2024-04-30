import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Grid implements ActionListener {
    private final int gridSize = 5;
    private final Pixel[][] pixels = new Pixel[gridSize][gridSize];

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
                if (i != 0 && j != 0) {
                    panel.add(pixels[i][j]);
                }
                pixels[i][j].addActionListener(this);
            }
        }

        frame.setSize(750, 750);
        frame.setVisible(true);
    }
    public void actionPerformed(ActionEvent e) {
        Pixel p = (Pixel) e.getSource();
        Pixel.State pState = p.getState();
        switch (pState) {
            case unknown:
                p.setState(Pixel.State.marked);
        }

        //if (p == unknownPixel) {
            //p = /* TODO if left pressed */ ? blackPixel : whitePixel; // TODO exchange pixel with blackPixel || whitePixel

        //if (p == blackPixel /* TODO && left mouse pressed */) {
            // TODO exchange pixel with unknownPixel

        //if (p == whitePixel /* TODO && right mouse is pressed */) {
            // TODO exchange pixel with unknownPixel

    }
}