/*
 *  Program EdytorGraficzny - aplikacja z graficznym interfejsem
 *   - obs�uga zdarzeń od klawiatury, myszki i innych elementów GUI.
 *
 *  Autor: Pawe� Rogalinski, Wojciech Wójcik
 *   Data: 1. 10, 2015 r.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class GraphicEditor extends JFrame implements ActionListener {


    private static final long serialVersionUID = 3727471814914970170L;


    private final String DESCRIPTION = "OPIS PROGRAMU\n\n" + "Aktywna klawisze:\n"
            + "   strzalki ==> przesuwanie figur\n"
            + "   SHIFT + strzalki ==> szybkie przesuwanie figur\n"
            + "   +,-  ==> powiekszanie, pomniejszanie\n"
            + "   SHIFT + '+/-' ==> szybkie skalowanie figur\n"
            + "   DEL  ==> kasowanie figur\n"
            + "   p  ==> dodanie nowego punktu\n"
            + "   c  ==> dodanie nowego kola\n"
            + "   t  ==> dodanie nowego trojkata\n"
            + "   k  ==> dodanie nowej klepsydry\n"
            + "   s  ==> dodanie nowego sześciokąta\n"
            + "   r  ==> dodanie nowego prostokąta\n"
            + "\nOperacje myszka:\n" + "   klik ==> zaznaczanie figur\n"
            + "   ALT + klik ==> zmiana zaznaczenia figur\n"
            + "   przeciaganie ==> przesuwanie figur";


    protected Picture picture;

    private JMenu[] menu = {
    		new JMenu("Figury"),
            new JMenu("Edytuj"),
            new JMenu("Pomoc")
            };

    private JMenuItem[] items = {
    		new JMenuItem("Punkt"),
            new JMenuItem("Kolo"),
            new JMenuItem("Trojkat"),
            new JMenuItem("Wypisz wszystkie"),
            new JMenuItem("Przesun w gore"),
            new JMenuItem("Przesun w dol"),
            new JMenuItem("Powieksz"),
            new JMenuItem("Pomniejsz"),
            new JMenuItem("Przesun w lewo"),
            new JMenuItem("Przesun w prawo"),
            new JMenuItem("Sześciokąt"),
            new JMenuItem("Prostokąt"),
            new JMenuItem("Klepsydra"),
            new JMenuItem("Pomoc"),
            new JMenuItem("Autor")
    };

    private JButton buttonPoint = new JButton("Punkt");
    private JButton buttonCircle = new JButton("Kolo");
    private JButton buttonTriangle = new JButton("Trojkat");
    private JButton buttonHexagon = new JButton("Sześciokąt");
    private JButton buttonRectangle = new JButton("Prostokąt");
    private JButton buttonHourglass = new JButton("Klepsydra");

    public GraphicEditor() {
        super("Edytor graficzny");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        for (int i = 0; i < items.length; i++)
            items[i].addActionListener(this);

        // dodanie opcji do menu "Figury"
        menu[0].add(items[0]);
        menu[0].add(items[1]);
        menu[0].add(items[2]);
        menu[0].add(items[10]);
        menu[0].add(items[11]);
        menu[0].add(items[12]);
        menu[0].addSeparator();
        menu[0].add(items[3]);

        // dodanie opcji do menu "Edytuj"
        menu[1].add(items[8]);
        menu[1].add(items[9]);
        menu[1].add(items[4]);
        menu[1].add(items[5]);
        menu[1].addSeparator();
        menu[1].add(items[6]);
        menu[1].add(items[7]);
        
        menu[2].add(items[13]);
        menu[2].add(items[14]);

        // dodanie do okna paska menu
        JMenuBar menubar = new JMenuBar();
        for (int i = 0; i < menu.length; i++)
            menubar.add(menu[i]);
        setJMenuBar(menubar);

        picture = new Picture();
        picture.addKeyListener(picture);
        picture.setFocusable(true);
        picture.addMouseListener(picture);
        picture.addMouseMotionListener(picture);
        picture.setLayout(new FlowLayout());

        buttonPoint.addActionListener(this);
        buttonCircle.addActionListener(this);
        buttonTriangle.addActionListener(this);
        buttonHexagon.addActionListener(this);
        buttonRectangle.addActionListener(this);
        buttonHourglass.addActionListener(this);
        
        picture.add(buttonPoint);
        picture.add(buttonCircle);
        picture.add(buttonTriangle);
        picture.add(buttonHexagon);
        picture.add(buttonRectangle);	
        picture.add(buttonHourglass);
        
        setContentPane(picture);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent evt) {
        Object zrodlo = evt.getSource();

        if (zrodlo == buttonPoint)
            picture.addFigure(new Point());
        if (zrodlo == buttonCircle)
            picture.addFigure(new Circle());
        if (zrodlo == buttonTriangle)
            picture.addFigure(new Triangle());
        if (zrodlo == buttonHexagon) 
			picture.addFigure(new Hexagon());
        if (zrodlo == buttonRectangle)
			picture.addFigure(new Rectangle());
		if (zrodlo == buttonHourglass)
			picture.addFigure(new Hourglass());
        
		

        if (zrodlo == items[0])
            picture.addFigure(new Point());
        if (zrodlo == items[1])
            picture.addFigure(new Circle());
        if (zrodlo == items[2])
            picture.addFigure(new Triangle());
        if (zrodlo == items[10])
            picture.addFigure(new Hexagon());
        if (zrodlo == items[11])
            picture.addFigure(new Rectangle());
        if (zrodlo == items[12])
            picture.addFigure(new Hourglass());        
        if (zrodlo == items[3])
            JOptionPane.showMessageDialog(null, picture.toString());

        if (zrodlo == items[8])
            picture.moveAllFigures(-30, 0);
        if (zrodlo == items[9])
            picture.moveAllFigures(30, 0);
        if (zrodlo == items[4])
            picture.moveAllFigures(0, -30);
        if (zrodlo == items[5])
            picture.moveAllFigures(0, 30);
        if (zrodlo == items[6])
            picture.scaleAllFigures(1.2f);
        if (zrodlo == items[7])
            picture.scaleAllFigures(0.8f);
        if (zrodlo == items[13])
            JOptionPane.showMessageDialog(null,	 DESCRIPTION);
        if (zrodlo == items[14])
            JOptionPane.showMessageDialog(null,	 "Autor: Pawel Rogalinski i Wojciech Wójcik");
        
        
        picture.requestFocus(); // przywrocenie ogniskowania w celu przywrocenia
        // obslugi zadarez� pd klawiatury
        repaint();
    }

    public static void main(String[] args) {
        new GraphicEditor();
    }

}

