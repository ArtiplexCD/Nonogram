import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameComplete implements ActionListener
{
    JButton button;
    GameController gameController;

    public GameComplete(GameController gameController, JPanel panel)
    {
        this.gameController = gameController;

        button = new JButton();

        button.addActionListener(this);

        panel.add(button);

        button.setBackground(Color.blue);
        button.setText("Check If Complete");
    }

    public void actionPerformed(ActionEvent e)
    {
        gameController.checkCompletion();
    }
}
