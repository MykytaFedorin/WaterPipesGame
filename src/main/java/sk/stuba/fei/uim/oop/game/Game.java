package sk.stuba.fei.uim.oop.game;
import sk.stuba.fei.uim.oop.game.logic.GameLogic;
import sk.stuba.fei.uim.oop.game.window.Window;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Game {
    public Game(){
        Window window = new Window();
        GameLogic logic = new GameLogic(window);
        window.getMenu().getSizeSetter().addChangeListener(logic);
        window.getField().addMouseListener(logic);
        window.getField().addMouseMotionListener(logic);
        window.getMenu().getResetButton().addActionListener(logic);
        window.getMenu().getCheckPathButton().addActionListener(logic);
        window.addKeyListener(logic);
//        window.getField().drawRandomPipes();
    }
}
