import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameComplete implements ActionListener {
    private JButton button;

    private GameController gameController;

    private JFrame frame;

    public GameComplete(GameController gameController, GridLayout buttonGridLayout) {
        this.gameController = gameController;

        button = new JButton();

        button.addActionListener(this);

        buttonGridLayout.addLayoutComponent("Check completion button", button);

        button.setText("Check If Complete");
    }

    public void showCompletionMessage() {
        JOptionPane.showMessageDialog(frame, "Congratulations you got it right!");
    }

    public void showNotCompleteMessage() {
        JOptionPane.showMessageDialog(frame, "Not quite there");
    }

    public void actionPerformed(ActionEvent e) {
        gameController.checkCompletion();
    }
}