    package org.example.impl;

    import java.awt.*;

    public class Constants {
        public static final int ROWS = 21;
        public static final int COLUMNS = 19;
        public static final int PIXEL_SIZE = 32;
        public static final int WIDTH = COLUMNS * PIXEL_SIZE;
        public static final int HEIGHT = ROWS * PIXEL_SIZE;
        public static final Color BACKGROUND = Color.BLACK;
        public static final Color FOOD_COLOR = Color.WHITE;
        public static final String basePath = "/assets/";
        public static final String audioBasePath = "/assets/pacman-audio/";
        public static final String INTRO = audioBasePath + "pacman_beginning.wav";
        public static final String DEATH = audioBasePath + "pacman_death.wav";
        public static final String EAT = audioBasePath + "pacman_chomp.wav";
        public static final String WALL = basePath + "wall.png";
        public static final String ORANGE = basePath + "orangeGhost.png";
        public static final String BLUE = basePath + "blueGhost.png";
        public static final String RED = basePath + "redGhost.png";
        public static final String PINK = basePath + "pinkGhost.png";
        public static final String PACMANUP = basePath + "pacmanUp.png";
        public static final String PACMANDOWN = basePath + "pacmanDown.png";
        public static final String PACMANLEFT = basePath + "pacmanLeft.png";
        public static final String PACMANRIGHT = basePath + "pacmanRight.png";
        public static final int PACMAN_SPEED = PIXEL_SIZE/8;
        public static final int GHOST_SPEED = PIXEL_SIZE/8;
        public static final int SCORE_INCREMENTER = 5;
        public static final String SCORE_LABEL = "SCORE: ";
        public static final Color SCORE_LABEL_COLOR = Color.WHITE;
        public static final String SCORE_LABEL_NAME = "Arial";
        public static final int SCORE_LABEL_FONT = Font.BOLD;
        public static final int SCORE_LABEL_SIZE = 20;
        public static final int SCORE_PANEL_WIDTH = WIDTH;
        public static final int SCORE_PANEL_HEIGHT = 50;
        public static final String LIFE_LABEL = "LIVES: ";
        public static final Color LIFE_LABEL_COLOR = Color.WHITE;
        public static final String LIFE_LABEL_NAME = "Arial";
        public static final int LIFE_LABEL_FONT = Font.BOLD;
        public static final int LIFE_LABEL_SIZE = 20;
        public static final int FPS = 16; // 100/16 -> 60 fps
    }
