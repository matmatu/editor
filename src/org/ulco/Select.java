package org.ulco;
import java.util.Vector;
public class Select {

    static public GraphicsObjects select(Point pt, double distance, Document document) {
        GraphicsObjects list = new GraphicsObjects();

        for (Layer layer : document.getLayer()) {
            list.addAll(Select.select(pt, distance, layer));
        }
        return list;
    }

    static public GraphicsObjects select(Point pt, double distance, Layer layer) {
        GraphicsObjects list = new GraphicsObjects();

        for (GraphicsObject object : layer.getM_list()) {
            if (object.isClosed(pt, distance)) {
                list.add(object);
            }
        }
        return list;
    }
}
