import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Iterator;
import java.util.Vector;

class Picture extends JPanel implements KeyListener, MouseListener, MouseMotionListener {

    private static final long serialVersionUID = 1L;


    Vector<Figure> figures = new Vector<Figure>();


    	private int mouseX;
    	private int mouseY;
    	
    
    /*
     * UWAGA: ta metoda będzie wywoływana automatycznie przy każdej potrzebie
     * odrysowania na ekranie zawartości panelu
     *
     * W tej metodzie NIE WOLNO !!! wywoływać metody repaint()
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Figure f : figures)
            f.draw(g);
    }


    void addFigure(Figure fig) {
        for (Figure f : figures) {
            f.unselect();
        }
        fig.select();
        figures.add(fig);
        repaint();
    }


    void moveAllFigures(float dx, float dy) {
        for (Figure f : figures) {
            if (f.isSelected()) f.move(dx, dy);
        }
        repaint();
    }

    void scaleAllFigures(float s) {
        for (Figure f : figures) {
            if (f.isSelected()) f.scale(s);
        }
        repaint();
    }

    public String toString() {
        String str = "Rysunek{ ";
        for (Figure f : figures)
            str += f.toString() + "\n         ";
        str += "}";
        return str;
    }


    /*
     *  Impelentacja interfejsu KeyListener - obsługa zdarzeń generowanych
     *  przez klawiaturę gdy focus jest ustawiony na ten obiekt.
     */
    public void keyPressed(KeyEvent evt)
    //Virtual keys (arrow keys, function keys, etc) - handled with keyPressed() listener.
    {
        int dist;
        if (evt.isShiftDown()) dist = 10;
        else dist = 1;
        switch (evt.getKeyCode()) {
            case KeyEvent.VK_UP:
                moveAllFigures(0, -dist);
                break;
            case KeyEvent.VK_DOWN:
                moveAllFigures(0, dist);
                break;
            case KeyEvent.VK_LEFT:
                moveAllFigures(-dist, 0);
                break;
            case KeyEvent.VK_RIGHT:
                moveAllFigures(dist, 0);
                break;
            case KeyEvent.VK_DELETE:
                Iterator<Figure> i = figures.iterator();
                while (i.hasNext()) {
                    Figure f = i.next();
                    if (f.isSelected()) {
                        i.remove();
                    }
                }
                repaint();
                break;
        }
    }

    public void keyReleased(KeyEvent evt) {
    }

    public void keyTyped(KeyEvent evt)
    //Characters (a, A, #, ...) - handled in the keyTyped() listener.
    {
        char znak = evt.getKeyChar(); //reakcja na przycisku na naciśnięcie klawisza
        switch (znak) {
            case 'p':
                addFigure(new Point());
                break;
            case 'c':
                addFigure(new Circle());
                break;
            case 't':
                addFigure(new Triangle());
                break;
            case 's':
                addFigure(new Hexagon());
                break;
            case 'k':
                addFigure(new Hourglass());
                break;
            case 'r':
                addFigure(new Rectangle());
                break;

            case '+':
            	if (evt.isShiftDown()) {
                    scaleAllFigures(2f);
                    break;
            	}
            	else {
                  scaleAllFigures(1.1f);
                  break;
            	}
            case '-':
            	if (evt.isShiftDown()) {
	                scaleAllFigures(0.5f);
	                break;
            	}            		
				else {
	                scaleAllFigures(0.9f);
	                break;
				}
        }
    }


    /*
     * Implementacja interfejsu MouseListener - obsługa zdarzeń generowanych przez myszkę
     * gdy kursor myszki jest na tym panelu
     */
    public void mouseClicked(MouseEvent e)
    // Invoked when the mouse button has been clicked (pressed and released) on a component.
    {
        int px = e.getX();
        int py = e.getY();
        for (Figure f : figures) {
            if (e.isAltDown() == false) f.unselect();
            if (f.isInside(px, py)) f.select(!f.isSelected());
        }
        repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e)
    //Invoked when the mouse enters a component.
    {
    }

    @Override
    public void mouseExited(MouseEvent e)
    //Invoked when the mouse exits a component.
    {
    }

    @Override
    public void mousePressed(MouseEvent e)
    // Invoked when a mouse button has been pressed on a component.
    {
    	this.mouseX = e.getX();
    	this.mouseY = e.getY();
    }

    @Override
    public void mouseReleased(MouseEvent e)
    //Invoked when a mouse button has been released on a component.
    {
    }
    
    @Override
    public void mouseDragged(MouseEvent e)
    {
    	moveAllFigures(e.getX() - mouseX, e.getY() - mouseY);
    	mouseX = e.getX();
    	mouseY = e.getY();
    	repaint();
   }


	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
