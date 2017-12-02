package org.ulco;

import static org.ulco.ID.getInstance;

public class ID {
    static public int ID = 0;
    private static ID instance = new ID();
    private ID(){}
    public static ID getInstance(){
        return instance;
    }
    public int next(){
        return ++ID;
    }
    public int valueID(){
        return ID;
    }
}