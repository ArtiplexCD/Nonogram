import java.io.FileInputStream;
import java.io.IOException;

public class ByteReader
{

    public byte[] readBytes(String filePath)
    {
        try (FileInputStream fileInputStream = new FileInputStream(filePath)) {
            byte[] bytes = new byte[(int) fileInputStream.available()];

            fileInputStream.read(bytes);

            return bytes;

        }
        catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());

            return new byte[0];
        }
    }
}