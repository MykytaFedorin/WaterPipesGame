package sk.stuba.fei.uim.oop.game.logic;

import sk.stuba.fei.uim.oop.game.window.Window;

import javax.swing.*;
import java.awt.event.ItemEvent;

public class GameLogic extends UniversalAdapter{
    private Window window;
    public GameLogic(Window window){
        this.window = window;
    }
    @Override
    public void itemStateChanged(ItemEvent e) {
        if(e.getStateChange() == ItemEvent.SELECTED){
            JComboBox<String> combo = this.window.getMenu().getSizeSetter();
            JLabel sizeLabel = this.window.getMenu().getSizeLabel();
            if(e.getSource() == combo) {
                sizeLabel.setText("Size: "+combo.getSelectedItem());
            }
        }
    }
}
