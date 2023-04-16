package sk.stuba.fei.uim.oop.game.window.gameField;

import lombok.Getter;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class GameField extends JPanel {
    private int size;
    private Random randomizer;
    @Getter
    private ArrayList<ArrayList<Square>> squares;
    public GameField(int size){
        this.size = size;
        this.setLayout(new GridLayout(this.size, this.size));
        this.squares = new ArrayList<>();
        this.randomizer = new Random();
        int randomRowPos = randomizer.nextInt(this.size);
        Square element;
        Border border;
        for(int i=0;i<this.size;i++){
            ArrayList<Square> row = new ArrayList<>();
            for(int j = 0;j<this.size;j++){
                if(i==0){
                    border = new MatteBorder(5, 0, 5, 5, Color.GREEN);
                }
                else{
                    border = new MatteBorder(5, 5, 5, 5, Color.GREEN);
                }
                element = new Square(Color.GRAY, i, j, border);
                row.add(element);
                this.add(element);
            }
            this.squares.add(row);
        }
    }
    public void createMaze(){
        Square startSquare = this.squares.get(0).get(0);
        randDFS(startSquare, this.size*this.size);
        Square lastSquare = this.squares.get(this.size-1).get(this.size-1);
        changeBorder(lastSquare, "right");
    }
    public void solveMaze(){
        Square startSquare = this.squares.get(0).get(0);
    }
    private void makeStep(Square square){

    }
    private void randDFS(Square square, int size){
        markVisited(square);
        Square nextSquare = randUnvisitedNeighbour(square);
        while (nextSquare != null){
            connectSquares(square, nextSquare);
            randDFS(nextSquare, size);
            nextSquare = randUnvisitedNeighbour(square);
        }
    }
    private void changeBorder(Square square, String param){
        Border border = square.getBorder();
        Insets insets = border.getBorderInsets(square);
        Border newBorder = removeBorderSide(insets, param);
        square.setBorder(newBorder);
    }
    private void connectSquares(Square square, Square nextSquare){
        nextSquare.setBackground(Color.RED);
        int rowIndex = square.getRowIndex();
        int columnIndex = square.getColumnIndex();
        int nextRowIndex = nextSquare.getRowIndex();
        int nextColumnIndex = nextSquare.getColumnIndex();

        if(rowIndex == nextRowIndex){
            if(columnIndex>nextColumnIndex){
                changeBorder(square, "left");
                changeBorder(nextSquare, "right");
            }
            else if(columnIndex<nextColumnIndex){
                changeBorder(square, "right");
                changeBorder(nextSquare, "left");
            }
        }
        else if(columnIndex == nextColumnIndex){
            if(rowIndex>nextRowIndex){
                changeBorder(square, "top");
                changeBorder(nextSquare, "bottom");
            }
            else if(rowIndex<nextRowIndex){
                changeBorder(square, "bottom");
                changeBorder(nextSquare, "top");
            }
        }
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
    private void markVisited(Square square){
        square.setBackground(Color.RED);
    }
    private Square randUnvisitedNeighbour(Square square) {
        ArrayList<Square> neighbours = getNeighbours(square);
        if(neighbours.size() == 0){
            return null;
        }
        int index = this.randomizer.nextInt(neighbours.size());
        return neighbours.get(index);
    }
    private ArrayList<Square> getNeighbours(Square square){
        ArrayList<Square> neighbours = new ArrayList<>();
        int[] pos1 = new int[]{square.getColumnIndex(), square.getRowIndex() - 1};
        int[] pos2 = new int[]{square.getColumnIndex(), square.getRowIndex()+1};
        int[] pos3 = new int[]{square.getColumnIndex()-1, square.getRowIndex()};
        int[] pos4 = new int[]{square.getColumnIndex()+1, square.getRowIndex()};
        int[][] positions = {pos1, pos2, pos3, pos4};
        for (int[] pos : positions) {
            try {
                Square neighbour = this.squares.get(pos[1]).get(pos[0]);
                if(neighbour.getBackground() != Color.RED){
                    neighbours.add(neighbour);
                }
            } catch (IndexOutOfBoundsException ignored) {

            }
        }
        return neighbours;
    }
    public int getFieldSize(){
        return this.size;
    }
}
