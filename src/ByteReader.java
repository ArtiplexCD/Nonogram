import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ByteReader
{
    private BufferedImage image;

    private int[] byteArray;

    private int[] colors = new int[0];

    public ByteReader(String BMPFileName) throws IOException
    {
        this.image = ImageIO.read(new File(BMPFileName));

        initializeColors();
    }

    public int[] seeBMPImage()
    {
        byteArray = new int[image.getWidth() * image.getHeight()];

        int n = 0;
        int x = 0;
        int y = 0;

        while (n <= image.getWidth() * image.getHeight() - 1) {

            int color = image.getRGB(x, y);

            if (color == Color.black.getRGB())
                byteArray[n] = 1;

            else
                byteArray[n] = 0;

            n++;
            x++;

            if (x == image.getWidth()) {
                y++;
                x = 0;
            }
        }

        return byteArray;
    }

    public int[] getByteArray()
    {
        return byteArray;
    }

    public int getGridSize(boolean ifx)
    {
        return ifx ? this.image.getWidth() : this.image.getHeight();
    }

    public int[] getColors()
    {
        return colors;
    }

    public void initializeColors()
    {
        int n = 0;
        int i = 0;

        int x = 0;
        int y = 0;

        while (n <= image.getWidth() * image.getHeight() - 1) {

            int color = image.getRGB(x, y);

            boolean newColor = true;

            for (int c : colors) {
                if (c == color) {
                    newColor = false;
                    break;
                }
            }

            if (newColor && colors.length != 0) {
                int[] col = colors;
                colors = new int[colors.length + 1];
                colors = col;
                //TODO Fix this
                colors[i] = color;
                i++;
            }

            n++;
            x++;

            if (x == image.getWidth()) {
                y++;
                x = 0;
            }
        }
    }
}