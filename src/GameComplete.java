import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameComplete implements ActionListener
{
    JButton button;
    GameController gameController;

    JFrame frame;

    public GameComplete(GameController gameController, JPanel panel, JFrame frame)
    {
        this.gameController = gameController;

        button = new JButton();

        this.frame = frame;

        button.addActionListener(this);

        panel.add(button, BorderLayout.SOUTH);

        button.setText("Check If Complete new");
    }

    public void actionPerformed(ActionEvent e)
    {
        gameController.checkCompletion();
    }

    public void showCompletionMessage()
    {
        JLabel label = new JLabel("Congratulations!");

        frame.setTitle("Complete Hanjie Puzzle Game");
    }

    public void showNotCompleteMessage()
    {
        JLabel label = new JLabel("Not Congratulations!");

        frame.setTitle("Not Complete Hanjie Puzzle Game");
    }
}
