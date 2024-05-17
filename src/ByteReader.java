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
        initBMPImage();
    }

    // Initializes byteArray with the image and initializes colors with all the types of colors in the image provided
    private void initBMPImage() {
        byteArray = new int[image.getWidth() * image.getHeight()];

        boolean isNewColor;

        int n = 0;
        int x = 0;
        int y = 0;
        int i = 0;

        while (n < image.getWidth() * image.getHeight()) {

            int rgb = image.getRGB(x, y);
            Color color = new Color(rgb);

            if (color.equals(Color.white)) {

                byteArray[n] = 0;

                n++;
                x++;

                if (x == image.getWidth()) {
                    x = 0;
                    y++;
                }
                continue;
            }

            isNewColor = true;

            for (int index = 0; index < colors.length; index++) {
                byteArray[n] = index + 1;
                if (colors[index] != null && colors[index].equals(color)) {
                    isNewColor = false;
                    break;
                }
            }

            for (Color c : colors) {
                if (c != null && c.equals(color)) {
                    isNewColor = false;
                    break;
                }
            }

            if (isNewColor) {
                byteArray[n] += 1;
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

    // Increases size of the variable colors
    private void increaseSize() {
        Color[] temp = new Color[colors.length + 1];
        System.arraycopy(colors, 0, temp, 0, colors.length);
        colors = temp;
    }

    public int[] getByteArray() {
        return byteArray;
    }

    public Color[] getColorByteArray() {
        Color[] colorArray = new Color[byteArray.length];

        for (int i = 0; i < byteArray.length; i++) {
            if (byteArray[i] == 0) {
                colorArray[i] = Color.WHITE;
                continue;
            }
            colorArray[i] = getColor(byteArray[i]);
        }
        return colorArray;
    }

    // Gets Width because height is actually width as the ByteArray is being read from top to bottom
    public int getGridWidth() {
        return image.getHeight();
    }

    // Gets height because width is actually height as the ByteArray is being read from top to bottom
    public int getGridHeight() {
        return image.getWidth();
    }

    public Color[] getColors() {
        return colors;
    }

    public Color getColor(int index) {
        return colors[index - 1];
    }

    public int getColorsIndex(Color color) {
        for (int i = 0; i < colors.length; i++)
            if (colors[i].equals(color))
                return i + 1;
        return 0;
    }
}