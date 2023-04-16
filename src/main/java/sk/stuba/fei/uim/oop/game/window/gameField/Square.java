package sk.stuba.fei.uim.oop.game.window.gameField;

import lombok.Getter;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class Square extends JPanel {
    @Getter
    private int rowIndex;
    @Getter
    private int columnIndex;
    private Border border;
    public Square(Color color, int rowIndex, int columnIndex, Border border){
        this.setBackground(color);
        this.rowIndex = rowIndex;
        this.columnIndex = columnIndex;
        this.border = border;
        this.setBorder(border);
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
}
