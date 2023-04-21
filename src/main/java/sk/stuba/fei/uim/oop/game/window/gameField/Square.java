package sk.stuba.fei.uim.oop.game.window.gameField;

import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.util.ArrayList;

public class Square extends JPanel {
    @Getter
    private int rowIndex;
    @Getter
    private int columnIndex;
    private Border border;
    @Setter
    private Color color;
    @Setter
    private Direction direction;
    @Setter
    @Getter
    private ArrayList<Side> sides;
    public Square(Color color, int rowIndex, int columnIndex, Border border){
        this.setBackground(color);
        this.rowIndex = rowIndex;
        this.columnIndex = columnIndex;
        this.border = border;
        this.color = color;
        this.sides = new ArrayList<>();
        this.direction = null;
        this.setBorder(border);
    }
    public void turn(){

        System.out.println("Inside");
        boolean north = this.sides.contains(Side.North);
        boolean east = this.sides.contains(Side.East);
        boolean south = this.sides.contains(Side.South);
        boolean west = this.sides.contains(Side.West);
        this.sides.clear();
        if(west && east){
            this.direction = Direction.Vertical;
            this.sides.add(Side.North);
            this.sides.add(Side.South);
        }
        else if(north && south){
            this.direction = Direction.Horizontal;
            this.sides.add(Side.West);
            this.sides.add(Side.East);
        }
        else if(north && east){
            this.direction = Direction.EastSouth;
            this.sides.add(Side.East);
            this.sides.add(Side.South);
        }
        else if(east && south){
            this.direction = Direction.SouthWest;
            this.sides.add(Side.South);
            this.sides.add(Side.West);
        }
        else if(south && west){
            this.direction = Direction.WestNorth;
            this.sides.add(Side.West);
            this.sides.add(Side.North);
        }
        else if(west && north){
            this.direction = Direction.NorthEast;
            this.sides.add(Side.North);
            this.sides.add(Side.East);
        }
        this.repaint();
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
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        boolean north = this.sides.contains(Side.North);
        boolean east = this.sides.contains(Side.East);
        boolean south = this.sides.contains(Side.South);
        boolean west = this.sides.contains(Side.West);
        int width = this.getWidth();
        int height = this.getHeight();
        if(this.getBackground() == Color.BLUE){
            g.setColor(Color.red);
            if(west && east){
                g.fillRect(0,3*height/8,width,2*height/8);
            } else if (north && south) {
                g.fillRect(3*width/8, 0, 2*width/8, height);
            }
            else if (west && north) {
                g.fillRect(0, 3*height/8, 5*width/8, 2*height/8);
                g.fillRect(3*width/8, 0, 2*width/8, 5*height/8);
            }
            else if (north && east){
                g.fillRect(3*width/8, 0, 2*width/8, 5*height/8);
                g.fillRect(3*width/8, 3*height/8, 5*width/8, 2*height/8);
            }
            else if(east && south){
                g.fillRect(3*width/8, 3*height/8, 5*width/8, 2*height/8);
                g.fillRect(3*width/8, 3*height/8, 2*width/8, 5*height/8);
            }
            else if(south && west){
                g.fillRect(3*width/8, 3*height/8, 2*width/8, 5*height/8);
                g.fillRect(0, 3*height/8, 5*width/8, 2*height/8);
            }
        }
    }
    public Side getNeighbourSide(Square neighbour){
        if(this.getRowIndex() == neighbour.getRowIndex() && this.getColumnIndex() > neighbour.getColumnIndex()){
            return Side.West;
        }
        else if(this.getColumnIndex() == neighbour.getColumnIndex() && this.getRowIndex()>neighbour.getRowIndex()){
            return Side.North;
        }
        else if(this.getRowIndex() == neighbour.getRowIndex() && this.getColumnIndex() < neighbour.getColumnIndex()){
            return Side.East;
        }
        else if(this.getColumnIndex() == neighbour.getColumnIndex() && this.getRowIndex() < neighbour.getRowIndex()){
            return Side.South;
        }
        else{
            return null;
        }
    }
    public boolean equals(Square square){
        return this.getColumnIndex() == square.getColumnIndex() &&
                this.getRowIndex() == square.getRowIndex();
    }
}
