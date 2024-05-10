import javax.swing.*;
import java.awt.*;

public class Pixel extends JButton
{
    private State state;
    private Color color;

    // Trying to use as identifiers
    enum State
    {
        unknown,
        shaded,
        marked;
    }

    public Pixel()
    {
        super();

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
        //if (state != State.marked)
        return this.state;
        //return ;
    }

    public void setColor(Color color)
    {
        this.color = color;
        this.setBackground(color);
    }

    public void updateColor()
    {
        switch(state) {

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

    public void updateColor(Color color)
    {
        switch(state) {

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