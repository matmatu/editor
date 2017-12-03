package org.ulco;

import java.util.Vector;

public class Group extends GraphicsObject{

    public Group() {
        //super();
        m_objectList = new Vector<GraphicsObject>();
        m_ID = ID.getInstance().next();
    }

    public int compteurObjet(){
        int nbSimple = 0;
        for (int i=0;i<m_objectList.size();++i) {
            if (m_objectList.elementAt(i).isSimple()) {
                nbSimple += 1;
            }
        }
        return nbSimple;
    }

    public Group(String json) {
        m_objectList = new Vector<GraphicsObject>();
        String str = json.replaceAll("\\s+","");
        int objectsIndex = str.indexOf("objects");
        int groupsIndex = str.indexOf("groups");
        int endIndex = str.lastIndexOf("}");

        parseObjects(str.substring(objectsIndex + 9, groupsIndex - 2));
        parseGroups(str.substring(groupsIndex + 8, endIndex - 1));
    }

    public void add(Object object) {
        addObject((GraphicsObject)object);
    }

    private void addObject(GraphicsObject object) {
        m_objectList.add(object);
    }

    public Group copy() {
        Group g = new Group();
        for (Object o : m_objectList) {
            GraphicsObject element = (GraphicsObject) (o);

            g.addObject(element.copy());
        }

        return g;
    }

    public int getID() {
        return m_ID;
    }

    @Override
    public boolean isClosed(Point pt, double distance) {
        for(GraphicsObject obj : m_objectList){
            if(obj.isClosed(pt,distance)){
                return true;
            }
        }
        return false;
    }

    public void move(Point delta) {
        Group g = new Group();

        for (Object o : m_objectList) {
            GraphicsObject element = (GraphicsObject) (o);

            element.move(delta);
        }
    }

    private int searchSeparator(String str) {
        return MethodD.searchSeparator(str);
    }

    @Override
    public boolean  isSimple() {
        return false;
    }
    private void parseGroups(String groupsStr) {
        while (!groupsStr.isEmpty()) {
            int separatorIndex = searchSeparator(groupsStr);
            String groupStr;

            if (separatorIndex == -1) {
                groupStr = groupsStr;
            } else {
                groupStr = groupsStr.substring(0, separatorIndex);
            }
            m_objectList.add(JSON.parseGroup(groupStr));
            if (separatorIndex == -1) {
                groupsStr = "";
            } else {
                groupsStr = groupsStr.substring(separatorIndex + 1);
            }
        }
    }

    private void parseObjects(String objectsStr) {
        MethodD.parseObjects(objectsStr,m_objectList);
    }

    public int size() {
        int size =0;

        for (int i = 0; i < m_objectList.size(); ++i) {
            GraphicsObject element = m_objectList.get(i);

            size += element.size();
        }
        return size;
    }

    public String toJson() {
        String str = "{ type: group, objects : { ";

        for (int i = 0; i < m_objectList.size(); ++i) {
            if (m_objectList.elementAt(i).isSimple()) {
                GraphicsObject element = m_objectList.elementAt(i);

                str += element.toJson();
                if (i < m_objectList.size() - 1) {
                    str += ", ";
                }
            }
        }
        str += " }, groups : { ";

        for (int i = 0; i < m_objectList.size(); ++i) {
            if (!(m_objectList.elementAt(i).isSimple())) {
                GraphicsObject element = m_objectList.elementAt(i);

                str += element.toJson();
            }
        }
        return str + " } }";
    }

    public String toString() {
        String str = "group[[";
        int compteurObjetSimple=1;
        for (int i = 0; i < m_objectList.size(); ++i) {
            if (m_objectList.get(i).isSimple()) {
                GraphicsObject element = m_objectList.elementAt(i);
                compteurObjetSimple++;
                str += element.toString();
                if (i < m_objectList.size() - 1 && compteurObjetSimple == compteurObjet()) {
                    str += ", ";
                }
            }
        }
        str += "],[";

        for (int i = 0; i < m_objectList.size(); ++i) {
            if (!(m_objectList.get(i).isSimple())) {
                GraphicsObject element = m_objectList.elementAt(i);
                str += element.toString();
            }
        }
        return str + "]]";
    }



    private Vector<GraphicsObject> m_objectList;
    private int m_ID;
}