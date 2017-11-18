import java.awt.Graphics;

public class Hexagon extends Figure {

	Point p1,p2,p3,p4,p5,p6;
	
	Point center;
	
	float a;
	
	public Hexagon() {	
		center = new Point();
		a =random.nextFloat() * 40;
				
		calculatePoints(a);
	}
	
	//ten konstruktor nie jest raczej potrzebny, ale zostawię go. Ładny jest, taki nielosowy :)
	public Hexagon(float a) {	
		center = new Point();
		calculatePoints(a);
	}
	
	//tu jest taka super klasa która pozycjonuje wszystkie punkty, W sumie przydatne (robi się to po zeskalowaniu i przesunięciu :) )
	void calculatePoints(float a) {
		float d_short = (float) (Math.sqrt(3)*a);
		p1 = new Point(center.getX()-0.5f*a,center.getY()+0.5f*d_short);
		p2 = new Point(center.getX()+0.5f*a,center.getY()+0.5f*d_short);
		p3 = new Point(center.getX()+0.5f*d_short,center.getY());
		p4 = new Point(center.getX()+0.5f*a,center.getY()-0.5f*d_short);
		p5 = new Point(center.getX()-0.5f*a,center.getY()-0.5f*d_short);
		p6 = new Point(center.getX()-0.5f*d_short,center.getY());
		
	}
	
	@Override
	public boolean isInside(float px, float py) {
		float d_short = (float) (Math.sqrt(3)*a);
		if (px>center.getX()-0.5f*d_short && px<center.getX()+0.5*d_short && py>center.getY()-0.5*d_short && py<center.getY()+0.5*d_short) {
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
                p4.toStringXY() + 
                p5.toStringXY() + 
                p6.toStringXY() + "}";
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
		return (float) (3/2* Math.sqrt(3) * a*a);
	}

	@Override
	float computePerimeter() {
		return 6*a;
	}

	@Override
	void move(float dx, float dy) {
		center.move(dx, dy);
		calculatePoints(a);
	}

	@Override
	void scale(float s) {
		a=s*a;
		calculatePoints(a);		
	}

	@Override
	void draw(Graphics g) {
        setColor(g);
        g.drawLine((int) p1.x, (int) p1.y,
                (int) p2.x, (int) p2.y);
        g.drawLine((int) p2.x, (int) p2.y,
                (int) p3.x, (int) p3.y);
        g.drawLine((int) p3.x, (int) p3.y,
                (int) p4.x, (int) p4.y);
        g.drawLine((int) p4.x, (int) p4.y,
                (int) p5.x, (int) p5.y);
        g.drawLine((int) p5.x, (int) p5.y,
                (int) p6.x, (int) p6.y);
        g.drawLine((int) p6.x, (int) p6.y,
                (int) p1.x, (int) p1.y);


	}

}
