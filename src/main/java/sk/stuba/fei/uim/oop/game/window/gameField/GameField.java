package sk.stuba.fei.uim.oop.game.window.gameField;

import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class GameField extends JPanel {
    @Getter
    private int fieldSize;
    private Random randomizer;
    @Getter
    @Setter
    private ArrayList<ArrayList<Square>> squares;
    @Getter
    private Square startSquare;
    @Getter
    private Square finishSquare;
    public GameField(int size){
        this.fieldSize = size;
        this.setLayout(new GridLayout(this.fieldSize, this.fieldSize));
        this.squares = new ArrayList<>();
        initSquares();
        this.randomizer = new Random();
        int randRowIndex = this.randomizer.nextInt(this.fieldSize);
        int randColIndex = this.randomizer.nextInt(this.fieldSize);
        this.finishSquare = this.squares.get(randColIndex).get(this.fieldSize -1);
        this.startSquare = this.squares.get(randRowIndex).get(0);
    }
    private void initSquares(){
        Square element;
        Border border;
        for(int i = 0; i<this.fieldSize; i++){
            ArrayList<Square> row = new ArrayList<>();
            for(int j = 0; j<this.fieldSize; j++){
                border = new MatteBorder(5, 5, 5, 5, Color.GREEN);
                element = new Square(Color.GRAY, i, j, border);
                row.add(element);
                this.add(element);
            }
            this.squares.add(row);
        }
    }
    public void repaint(){
        for( Component component: this.getComponents()){
            component.repaint();
        }
    }
    public void drawRandomPipes(){
        ArrayList<ArrayList<Square>> newSquares = new ArrayList<>();
        for(int rowIndex = 0; rowIndex<this.fieldSize; rowIndex++){
            ArrayList<Square> row = new ArrayList<>();
            for(int columnIndex = 0; columnIndex<this.fieldSize; columnIndex++){
                Square square = this.squares.get(rowIndex).get(columnIndex);
                if(square.getBackground() == Color.BLUE){
                    Square newSquare = new Square(Color.magenta, rowIndex, columnIndex, null);
                    this.remove(square);
                    row.add(newSquare);
                    this.add(newSquare);
                }
                else{
                    Square newSquare = new Square(Color.GRAY, rowIndex, columnIndex, null);
                    this.remove(square);
                    row.add(newSquare);
                    this.add(newSquare);
                }
            }
            newSquares.add(row);
        }
    }

    public int getFieldSize(){
        return this.fieldSize;
    }
}
