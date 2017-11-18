import java.awt.*;

class Circle extends Point {
    float r;

    Circle() {
        super();
        r = random.nextFloat() * 100;
    }

    Circle(float px, float py, float pr) {
        super(px, py);
        r = pr;
    }

    @Override
    public boolean isInside(float px, float py) {
        return (Math.sqrt((x - px) * (x - px) + (y - py) * (y - py)) <= r);
    }

    @Override
    String getName() {
        return "Circle(" + x + ", " + y + ")";
    }

    @Override
    float computeArea() {
        return (float) Math.PI * r * r;
    }

    @Override
    float computePerimeter() {
        return (float) Math.PI * r * 2;
    }

    @Override
    void scale(float s) {
        r *= s;
    }

    @Override
    void draw(Graphics g) {
        setColor(g);
        g.drawOval((int) (x - r), (int) (y - r), (int) (2 * r), (int) (2 * r));
    }

}
