package sk.stuba.fei.uim.oop.game.window.menu;

import lombok.Getter;
import sk.stuba.fei.uim.oop.game.logic.GameLogic;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Menu extends JPanel{
    @Getter
    private JComboBox<String> sizeSetter;
    @Getter
    private JLabel sizeLabel;
    private JLabel levellabel;
    private JButton resetButton;
    private JButton checkPathButton;
    public Menu(){
        this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        initComponents();
        JComponent[] menu_components = getElements();
        for (JComponent menuComponent : menu_components) {
            this.add(menuComponent);
            this.add(Box.createRigidArea(new Dimension(20, 0)));
        }
        this.setBackground(Color.ORANGE);
        this.setVisible(true);
    }
    private JComponent[] getElements(){
        return new JComponent[]{this.sizeSetter,
                                this.sizeLabel,
                                this.levellabel,
                                this.resetButton,
                                this.checkPathButton};
    }
    private JComboBox<String> initCombo(){
        String[] sizes = { "8x8",
                            "10x10",
                            "12x12",
                            "14x14",
                            "16x16"};
        return new JComboBox<>(sizes);
    }
    private void initComponents(){
        this.sizeSetter = initCombo();
        this.sizeLabel = new JLabel("Size: 8x8");
        this.levellabel = new JLabel("Level: 1");
        this.resetButton = new JButton("RESET");
        this.checkPathButton = new JButton("Check path");
    }
    public void addListenerForAll(GameLogic logic){
        this.sizeSetter.addActionListener(logic);
    }
}
