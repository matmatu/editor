package org.ulco;

import java.lang.reflect.Constructor;

public class JSON {
    static public GraphicsObject parse(String json) {
        GraphicsObject o = null;
        String str = json.replaceAll("\\s+", "");
        String type = str.substring(str.indexOf("type") + 5, str.indexOf(","));

        /*if (type.compareTo("square") == 0) {
            o = new Square(str);
        } else if (type.compareTo("rectangle") == 0) {
            o = new Rectangle(str);
        } else if (type.compareTo("circle") == 0) {
            o = new Circle(str);
        }*/
        String nomClass = type.substring(0,1).toUpperCase() + type.substring(1);
        try{
            Class classe = Class.forName("org.ulco."+nomClass);
            Class[] parametre = new Class[]{String.class};
            Constructor constructeur = classe.getConstructor(parametre);
            o = (GraphicsObject)constructeur.newInstance(new String[]{str});
        }catch(Exception e) {
            e.printStackTrace();
        }
        return o;
    }

    static public Group parseGroup(String json) {
        return new Group(json);
    }

    static public Layer parseLayer(String json) {
        return new Layer(json);
    }

    static public Document parseDocument(String json) {
        return new Document(json);
    }
}
