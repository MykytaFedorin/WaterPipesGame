package sk.stuba.fei.uim.oop.game.logic;

import sk.stuba.fei.uim.oop.game.window.Window;
import sk.stuba.fei.uim.oop.game.window.gameField.Direction;
import sk.stuba.fei.uim.oop.game.window.gameField.GameField;
import sk.stuba.fei.uim.oop.game.window.gameField.Side;
import sk.stuba.fei.uim.oop.game.window.gameField.Square;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class GameLogic extends UniversalAdapter{
    private Window window;
    private Random randomizer;
    public GameLogic(Window window){
        this.window = window;
        this.randomizer = new Random();
        createPath();
    }
    private void setColorToAll(){
        for(Component component: this.window.getField().getComponents()){
            if(component instanceof Square){
                Square square = ((Square) component);
                if(square.getBackground() != Color.BLUE){
                    square.setBackground(Color.YELLOW);
                }
                square.setColor(square.getBackground());
            }
        }
    }
    public void createMaze(){
        Square start = this.window.getField().getStartSquare();
        Square finish = this.window.getField().getFinishSquare();
        int size = this.window.getField().getFieldSize();
        randDFS(start, size);
        start.changeBorder("left");
        finish.changeBorder("right");
    }
    public void solveMaze(){
        Square start = this.window.getField().getStartSquare();
        makeStep(start);
        this.window.getField().repaint();
    }
    private boolean makeStep(Square square){
        square.setBackground(Color.BLUE);
        ArrayList<Square> neighbours = getNeighbours(square, Color.BLUE);
        validateNeighbours(square, neighbours);
        boolean state = false;
        Square finish = this.window.getField().getFinishSquare();
        boolean squareIsLast = square == finish;
        if(neighbours.size()==0 && !squareIsLast){
            return false;
        }
        else if(squareIsLast){
            return true;
        }
        else{
            for(Square neighbour: neighbours){
                state = makeStep(neighbour);
                if(!state){
                    neighbour.setBackground(Color.YELLOW);
                }
                else{
                    neighbour.setBackground(Color.BLUE);
                    return true;
                }
            }
            return state;
        }
    }
    private void validateNeighbours(Square target, ArrayList<Square> neighbours){
        neighbours.removeIf(neighbour -> !target.isConnectedTo(neighbour));
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
    private void connectSquares(Square square, Square nextSquare){
        nextSquare.setBackground(Color.RED);
        int rowIndex = square.getRowIndex();
        int columnIndex = square.getColumnIndex();
        int nextRowIndex = nextSquare.getRowIndex();
        int nextColumnIndex = nextSquare.getColumnIndex();

        if(rowIndex == nextRowIndex){
            if(columnIndex>nextColumnIndex){
                square.changeBorder("left");
                nextSquare.changeBorder("right");
            }
            else if(columnIndex<nextColumnIndex){
                square.changeBorder("right");
                nextSquare.changeBorder("left");
            }
        }
        else if(columnIndex == nextColumnIndex){
            if(rowIndex>nextRowIndex){
                square.changeBorder("top");
                nextSquare.changeBorder("bottom");
            }
            else if(rowIndex<nextRowIndex){
                square.changeBorder("bottom");
                nextSquare.changeBorder("top");
            }
        }
    }
    private Square randUnvisitedNeighbour(Square square) {
        ArrayList<Square> neighbours = getNeighbours(square, Color.RED);
        if(neighbours.size() == 0){
            return null;
        }
        int index = this.randomizer.nextInt(neighbours.size());
        return neighbours.get(index);
    }
    private ArrayList<Square> getNeighbours(Square square, Color color){
        ArrayList<ArrayList<Square>> allSuares = this.window.getField().getSquares();
        ArrayList<Square> neighbours = new ArrayList<>();
        int[] pos1 = new int[]{square.getColumnIndex(), square.getRowIndex() - 1};
        int[] pos2 = new int[]{square.getColumnIndex(), square.getRowIndex()+1};
        int[] pos3 = new int[]{square.getColumnIndex()-1, square.getRowIndex()};
        int[] pos4 = new int[]{square.getColumnIndex()+1, square.getRowIndex()};
        int[][] positions = {pos1, pos2, pos3, pos4};
        for (int[] pos : positions) {
            try {
                Square neighbour = allSuares.get(pos[1]).get(pos[0]);
                if(neighbour.getBackground() != color){
                    neighbours.add(neighbour);
                }
            } catch (IndexOutOfBoundsException ignored) {

            }
        }
        return neighbours;
    }
    private void createPath(){
        createMaze();
        solveMaze();
        setColorToAll();
        putPipes();
    }
    private void putPipes(){
        Square start = this.window.getField().getStartSquare();
        Square finish = this.window.getField().getFinishSquare();
        for(Component component: this.window.getField().getComponents()){
            Square current = ((Square) component);
            if(!current.equals(start) && !current.equals(finish) && current.getBackground() == Color.BLUE){
                putPipesByColor(current);
            }
            else if(current.equals(start) && current.getBackground() == Color.BLUE){
                current.getSides().add(Side.West);
                putPipesByColor(current);
            }
            else if(current.equals(finish) && current.getBackground() == Color.BLUE){
                current.getSides().add(Side.East);
                putPipesByColor(current);
            }
        }
    }
    private void putPipesByColor(Square square){
        ArrayList<Square> neighbours = getNeighbours(square, Color.YELLOW);
        validateNeighbours(square, neighbours);
        for(Square neighbour: neighbours){
            Side side = square.getNeighbourSide(neighbour);
            if(side!=null){
                square.getSides().add(side);
            }
        }
    }
    private void markVisited(Square square){
        square.setBackground(Color.RED);
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        Component current = this.window.getField().getComponentAt(e.getPoint());
        System.out.println("irfbirhjbf2ij");
        if(!(current instanceof Square) && current != null){
            return;
        }
        if(current != null){
            ((Square) current).turn();
        }
    }
    @Override
    public void itemStateChanged(ItemEvent e) {
        if(e.getStateChange() == ItemEvent.SELECTED){
            JComboBox<String> combo = this.window.getMenu().getSizeSetter();
            JLabel sizeLabel = this.window.getMenu().getSizeLabel();
            if(e.getSource() == combo) {
                sizeLabel.setText("Size: "+combo.getSelectedItem());
            }
            String size = Objects.requireNonNull(combo.getSelectedItem()).toString();
            this.window.remove(this.window.getField());
            this.window.setField(new GameField(Integer.parseInt(size)));
            window.getField().addMouseListener(this);
            createPath();
//            this.window.getField().drawRandomPipes();
        }
    }
}
