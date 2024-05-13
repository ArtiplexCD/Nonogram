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

    private Color[] colors = new Color[0];

    public ByteReader(String BMPFileName) throws IOException
    {
        this.image = ImageIO.read(new File(BMPFileName));
    }

    public void seeBMPImage()
    {
        byteArray = new int[image.getWidth() * image.getHeight()];

        int n = 0;
        int x = 0;
        int y = 0;

        while (n <= image.getWidth() * image.getHeight() - 1) {

            int color = image.getRGB(x, y);

            if (color != Color.white.getRGB())
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
        return ifx ? image.getWidth() : image.getHeight();
    }

    public Color[] getColors()
    {
        initializeColors();
        System.out.println(Arrays.toString(colors));
        return colors;
    }

    public void initializeColors()
    {
        int n = 0;
        int i = 0;

        int x = 0;
        int y = 0;

        while (n < image.getWidth() * image.getHeight()) {

            int rgb = image.getRGB(x, y);
            Color color = new Color(rgb);
            if (color.equals(Color.white)) {
                n++;
                x++;
                if (x == image.getWidth()) {
                    y++;
                    x = 0;
                }
                continue;
            }


            boolean isNewColor = true;

            for (Color c : colors) {
                if (c != null && c.equals(color)) {
                    isNewColor = false;
                    break;
                }
            }

            if (isNewColor) {
                //System.out.println("New color: " + rgb);
                increaseSize();
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

    public void increaseSize()
    {
            Color[] temp = new Color[colors.length + 1];

            System.arraycopy(colors, 0, temp, 0, colors.length);

            colors = temp;
    }
}