package sk.stuba.fei.uim.oop.game.window.gameField;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GameField extends JPanel {
    private int size;
    private ArrayList<Square> squares;
    public GameField(int size){
        this.size = size;
        this.setLayout(new GridLayout(this.size, this.size));
        this.squares = new ArrayList<>();
        for(int i=0;i<this.size;i++){
            for(int j = 0;j<this.size;j++){
                Square element = new Square();
//                JLabel label = new JLabel(String.valueOf(i));
//                element.add(label);
                this.squares.add(element);
                this.add(element);
            }
        }
    }
}
