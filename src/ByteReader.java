import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ByteReader {
    private BufferedImage image;

    private int[] byteArray;

    private Color[] colors = new Color[0];


    public ByteReader(String BMPFileName) throws IOException {
        this.image = ImageIO.read(new File(BMPFileName));
    }

    // Trying to combine seeBMPImage and initializeColors into one method
    public void initializeBMPImage() {
        byteArray = new int[image.getWidth() * image.getHeight()];

        boolean isNewColor = false;

        int n = 0;
        int x = 0;
        int y = 0;
        int i = 0;

        while (n < image.getWidth() * image.getHeight() - 1) {

            int rgb = image.getRGB(x, y);
            Color color = new Color(rgb);

            if (color.equals(Color.white)) {
                byteArray[n] = 0;
                n++;
                x++;
                if (x == image.getWidth()) {
                    y++;
                    x = 0;
                }
                continue;
            }


            isNewColor = true;

            for (Color c : colors) {
                if (c != null && c.equals(color)) {
                    isNewColor = false;
                    break;
                }
            }

            if (isNewColor) {
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

    // Initializes byteArray with the image
    public void seeBMPImage() {
        byteArray = new int[image.getWidth() * image.getHeight()];

        int n = 0;
        int x = 0;
        int y = 0;

        while (n <= image.getWidth() * image.getHeight() - 1) {

            int rgb = image.getRGB(x, y);
            Color color = new Color(rgb);

            if (!color.equals(Color.white))
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

    public int[] getByteArray() {
        seeBMPImage();
        return byteArray;
    }

    // Gets Width because height is actually width as the ByteArray is being read from top to bottom
    public int getGridWidth() {
        return image.getHeight();
    }

    // Gets height because width is actually height as the ByteArray is being read from top to bottom
    public int getGridHeight() {
        return image.getWidth();
    }

    // Initializes colors with all the types of colors in the image provided
    public void initializeColors() {
        boolean isNewColor;

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

            isNewColor = true;

            for (Color c : colors) {
                if (c != null && c.equals(color)) {
                    isNewColor = false;
                    break;
                }
            }

            if (isNewColor) {
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

    // Increases size of colors
    public void increaseSize() {
        Color[] temp = new Color[colors.length + 1];
        System.arraycopy(colors, 0, temp, 0, colors.length);
        colors = temp;
    }

    public Color[] getColors() {
        initializeColors();
        return colors;
    }
}