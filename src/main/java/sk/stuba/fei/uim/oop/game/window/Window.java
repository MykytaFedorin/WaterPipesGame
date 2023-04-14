package sk.stuba.fei.uim.oop.game.window;

import lombok.Getter;
import sk.stuba.fei.uim.oop.game.logic.GameLogic;
import sk.stuba.fei.uim.oop.game.window.gameField.GameField;
import sk.stuba.fei.uim.oop.game.window.menu.Menu;

import javax.swing.*;
import java.awt.*;

public class Window extends JFrame{
    @Getter
    private Menu menu;

    public Window() {
        this.setSize(800,800);
        this.setResizable(false);
        this.setFocusable(true);
        this.requestFocusInWindow();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.menu = new Menu();
        this.add(menu, BorderLayout.PAGE_START);
        this.setVisible(true);
    }
    public void addListenerForAll(GameLogic logic){
        this.getMenu().addListenerForAll(logic);
        //need to add for game field also
    }
}
