import java.io.IOException;

public class Driver {

    public static void main(String[] args) throws IOException {
        String bmpFile = "bmpFiles/inputNameOfFileHere";

        ByteReader byteReader = new ByteReader(bmpFile);

        GameView gameView = new GameView(byteReader);
    }
}
