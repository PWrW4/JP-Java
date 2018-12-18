import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.RenderingHints.Key;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Random;
import java.util.TreeSet;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

class PanelKolosa extends JPanel implements ActionListener, MouseMotionListener, MouseListener, KeyListener {

	private static final long serialVersionUID = 1;

	JButton dodajButton = new JButton("Dodaj ko�o");
	JButton usunButton = new JButton("Usun ko�o");
	TreeSet<Circle> kolekcjaKolek = new TreeSet<Circle>();
	Circle circleUnderCursor = null;
	int mouseX=0, mouseY=0;
	protected int mouseCursor = Cursor.DEFAULT_CURSOR;

	PanelKolosa() {

		this.add(dodajButton);
		this.add(usunButton);
		dodajButton.addActionListener(this);
		usunButton.addActionListener(this);
		this.setFocusable(true);
		this.addMouseListener(this);
		this.addKeyListener(this);
		this.addMouseMotionListener(this);
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		Object source = arg0.getSource();
		if (source == usunButton) {
			this.kolekcjaKolek.removeAll(kolekcjaKolek);
		}
		if (source == dodajButton) {
			String nazwa = JOptionPane.showInputDialog(this, "Podan nazwe k�ka", "Tworzenie k�ka",
					JOptionPane.DEFAULT_OPTION);
			Random rand = new Random();
			int x = rand.nextInt(400);
			int y = rand.nextInt(400);
			Circle tmp = new Circle(nazwa, x, y);
			this.kolekcjaKolek.add(tmp);
		}
		this.requestFocus();
		repaint();
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		for (Circle circle : kolekcjaKolek) {
			circle.paint(g);
		}
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		String nazwa = JOptionPane.showInputDialog(this, "Podan nazwe k�ka", "Tworzenie k�ka",
				JOptionPane.DEFAULT_OPTION);
		Circle tmp = new Circle(nazwa, arg0.getX(), arg0.getY());
		this.kolekcjaKolek.add(tmp);
		repaint();
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		int moveSpeed = 1 ;
		if (e.isShiftDown()==true) moveSpeed = 10;
		int keyCode = e.getKeyCode();
		switch (keyCode) {
		case KeyEvent.VK_UP:
			for (Circle circle : kolekcjaKolek) {
				circle.move(0, moveSpeed);
			}
			break;
		case KeyEvent.VK_DOWN:
			for (Circle circle : kolekcjaKolek) {
				circle.move(0, -moveSpeed);
			}
			break;
		case KeyEvent.VK_RIGHT:
		for (Circle circle : kolekcjaKolek) {
			circle.move(-moveSpeed, 0);
		}
		break;
		case KeyEvent.VK_LEFT:
			for (Circle circle : kolekcjaKolek) {
				circle.move(moveSpeed, 0);
			}
			break;
	}
		repaint();
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent e) {
		char keyCode = e.getKeyChar();
		if (keyCode == 'c' || keyCode == 'C') {
			String nazwa = JOptionPane.showInputDialog(this, "Podan nazwe k�ka", "Tworzenie k�ka",
					JOptionPane.DEFAULT_OPTION);
			Random rand = new Random();
			int x = rand.nextInt(400);
			int y = rand.nextInt(400);
			Circle tmp = new Circle(nazwa, x, y);
			this.kolekcjaKolek.add(tmp);
		}
		if (e.getKeyChar() == 127)
			this.kolekcjaKolek.removeAll(kolekcjaKolek);
		repaint();
	}

	void circleUnderCursor(MouseEvent e) {
		for (Circle circle : kolekcjaKolek) {
			if (circle.isMouseOver(e.getX(), e.getY())) {
				circleUnderCursor = circle;
				return;
			}
		}
		circleUnderCursor = null;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if (circleUnderCursor != null) {
			circleUnderCursor.move(mouseX - e.getX(),mouseY- e.getY());
		} else {
			for (Circle circle : kolekcjaKolek) {
				circle.move(mouseX-e.getX(), mouseY - e.getY());
			}
		}

		mouseX = e.getX();
		mouseY = e.getY();
		repaint();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
		circleUnderCursor(e);
		if (circleUnderCursor != null) {
			mouseCursor = Cursor.HAND_CURSOR;
			this.setCursor(Cursor.getPredefinedCursor(mouseCursor));
		} else {
			mouseCursor = Cursor.DEFAULT_CURSOR;
			this.setCursor(Cursor.getPredefinedCursor(mouseCursor));
		}

	}
}

class Circle implements Comparable {

	int x, y;
	String nazwa;

	public Circle(String nazwa, int x, int y) {
		this.nazwa = nazwa;
		this.x = x;
		this.y = y;
	}

	void move(int x, int y) {
		this.x -= x;
		this.y -= y;
	}

	@Override
	public int compareTo(Object a) {
		Circle circle = (Circle) a;
		return nazwa.compareTo(circle.nazwa);

	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	public boolean isMouseOver(int mouseX, int mouseY) {
		return (x - mouseX) * (x - mouseX) + (y - mouseY) * (y - mouseY) <= 35 * 35;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nazwa == null) ? 0 : nazwa.hashCode());
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		Circle other = (Circle) obj;
		if (nazwa == null) {
			if (other.nazwa != null)
				return false;
		} else if (!nazwa.equals(other.nazwa))
			return false;
		return true;
	}

	void paint(Graphics g) {
		Color tmpColor = g.getColor();
		g.setColor(Color.black);
		g.drawString(this.nazwa, this.x, this.y);
		g.setColor(Color.GREEN);
		g.fillOval(x, y, 35, 35);
		g.setColor(tmpColor);
	}
}

public class RogalKolo2018 extends JFrame implements ActionListener {

	/**
	 * 
	 */

	private static final long serialVersionUID = 1L;
	JMenu menu = new JMenu();
	JMenuBar menuBar = new JMenuBar();
	JMenuItem autorItem = new JMenuItem("Autor");
	JMenuItem aboutItem = new JMenuItem("O programie");
	JMenuItem zakonczItem = new JMenuItem("Zako�cz");
	PanelKolosa panel = new PanelKolosa();

	RogalKolo2018() {
		super("Imie Nazwisko 123456");
		this.setSize(new Dimension(500, 500));
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		menuBar.add(autorItem);
		menuBar.add(aboutItem);
		menuBar.add(zakonczItem);
		autorItem.addActionListener(this);
		aboutItem.addActionListener(this);
		zakonczItem.addActionListener(this);
		this.setJMenuBar(menuBar);
		this.setContentPane(panel);
		this.setVisible(true);
	}

	public static void main(String[] args) {
		new RogalKolo2018();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		Object source = arg0.getSource();
		if (source == zakonczItem) {
			this.dispose();
		}
		if (source == autorItem) {
			JOptionPane.showMessageDialog(this, "Imie Nazwisko\n123456", "O autorze", JOptionPane.INFORMATION_MESSAGE);
		}
		if (source == aboutItem) {
			JOptionPane.showMessageDialog(this, "c: dodaje nowy obiekt klasy Circle w losowym miejscu\ndelete: usuwa wszystkie obiekty klasy cicle", "Instrukcje",
					JOptionPane.INFORMATION_MESSAGE);
		}
	}
}
