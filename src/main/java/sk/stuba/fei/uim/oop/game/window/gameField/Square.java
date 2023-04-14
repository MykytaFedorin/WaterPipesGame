package sk.stuba.fei.uim.oop.game.window.gameField;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class Square extends JPanel {
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.setBackground(Color.gray);
        this.setBorder(BorderFactory.createLineBorder(Color.black));
    }
}
