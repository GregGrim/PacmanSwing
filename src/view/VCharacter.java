package view;

import model.Character;
import model.Item;

import javax.swing.*;


public abstract class VCharacter extends VItem {
    public VCharacter(Item model, int r) {
        super(model,r);
    }
}
