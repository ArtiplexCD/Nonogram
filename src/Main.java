import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        String elephant = "bmpFiles/elephant.bmp";
        String elephant2 = "bmpFiles/2color_elephant.bmp";
        String basketball = "bmpFiles/3color_basketball.bmp";

        String image = elephant;

        ByteReader byteReader = new ByteReader(image);

        GameView gameView = new GameView(byteReader);
    }
}
