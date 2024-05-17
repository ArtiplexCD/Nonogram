import javax.swing.*;
import java.awt.*;

public class Pixel extends JButton {
    private State state;

    // Using as identifiers
    public enum State {
        unknown,
        shaded,
        marked
    }

    public Pixel() {
        super();

        this.state = State.unknown;

        updateColor();
    }

    public State getState() {
        return this.state;
    }

    public void setState(State state) {
        this.state = state;
        updateColor();
    }

    public void setMarkedState(Color color) {
        this.state = State.marked;
        updateMarkedColor(color);
    }

    public void setColor(Color color) {
        this.setBackground(color);
    }

    public Color getColor() {
        return this.getBackground();
    }

    public void updateMarkedColor(Color color) {
        setColor(color);
    }

    public void updateColor() {
        switch (state) {
            case unknown:
                this.setBackground(Color.decode("#D3B5E5")); // Wanted a different color
                break;

            case shaded:
                this.setBackground(Color.white);
                break;

            case marked:
                this.setBackground(Color.black);
                break;
        }
    }
}