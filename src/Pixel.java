import javax.swing.*;
import java.awt.*;

public class Pixel extends JButton {
    private State state;

    // Trying to use as identifiers
    enum State {
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
        updateColor(color);
    }

    public void setColor(Color color) {
        this.setBackground(color);
    }

    public void updateColor() {
        switch (state) {
            case unknown:
                this.setBackground(Color.decode("#FFDF00"));
                break;

            case shaded:
                this.setBackground(Color.white);
                break;

            case marked:
                this.setBackground(Color.black);
                break;
        }
    }

    public void updateColor(Color color) {
        switch (state) {

            case unknown:
                this.setBackground(Color.decode("#FFDF00"));
                break;

            case shaded:
                this.setBackground(Color.white);
                break;

            case marked:
                setColor(color);
                break;
        }
    }
}