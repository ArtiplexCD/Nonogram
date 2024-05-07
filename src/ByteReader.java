import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ByteReader
{
    BufferedImage image;

    public ByteReader(String BMPFileName) throws IOException
    {
        this.image = ImageIO.read(new File(BMPFileName));
    }

    public int[] seeBMPImage() {

        int[] byteArray = new int[image.getWidth() * image.getHeight()];

        int n = 0;
        int x = 0;
        int y = 0;

        while (n <= image.getWidth() * image.getHeight() - 1) {

            byteArray[n] = image.getRGB(x, y);

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

//    public int[][] seeBMPIImage2DArray()
//    {
//        int[][] byteArray2D = new int[image.getWidth()][image.getHeight()];
//
//        for (int yPixel = 0; yPixel < image.getHeight(); yPixel++)
//        {
//            for (int xPixel = 0; xPixel < image.getWidth(); xPixel++)
//            {
//                int color = image.getRGB(xPixel, yPixel);
//
//                if (color == Color.black.getRGB())
//                    byteArray2D[xPixel][yPixel] = 1;
//
//                else
//                    byteArray2D[xPixel][yPixel] = 0;
//            }
//        }
//
//        return byteArray2D;
//    }

    public int getGridSize(boolean ifx) {
        return ifx ? this.image.getWidth() : this.image.getHeight();
    }
}