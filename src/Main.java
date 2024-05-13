import java.io.IOException;

public class Main
{

    public static void main(String[] args) throws IOException
    {
        String image = "bmpFiles/2colour_elephant.bmp";

        ByteReader byteReader = new ByteReader(image);

        GameView gameView = new GameView(byteReader);
    }
}
