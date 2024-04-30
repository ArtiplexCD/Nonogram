import javax.swing.*;
import java.awt.*;

public class Pixel extends JButton {
    //private int xPos;
    //private int yPos;
    private Icon unknownIcon;

    public Pixel(String filename)
    {
        super(new ImageIcon(filename));

        unknownIcon = this.getIcon();
    }

    //public int getxPos() { return xPos; }
    //public int getyPos() { return  yPos; }
}
