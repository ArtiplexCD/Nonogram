import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class CompletationMessages
{
    private final JPanel borderPanel;
    private JLabel messageLabel;
    private Timer timer;
    private float timing = 1.0f;

    public CompletationMessages(JPanel borderPanel)
    {
        this.borderPanel = borderPanel;
    }

    public void createAndShowCorrectMessage()
    {
        messageLabel = new JLabel("You got in correct congratulations");
        borderPanel.add(messageLabel, BorderLayout.CENTER);

        timer = new Timer(50, e -> {
            timing -= 0.02f;
            if (timing <= 0) {
                timer.stop();
                borderPanel.remove(messageLabel);
            }
            else
                updateTiming();
        });

    }

    public void createAndShowIncorrectMessage()
    {
        messageLabel = new JLabel("You got it wrong");
        borderPanel.add(messageLabel, BorderLayout.CENTER);

        timer = new Timer(50, e -> {
            timing -= 0.02f;
            if (timing <= 0) {
                timer.stop();
                borderPanel.remove(messageLabel);
            }
            else
                updateTiming();
        });

    }

    private void updateTiming()
    {
        BufferedImage image = new BufferedImage(borderPanel.getWidth(), borderPanel.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = image.createGraphics();
        graphics.setComposite(AlphaComposite.SrcOver.derive(timing));
        graphics.drawImage((Image) messageLabel.getIcon(), 0, 0, null);
        graphics.dispose();
        messageLabel.setIcon(new ImageIcon(image));
    }

}
