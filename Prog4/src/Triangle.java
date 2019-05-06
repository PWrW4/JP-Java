import java.awt.*;
import java.io.Serializable;

class Triangle extends Figure implements Serializable {
    Point point1, point2, point3;

    Triangle() {
        point1 = new Point();
        point2 = new Point();
        point3 = new Point();
    }

	Triangle(Point p1, Point p2, Point p3) {
        point1 = p1;
        point2 = p2;
        point3 = p3;
    }

    @Override
    public boolean isInside(float px, float py) {
        float d1, d2, d3;
        d1 = px * (point1.y - point2.y) + py * (point2.x - point1.x) +
                (point1.x * point2.y - point1.y * point2.x);
        d2 = px * (point2.y - point3.y) + py * (point3.x - point2.x) +
                (point2.x * point3.y - point2.y * point3.x);
        d3 = px * (point3.y - point1.y) + py * (point1.x - point3.x) +
                (point3.x * point1.y - point3.y * point1.x);
        return ((d1 <= 0) && (d2 <= 0) && (d3 <= 0)) || ((d1 >= 0) && (d2 >= 0) && (d3 >= 0));
    }

    @Override
    String getName() {
        return "Triangle{" + point1.toStringXY() +
                point2.toStringXY() +
                point3.toStringXY() + "}";
    }

    @Override
    float getX() {
        return (point1.x + point2.x + point3.x) / 3;
    }

    @Override
    float getY() {
        return (point1.y + point2.y + point3.y) / 3;
    }

    @Override
    float computeArea() {
        float a = (float) Math.sqrt((point1.x - point2.x) * (point1.x - point2.x) +
                (point1.y - point2.y) * (point1.y - point2.y));
        float b = (float) Math.sqrt((point2.x - point3.x) * (point2.x - point3.x) +
                (point2.y - point3.y) * (point2.y - point3.y));
        float c = (float) Math.sqrt((point1.x - point3.x) * (point1.x - point3.x) +
                (point1.y - point3.y) * (point1.y - point3.y));
        float p = (a + b + c) / 2;
        return (float) Math.sqrt(p * (p - a) * (p - b) * (p - c));
    }

    @Override
    float computePerimeter() {
        float a = (float) Math.sqrt((point1.x - point2.x) * (point1.x - point2.x) +
                (point1.y - point2.y) * (point1.y - point2.y));
        float b = (float) Math.sqrt((point2.x - point3.x) * (point2.x - point3.x) +
                (point2.y - point3.y) * (point2.y - point3.y));
        float c = (float) Math.sqrt((point1.x - point3.x) * (point1.x - point3.x) +
                (point1.y - point3.y) * (point1.y - point3.y));
        return a + b + c;
    }

    @Override
    void move(float dx, float dy) {
        point1.move(dx, dy);
        point2.move(dx, dy);
        point3.move(dx, dy);
    }

    @Override
    void scale(float s) {
        Point sr1 = new Point((point1.x + point2.x + point3.x) / 3,
                (point1.y + point2.y + point3.y) / 3);
        point1.x *= s;
        point1.y *= s;
        point2.x *= s;
        point2.y *= s;
        point3.x *= s;
        point3.y *= s;
        Point sr2 = new Point((point1.x + point2.x + point3.x) / 3,
                (point1.y + point2.y + point3.y) / 3);
        float dx = sr1.x - sr2.x;
        float dy = sr1.y - sr2.y;
        point1.move(dx, dy);
        point2.move(dx, dy);
        point3.move(dx, dy);
    }

    @Override
    void draw(Graphics g) {
        setColor(g);
        g.drawLine((int) point1.x, (int) point1.y,
                (int) point2.x, (int) point2.y);
        g.drawLine((int) point2.x, (int) point2.y,
                (int) point3.x, (int) point3.y);
        g.drawLine((int) point3.x, (int) point3.y,
                (int) point1.x, (int) point1.y);
    }

}
