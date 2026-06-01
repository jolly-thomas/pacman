package org.example.impl;

import javax.swing.*;
import java.awt.*;

public class ScorePanel extends JPanel {
    private  JLabel scoreLabel;
    private  JLabel lifeLabel;
    ScorePanel(int width, int height){
        setBackground(Constants.BACKGROUND);
        setPreferredSize(new Dimension(width, height));
        setLayout(new FlowLayout(
                FlowLayout.CENTER,
                200,
                20
        ));
        scoreLabel= new JLabel(Constants.SCORE_LABEL + "0");
        scoreLabel.setForeground(Constants.SCORE_LABEL_COLOR);
        scoreLabel.setFont(new Font(Constants.SCORE_LABEL_NAME,Constants.SCORE_LABEL_FONT,Constants.SCORE_LABEL_SIZE));
        add(scoreLabel);

        lifeLabel= new JLabel(Constants.LIFE_LABEL + "3");
        lifeLabel.setForeground(Constants.LIFE_LABEL_COLOR);
        lifeLabel.setFont(new Font(Constants.LIFE_LABEL_NAME,Constants.LIFE_LABEL_FONT,Constants.LIFE_LABEL_SIZE));
        add(lifeLabel);
    }
    public  void setScore(int score){
        scoreLabel.setText(Constants.SCORE_LABEL + score);
    }
    public void setLife(int life){
        lifeLabel.setText(Constants.LIFE_LABEL + life);
    }
}
