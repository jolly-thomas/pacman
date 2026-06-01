package org.example.impl;

import javax.swing.*;
import java.awt.*;

public class Grid {
    public void initialize_frame(){
    JFrame frame = new JFrame();
        ScorePanel scorePanel = new ScorePanel(Constants.SCORE_PANEL_WIDTH,Constants.SCORE_PANEL_HEIGHT);
        Pacman pacman = new Pacman(Constants.WIDTH,Constants.HEIGHT,scorePanel);
    frame.setTitle("Pacman");
    frame.setSize(Constants.WIDTH,Constants.HEIGHT);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setLocationRelativeTo(null);
    frame.add(pacman, BorderLayout.CENTER);
    frame.add(scorePanel,BorderLayout.NORTH);
    frame.pack();
    frame.setVisible(true);
}}
