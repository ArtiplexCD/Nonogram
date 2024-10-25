import java.io.IOException;

public class Driver {

    public static void main(String[] args) throws IOException {
        String bmpFile = "bmpFiles/2colour_elephant.bmp";

        ByteReader byteReader = new ByteReader(bmpFile);

        GameView gameView = new GameView(byteReader);
    }
}
