package sk.stuba.fei.uim.oop.game;
import sk.stuba.fei.uim.oop.game.logic.GameLogic;
import sk.stuba.fei.uim.oop.game.window.Window;

import javax.swing.*;

public class Game {
    public Game(){
        Window window = new Window();
        GameLogic logic = new GameLogic(window);
        window.getMenu().getSizeSetter().addItemListener(logic);
    }
}
