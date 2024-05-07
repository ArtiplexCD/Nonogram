import java.io.IOException;

public class Main
{

    public static void main(String[] args) throws IOException {
        String image = new String("elephant.bmp");

        ByteReader byteReader = new ByteReader(image);

        GameView gameView = new GameView(byteReader);
    }
}
