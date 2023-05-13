package controller;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.Set;

// shortcut to close any other window except startingWindow
public class CompoundShortcut implements KeyListener {

    Window window;

    public CompoundShortcut(Window window){
        this.window=window;
    }
    private final Set<Integer> pressedKeys = new HashSet<>();
    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        pressedKeys.add(e.getKeyCode());
        if(pressedKeys.contains(KeyEvent.VK_CONTROL)
            &&pressedKeys.contains(KeyEvent.VK_SHIFT)
            &&pressedKeys.contains(KeyEvent.VK_Q)) {
                window.dispose();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        pressedKeys.remove(e.getKeyCode());
    }
}
