package sk.stuba.fei.uim.oop.game.logic;

import sk.stuba.fei.uim.oop.game.window.Window;
import sk.stuba.fei.uim.oop.game.window.gameField.GameField;
import sk.stuba.fei.uim.oop.game.window.gameField.Side;
import sk.stuba.fei.uim.oop.game.window.gameField.Square;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Random;

public class GameLogic extends UniversalAdapter{
    private Window window;
    private Random randomizer;
    private int level;
    public GameLogic(Window window){
        this.window = window;
        this.randomizer = new Random();
        this.level = 1;
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
        randDFS(start);
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
    private void randDFS(Square square){
        markVisited(square);
        Square nextSquare = randUnvisitedNeighbour(square);
        while (nextSquare != null){
            connectSquares(square, nextSquare);
            randDFS(nextSquare);
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
                current.getRightSides().add(Side.West);
                current.getCurrentSides().add(Side.West);
                putPipesByColor(current);
            }
            else if(current.equals(finish) && current.getBackground() == Color.BLUE){
                current.getRightSides().add(Side.East);
                current.getCurrentSides().add(Side.East);
                putPipesByColor(current);
            }
            turnRandomly(current);
        }
    }
    private void turnRandomly(Square square){
        int randomTurn = this.randomizer.nextInt(5);
        for(int i=0;i<randomTurn;i++){
            square.turn();
        }
        square.getInitialSides().clear();
        for(Side side: square.getCurrentSides()){
            square.getInitialSides().add(side);
        }
    }
    private void goTo(Square square){
        ArrayList<Square> neighbours = getNeighbours(square, Color.YELLOW);
        validateNeighbours(square, neighbours);
        for(Side side: square.getCurrentSides()){
            if(!square.getRightSides().contains(side)){
                return;
            }
        }
        square.setHighlighted(true);
        this.window.getField().repaint();
        if(square == this.window.getField().getFinishSquare()){
            setNewPath();
            this.level +=1;
            this.window.getMenu().getLevelLabel().setText("Level: "+String.valueOf(this.level));
            return;
        }
        for(Square neighbour: neighbours){
            if(!neighbour.isHighlighted()){
                goTo(neighbour);
            }
        }
    }
    private void checkPath(){
        Square square = this.window.getField().getStartSquare();
        goTo(square);
    }
    private void putPipesByColor(Square square){
        ArrayList<Square> neighbours = getNeighbours(square, Color.YELLOW);
        validateNeighbours(square, neighbours);
        for(Square neighbour: neighbours){
            Side side = square.getNeighbourSide(neighbour);
            if(side!=null){
                square.getRightSides().add(side);
                square.getCurrentSides().add(side);
            }
        }
    }
    private void markVisited(Square square){
        square.setBackground(Color.RED);
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        Component current = this.window.getField().getComponentAt(e.getPoint());
        if(!(current instanceof Square) && current != null){
            return;
        }
        if(current != null){
            ((Square) current).turn();
        }
    }
    @Override
    public void stateChanged(ChangeEvent e) {
        super.stateChanged(e);
        JSlider slider = this.window.getMenu().getSizeSetter();
        if(e.getSource() == slider) {
            JLabel sizeLabel = this.window.getMenu().getSizeLabel();
            sizeLabel.setText("Size: " + slider.getValue());
            this.level = 1;
            this.window.getMenu().getLevelLabel().setText("Level: "+String.valueOf(this.level));
            String size = Integer.valueOf(slider.getValue()).toString();
            GameField newField = new GameField(Integer.parseInt(size));
            newField.setFieldSize(Integer.parseInt(size));
            this.window.remove(this.window.getField());
            this.window.setField(newField);
            createPath();
            this.window.getField().addMouseListener(this);
            this.window.getField().addMouseMotionListener(this);
            this.window.addKeyListener(this);
//            this.window.getField().drawRandomPipes();
        }
    }

    private void setNewPath(){
        int size = this.window.getField().getFieldSize();
        this.window.remove(this.window.getField());
        this.window.setField(new GameField(size));
        this.window.getField().repaint();
        this.window.getField().addMouseListener(this);
        this.window.getField().addMouseMotionListener(this);
        createPath();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        super.actionPerformed(e);
        if(e.getSource() == this.window.getMenu().getResetButton()){
            resetField();
        }
        else{
            checkPath();
        }
    }
    private void resetField(){
        this.level = 1;
        this.window.getMenu().getLevelLabel().setText("Level: "+this.level);
        setNewPath();
    }
    @Override
    public void keyPressed(KeyEvent e) {
        super.keyPressed(e);
        if(e.getKeyChar() == 'r' || e.getKeyChar() == 'R'){
            resetField();
        }
        else if(e.getKeyChar() == '\n'){
            checkPath();
        }
        else if(e.getKeyCode() == 27){
            this.window.dispose();
        }
    }
    @Override
    public void mouseMoved(MouseEvent e) {
        Component current = this.window.getField().getComponentAt(e.getPoint());
        if(!(current instanceof Square)){
            return;
        }
        ((Square) current).setHighlighted(true);
        this.window.getField().repaint();
    }

    @Override
    public void mouseExited(MouseEvent e) {
        this.window.getField().repaint();
    }
}
