package ui;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;
import java.util.List;

import javax.swing.JFrame;

public class Dialog extends Canvas {
    class Point
    {
        int x;
        int y;
    }

    class Node <T>
    {
        T data;
        Node<T> next;
        public Node(T d)
        {
            data = d;
        }
    }

    class SizableShape
    {
        Node<Point> begin = new Node<>(new Point());
        Node<Point> end = begin;
        Node<Point> current = begin;

        public SizableShape()
        {
            getPoint().x = 200;
            getPoint().y = 200;
        }

        Point getPoint()
        {
            return current.data;
        }

        void addPoint()
        {
            Point p = new Point();
            p.x = getPoint().x;
            p.y = getPoint().y;
            end.next = new Node<>(p);
            end = end.next;
        }

        void nextPoint()
        {
            if (current.next == null)
            {
                current = begin;
            }
            else
            {
                current = current.next;
            }
        }
    }

    SizableShape ss = new SizableShape();

    private static final int WIDTH = 400;
    private static final int HEIGHT = 400;

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        Node<Point> last = ss.begin;
        Node<Point> next = ss.begin;
        g.drawLine(next.data.x,next.data.y,last.data.x,last.data.y);

        while (next.next != null)
        {
            last = next;
            next = next.next;
            g.drawLine(next.data.x,next.data.y,last.data.x,last.data.y);
        }
        g.setColor(Color.black);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setSize(WIDTH, HEIGHT);
        Dialog d = new Dialog();
        frame.addKeyListener(d.keyListener);
        d.addKeyListener(d.keyListener);
        frame.add(d);
        frame.setVisible(true);
    }

    private boolean left;
    private boolean right;
    private boolean up;
    private boolean down;

    KeyListener keyListener = new KeyListener()
    {
        @Override
        public void keyTyped(KeyEvent keyEvent) {
            System.out.println("keyTyped: "+keyEvent.getExtendedKeyCode());
        }

        @Override
        public void keyPressed(KeyEvent keyEvent) {
            System.out.println("keyPressed: "+keyEvent.getExtendedKeyCode());

            switch (keyEvent.getExtendedKeyCode())
            {
                case (37): left = true; break;
                case (39): right = true; break;
                case (38): up = true; break;
                case (40): down = true; break;
                case (78): ss.nextPoint(); break;
                case (65): ss.addPoint(); break;
            }
            evaluateMovement();
            repaint();
        }

        @Override
        public void keyReleased(KeyEvent keyEvent) {
            System.out.println("keyReleased: "+keyEvent.getExtendedKeyCode());
            switch (keyEvent.getExtendedKeyCode())
            {
                case (37): left = false; break;
                case (39): right = false; break;
                case (38): up = false; break;
                case (40): down = false; break;
            }
        }
    };

    private void evaluateMovement()
    {
        if (up) ss.getPoint().y--;
        if (down) ss.getPoint().y++;
        if (left) ss.getPoint().x--;
        if (right) ss.getPoint().x++;
    }
}