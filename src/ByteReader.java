import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

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

    public void seeBMPImage()
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
    }

    public int[] getByteArray()
    {
        seeBMPImage();
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

        while (n < image.getWidth() * image.getHeight()) {

            int color = image.getRGB(x, y);
            int white = (color >> 24) & 0xff;

            boolean newColor = true;

            for (int c : colors) {
                if ((c == color) || (color == white)) {
                    newColor = false;
                    break;
                }
            }

            if (newColor /*&& colors.length != 0 */) {
                increaseSize();

                colors[i] = color;

                i++;
            }
            System.out.println("Color: " + Arrays.toString(colors));

            n++;
            x++;

            if (x == image.getWidth()) {
                y++;
                x = 0;
            }
        }
    }

    public void increaseSize() {
        int[] temp = new int[colors.length + 1];

        System.arraycopy(colors, 0, temp, 0, colors.length);

        colors = temp;
    }
}