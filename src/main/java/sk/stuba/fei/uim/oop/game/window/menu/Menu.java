package sk.stuba.fei.uim.oop.game.window.menu;

import lombok.Getter;
import sk.stuba.fei.uim.oop.game.logic.GameLogic;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Menu extends JPanel{
    @Getter
    private JSlider sizeSetter;
    @Getter
    private JLabel sizeLabel;
    @Getter

    private JLabel levelLabel;
    @Getter
    private JButton resetButton;
    @Getter
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
                                this.levelLabel,
                                this.resetButton,
                                this.checkPathButton};
    }
    private JSlider initCombo(){
        JSlider slider = new JSlider(JSlider.HORIZONTAL, 8, 16, 8);
        slider.setMinorTickSpacing(2);
        slider.setMajorTickSpacing(2);
        slider.setSnapToTicks(true);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setFocusable(false);
        return slider;
    }
    private void initComponents(){
        this.sizeSetter = initCombo();
        this.sizeLabel = new JLabel("Size: 8");
        this.levelLabel = new JLabel("Level: 1");
        this.resetButton = new JButton("RESET");
        this.resetButton.setFocusable(false);
        this.checkPathButton = new JButton("Check path");
        this.checkPathButton.setFocusable(false);
    }
    public void addListenerForAll(GameLogic logic){
        this.sizeSetter.addChangeListener(logic);
    }
}
