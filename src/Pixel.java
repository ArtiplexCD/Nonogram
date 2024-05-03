import javax.swing.*;
import java.awt.*;

public class Pixel extends JButton {

    // Trying to use as identifiers
    enum State
    {
        unknown,
        shaded,
        marked
    }

    private State state;

    public Pixel()
    {
        super();

        state = State.unknown;
        this.state = State.unknown;

        updateColor();
    }

    public void setState(State state)
    {
        this.state = state;
        updateColor();
    }

    public State getState()
    {
        return this.state;
    }

    public void updateColor()
    {
        switch(state) {

            case unknown:
                setBackground(Color.yellow);
                break;

            case marked:
                setBackground(Color.black);
                break;

            case shaded:
                setBackground(Color.white);
                break;
        }

    }

}