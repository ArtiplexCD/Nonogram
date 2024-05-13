import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameComplete implements ActionListener
{
    JButton button;
    GameController gameController;

    JFrame frame;

    public GameComplete(GameController gameController, JPanel borderPanel, GridLayout buttonGridLayout)
    {
        this.gameController = gameController;

        button = new JButton();

        button.addActionListener(this);

        buttonGridLayout.addLayoutComponent("Check completion button" ,button);

        button.setText("Check If Complete");
    }

    public void actionPerformed(ActionEvent e)
    {
        gameController.checkCompletion();
    }

    public void showCompletionMessage()
    {
        JOptionPane.showMessageDialog(frame, "Congratulations you got it right!");
    }

    public void showNotCompleteMessage()
    {
        JOptionPane.showMessageDialog(frame, "Not quite there yet");
    }
}