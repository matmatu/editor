package test;

import junit.framework.TestCase;
import org.ulco.*;

public class IsClosedTest extends TestCase {
    public void testClosed() throws Exception{
        Group g = new Group();
        Square s = new Square(new Point(0,0), 5);
        Circle c = new Circle(new Point(5,5), 4);
        Group g2 = new Group();
        Rectangle r = new Rectangle(new Point(-6,10), 5.2, 9);

        g.add(s);
        g.add(c);
        g2.add(g);
        g2.add(r);

        Point p=new Point(6,6);
        assertTrue(g2.isClosed(p,4));
    }
}
