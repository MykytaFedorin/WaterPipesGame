package sk.stuba.fei.uim.oop.game.window.gameField;

import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;
import java.awt.*;

public class Square extends JPanel {
    @Getter
    private int rowIndex;
    @Getter
    private int columnIndex;
    private Border border;
    @Setter
    private Color color;
    public Square(Color color, int rowIndex, int columnIndex, Border border){
        this.setBackground(color);
        this.rowIndex = rowIndex;
        this.columnIndex = columnIndex;
        this.border = border;
        this.color = color;
        this.setBorder(border);
    }
    public void changeBorder(String param){
        Border border = this.getBorder();
        Insets insets = border.getBorderInsets(this);
        Border newBorder = removeBorderSide(insets, param);
        this.setBorder(newBorder);
    }
    private Border removeBorderSide(Insets lastInsets, String param){
        Border border= null;
        switch (param){
            case "top":
                border = new MatteBorder(0, lastInsets.left, lastInsets.bottom, lastInsets.right, Color.GREEN);
                break;
            case "left":
                border = BorderFactory.createMatteBorder(lastInsets.top,0,lastInsets.bottom,lastInsets.right,Color.GREEN);
                break;
            case "bottom":
                border = BorderFactory.createMatteBorder(lastInsets.top, lastInsets.left, 0,lastInsets.right,Color.GREEN);
                break;
            case "right":
                border = BorderFactory.createMatteBorder(lastInsets.top, lastInsets.left, lastInsets.bottom, 0,Color.GREEN);
                break;
        }
        return border;
    }
    public boolean isConnectedTo(Square square2){
        int rowIndex = this.getRowIndex();
        int columnIndex = this.getColumnIndex();
        int nextRowIndex = square2.getRowIndex();
        int nextColumnIndex = square2.getColumnIndex();

        if(rowIndex == nextRowIndex){
            if(columnIndex>nextColumnIndex){
                return !borderExist(this, "left") || !borderExist(square2, "right");
            }
            else if(columnIndex<nextColumnIndex){
                return !borderExist(this, "right") || !borderExist(square2, "left");
            }
        }
        else if(columnIndex == nextColumnIndex){
            if(rowIndex>nextRowIndex){
                return !borderExist(this, "top") || !borderExist(square2, "bottom");
            }
            else {
                return !borderExist(this, "bottom") || !borderExist(square2, "top");
            }
        }
        return true;
    }
    private boolean borderExist(Square square, String param){
        Insets insets = square.getInsets();
        switch(param){
            case "top":
                return insets.top != 0;
            case "left":
                return insets.left != 0;
            case "bottom":
                return insets.bottom != 0;
            case "right":
                return insets.right != 0;
            default:
                return false;
        }
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(this.getBackground() == Color.BLUE){
            g.setColor(Color.red);
            g.fillRect(0,0,this.getWidth()/2,this.getHeight()/2);
        }
    }
}
