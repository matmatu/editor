package org.ulco;

import java.util.Vector;

public class Layer {
    public Layer() {
        m_list = new Vector<GraphicsObject>();
        m_ID = ID.getInstance().next();
    }

    public Layer(String json) {
        m_list= new Vector<GraphicsObject>();
        String str = json.replaceAll("\\s+","");
        int objectsIndex = str.indexOf("objects");
        int endIndex = str.lastIndexOf("}");
        if(str.contains("groups")){
            int groupsIndex = str.indexOf("groups");
            parseObjects(str.substring(groupsIndex + 8, endIndex - 1));
        }

        parseObjects(str.substring(objectsIndex + 9, endIndex - 1));
    }

    public void add(GraphicsObject o) {
        m_list.add(o);
    }

    public GraphicsObject get(int index) {
        return m_list.elementAt(index);
    }

    public int getObjectNumber() {
        return m_list.size();
    }

    public int getID() {
        return m_ID;
    }

    private void parseObjects(String objectsStr) {
        MethodD.parseObjects(objectsStr,m_list);
    }

    private int searchSeparator(String str) {
        return MethodD.searchSeparator(str);
    }

    public GraphicsObjects select(Point pt, double distance) {
        /*GraphicsObjects list = new GraphicsObjects();

        for (GraphicsObject object : m_list) {
            if (object.isClosed(pt, distance)) {
                list.add(object);
            }
        }
        return list;*/
        return Select.select(pt,distance,this);
    }

    public Vector<GraphicsObject> getM_list() {
        return m_list;
    }

    public String toJson() {
        String str = "{ type: layer, objects : { ";

        for (int i = 0; i < m_list.size(); ++i) {
            GraphicsObject element = m_list.elementAt(i);

            str += element.toJson();
            if (i < m_list.size() - 1) {
                str += ", ";
            }
        }
        return str + " } }";
    }

    private Vector<GraphicsObject> m_list;
    private int m_ID;
}
