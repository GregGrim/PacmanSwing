package view;

import model.Item;

import javax.swing.*;
import java.lang.reflect.Constructor;


public abstract class VItem implements Icon {
    protected Item model;
    protected int r;
    public VItem(Item model,int r) {
        this.model = model;
        this.r=r;
    }
    public static VItem newItem(Item model, int r) {
        try {
            Class<?> clazz = Class.forName("view.V" +
                    model.getClass().getName().replace("model.", ""));
            Constructor<?> ctor = clazz.getConstructor(Item.class, int.class);
            VItem item = (VItem) ctor.newInstance(model, r);
            return item;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
