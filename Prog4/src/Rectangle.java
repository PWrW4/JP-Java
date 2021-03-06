import java.awt.Graphics;

public class Rectangle extends Figure {

	Point center;
	
	float a,b;
	
	Point p1,p2,p3,p4;
	
	
	//Tu już pisałem tylko losowy konstruktor, chyba że coś się zmieni póżniej
	public Rectangle() {
		center = new Point();
		a = random.nextFloat() * 200;
		b = random.nextFloat() * 200;
		calculatePoints();
	}
	
	void calculatePoints() {
		p1 = new Point(center.getX()-0.5f*a, center.getY()+0.5f*b);
		p2 = new Point(center.getX()+0.5f*a, center.getY()+0.5f*b);
		p3 = new Point(center.getX()-0.5f*a, center.getY()-0.5f*b);
		p4 = new Point(center.getX()+0.5f*a, center.getY()-0.5f*b);
	}
	
	@Override
	public boolean isInside(float px, float py) {
		if (px> center.getX()-0.5*a && px <center.getX()+0.5*a && py>center.getY()-0.5*b &&py<center.getY()+0.5*b) {
			return true;
		}
		return false;
	}

	@Override
	String getName() {
		return "Sześciokąt{" + 
				p1.toStringXY() +
                p2.toStringXY() +
                p3.toStringXY() +
                p4.toStringXY() +  "}";
	}

	@Override
	float getX() {
		return center.getX();
	}

	@Override
	float getY() {
		return center.getY();
	}

	@Override
	float computeArea() {
		return a*b;
	}

	@Override
	float computePerimeter() {
		return 2*a + 2*b;
	}

	@Override
	void move(float dx, float dy) {
		center.move(dx, dy);
		calculatePoints();
	}

	@Override
	void scale(float s) {
		a*=s;
		b*=s;
		calculatePoints();
	}

	@Override
	void draw(Graphics g) {
        setColor(g);
        g.drawLine((int) p1.x, (int) p1.y,
                (int) p2.x, (int) p2.y);
        g.drawLine((int) p2.x, (int) p2.y,
                (int) p4.x, (int) p4.y);
        g.drawLine((int) p4.x, (int) p4.y,
                (int) p3.x, (int) p3.y);
        g.drawLine((int) p3.x, (int) p3.y,
                (int) p1.x, (int) p1.y);
	}

}
