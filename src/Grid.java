import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Grid implements ActionListener {
    private final Pixel[][] pixels = new Pixel[15][15];
    private Pixel unknownPixel;
    private Pixel whitePixel;
    private Pixel blackPixel;

    public Grid()
    {
        JPanel panel = new JPanel();
        JFrame frame = new JFrame();
        GridLayout layout = new GridLayout(3, 4);

        panel.setLayout(layout);
        frame.setContentPane(panel);
        frame.setSize(525, 525);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        for (int i = 0; i < pixels.length; i++) {
            for (int j = 0; j < pixels.length; j++) {
                pixels[i][j] = new Pixel();
            }
        }

        frame.setVisible(true);
    }
    public void actionPerformed(ActionEvent e) {
        Pixel p = (Pixel) e.getSource();
        if (p == unknownPixel) {
            //p = /* TODO if left pressed */ ? blackPixel : whitePixel; // TODO exchange pixel with blackPixel || whitePixel
        }
        if (p == blackPixel /* TODO && left mouse pressed */) {
            // TODO exchange pixel with unknownPixel
        }
        if (p == whitePixel /* TODO && right mouse is pressed */) {
            // TODO exchange pixel with unknownPixel
        }
    }
}