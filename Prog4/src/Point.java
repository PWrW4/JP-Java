import java.awt.*;
import java.io.Serializable;

class Point extends Figure implements Serializable {

    protected float x, y;

    Point() {
        this.x = random.nextFloat() * 400;
        this.y = random.nextFloat() * 400;
    }

    Point(float x, float y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean isInside(float px, float py) {
        // by umożliwić zaznaczanie punktu myszką
        // miejsca odległe nie więcej niż 6 leżą wewnątrz
        return (Math.sqrt((x - px) * (x - px) + (y - py) * (y - py)) <= 6);
    }


    @Override
    String getName() {
        return "Point(" + x + ", " + y + ")";
    }

    @Override
    float getX() {
        return x;
    }

    @Override
    float getY() {
        return y;
    }

    @Override
    float computeArea() {
        return 0;
    }

    @Override
    float computePerimeter() {
        return 0;
    }

    @Override
    void move(float dx, float dy) {
        x += dx;
        y += dy;
    }

    @Override
    void scale(float s) {
    }

    @Override
    void draw(Graphics g) {
        setColor(g);
        g.fillOval((int) (x - 3), (int) (y - 3), 6, 6);
    }

    String toStringXY() {
        return "(" + x + " , " + y + ")";
    }

}
