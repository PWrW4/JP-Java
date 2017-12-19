package oldKolokwium201617;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

public class Panelik extends JPanel implements MouseMotionListener, MouseListener {

	boolean shouldIPrint = true, fillCircle = true;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	int X=100, Y=100, R = 50;
	
	
	public Panelik() {
		addMouseListener(this);
		addMouseMotionListener(this);
	}
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		if (shouldIPrint) {
			if (fillCircle) {	
				g.clearRect(0, 0, this.getWidth(), this.getHeight());
				g.setColor(Color.BLACK);  
				X = X-(R/2);
				Y = Y-(R/2);
				g.drawOval(X,Y,R,R);
			}else {
				g.clearRect(0, 0, this.getWidth(), this.getHeight());
				g.setColor(Color.BLACK);  
				X = X-(R/2);
				Y = Y-(R/2);
				g.fillOval(X,Y,R,R);  
			}
		}else
		{
			g.clearRect(0, 0, this.getWidth(), this.getHeight());
		}
  
    }
	

	public void moveCircle(int x, int y){
		X= x;
		Y=y;
	}
	
	
	@Override
	public void mouseClicked(MouseEvent e) {
		moveCircle(e.getX(),e.getY());
		repaint();
		
	}


	@Override
	public void mouseEntered(MouseEvent e) {
		shouldIPrint = true;
		repaint();
		
	}


	@Override
	public void mouseExited(MouseEvent e) {
		shouldIPrint = false;
		repaint();
	}


	@Override
	public void mousePressed(MouseEvent e) {
		fillCircle = false;
		repaint();
	}


	@Override
	public void mouseReleased(MouseEvent e) {
		fillCircle = true;
		repaint();
	}


	@Override
	public void mouseDragged(MouseEvent e) {
		moveCircle(e.getX(),e.getY());
		repaint();
	}


	@Override
	public void mouseMoved(MouseEvent e) {
		moveCircle(e.getX(),e.getY());
		repaint();
	}
}
