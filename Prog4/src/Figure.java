import java.awt.*;
import java.io.Serializable;
import java.util.Random;

abstract class Figure implements Serializable {

    static Random random = new Random();

    private boolean selected = false;

    public boolean isSelected() {
        return selected;
    }

    public void select() {
        selected = true;
    }

    public void select(boolean z) {
        selected = z;
    }

    public void unselect() {
        selected = false;
    }

    protected void setColor(Graphics g) {
        if (selected) g.setColor(Color.RED);
        else g.setColor(Color.BLACK);
    }

    public abstract boolean isInside(float px, float py);

    public boolean isInside(int px, int py) {
        return isInside((float) px, (float) py);
    }

    protected String properties() {
        String s = String.format("  Pole: %.0f  Obwod: %.0f", computeArea(), computePerimeter());
        if (isSelected()) s = s + "   [SELECTED]";
        return s;
    }
    
    abstract String getName();

    abstract float getX();

    abstract float getY();

    abstract float computeArea();

    abstract float computePerimeter();

    abstract void move(float dx, float dy);

    abstract void scale(float s);

    abstract void draw(Graphics g);

    @Override
    public String toString() {
        return getName();
    }

}
