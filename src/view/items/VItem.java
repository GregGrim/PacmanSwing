package view.items;

import model.items.Item;

import javax.swing.*;


public abstract class VItem implements Icon {
    protected Item model;
    protected int r;
    public VItem(Item model,int r) {
        this.model = model;
        this.r=r;
    }
}
