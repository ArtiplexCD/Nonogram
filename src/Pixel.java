import javax.swing.*;
import java.awt.*;

public class Pixel extends JButton {

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
    }

    public void setState(State state)
    {
        this.state = state;
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

    public State getState()
    {
        return this.state;
    }
}
