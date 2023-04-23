package sk.stuba.fei.uim.oop.game.window;

import lombok.Getter;
import lombok.Setter;
import sk.stuba.fei.uim.oop.game.logic.GameLogic;
import sk.stuba.fei.uim.oop.game.window.gameField.GameField;
import sk.stuba.fei.uim.oop.game.window.menu.Menu;

import javax.swing.*;
import java.awt.*;

public class Window extends JFrame{
    @Getter
    private Menu menu;
    @Getter
    private GameField field;

    public void setField(GameField field) {
        this.field = field;
        this.add(field);
    }

    public Window() {
        this.setSize(800,800);
        this.setResizable(false);
        this.setFocusable(true);
        this.requestFocusInWindow();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.menu = new Menu();
        this.field = new GameField(8);
        this.add(this.menu, BorderLayout.PAGE_START);
        this.add(this.field, BorderLayout.CENTER);
        this.setVisible(true);
    }
}
