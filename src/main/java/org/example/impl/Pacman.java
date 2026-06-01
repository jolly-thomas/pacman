package org.example.impl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class Pacman extends JPanel {
    private ScorePanel scorePanel;
    Pacman(int width,int height,ScorePanel scorePanel){
    setBackground(Constants.BACKGROUND);
    setPreferredSize(new Dimension(width,height));
    this.scorePanel=scorePanel;
    loadMap(tiles);
    setupGame();
    soundManager.play(Constants.INTRO);
}   private InputMap inputMap = getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
    private ActionMap actionMap = getActionMap();
    private boolean gameStarted = false;
    private Direction requestedDirection = Direction.RIGHT;
    private Direction currentDirection = requestedDirection;
    private Timer timer;
    private SoundManager soundManager = new SoundManager();
    private List<Block> wall = new ArrayList<>();
    private List<Block> ghost = new ArrayList<>();
    private List<Block> food = new ArrayList<>();
    private Direction [] direction = Direction.values();
    private HashMap <Direction,Integer> avail = new HashMap<>();
    private Block pacman ;
    private Block dummyGhost = new Block(null,0,0,0,0,null,null);
    private Block dummyPacman = new Block(null,0,0,0,0,null,null);
    private int score = 0;
    private int life = 3;
    private final Image pacmanLeft = loadAsset(Constants.PACMANLEFT);
    private final Image pacmanRight = loadAsset(Constants.PACMANRIGHT);
    private final Image pacmanUp = loadAsset(Constants.PACMANUP);
    private final Image pacmanDown = loadAsset(Constants.PACMANDOWN);
   private  String[] tiles ={
            "XXXXXXXXXXXXXXXXXXX",
            "X        X        X",
            "X XX XXX X XXX XX X",
            "X                 X",
            "X XX X XXXXX X XX X",
            "X    X       X    X",
            "XXXX XXXX XXXX XXXX",
            "OOOX X       X XOOO",
            "XXXX X XXrXX X XXXX",
            "X       bpo       X",
            "XXXX X XXXXX X XXXX",
            "OOOX X       X XOOO",
            "XXXX X XXXXX X XXXX",
            "X        X        X",
            "X XX XXX X XXX XX X",
            "X  X     P     X  X",
            "XX X X XXXXX X X XX",
            "X    X   X   X    X",
            "X XXXXXX X XXXXXX X",
            "X                 X",
            "XXXXXXXXXXXXXXXXXXX"
    };
    public Image loadAsset(String path){
        return new ImageIcon(getClass().getResource(path)).getImage();
    }

    public void paintComponent(Graphics graphics){
        super.paintComponent(graphics);
        draw(graphics);
    }

    public void draw(Graphics graphics){
        graphics.drawImage(pacman.image, pacman.x, pacman.y,pacman.width, pacman.height, null);
        for (Block wall :wall){
            graphics.drawImage(wall.image, wall.x, wall.y, wall.width, wall.height, null);
        }
        for (Block ghost:ghost){
            graphics.drawImage(ghost.image, ghost.x, ghost.y, ghost.width, ghost.height, null);
        }
        graphics.setColor(Constants.FOOD_COLOR);
        for(Block food:food) {
            graphics.fillRect(food.x, food.y, food.width, food.height);
        }
    }
    public void updateGame(){
        movePacman();
        eat();
        moveGhost();
        caught();
    }
    public void caught(){
        if(checkCollision(ghost,pacman)){
            life--;
            gameOver();
        }
    }

    public void movePacman(){
        int x = pacman.x;
        int y = pacman.y;
        dummyPacman.width = pacman.width;
        dummyPacman.height = pacman.height;
        dummyPacman.x=x;
        dummyPacman.y=y;
        switch (requestedDirection) {
            case Direction.LEFT:
                dummyPacman.x -= Constants.PACMAN_SPEED;
                if (!checkCollision(wall,dummyPacman)){
                    currentDirection = requestedDirection;
                    pacman.x= dummyPacman.x;
                    pacman.image=pacmanLeft;
                }
                else{
                    if(currentDirection.equals(requestedDirection)){
                        pacman.x=x;
                    }
                    else{
                        requestedDirection = currentDirection;}
                }
                break;
            case Direction.RIGHT:
                dummyPacman.x += Constants.PACMAN_SPEED;
                if (!checkCollision(wall,dummyPacman)){
                    currentDirection = requestedDirection;
                    pacman.x= dummyPacman.x;
                    pacman.image=pacmanRight;
                }
                else{
                    if(currentDirection.equals(requestedDirection)){
                        pacman.x=x;
                    }
                    else{
                        requestedDirection = currentDirection;}
                }
                break;
            case Direction.UP:
                dummyPacman.y -= Constants.PACMAN_SPEED;
                if (!checkCollision(wall,dummyPacman)){
                    currentDirection = requestedDirection;
                    pacman.y= dummyPacman.y;
                    pacman.image=pacmanUp;
                }
                else{
                    if(currentDirection.equals(requestedDirection)){
                        pacman.y=y;
                    }
                    else{
                        requestedDirection = currentDirection;}
                }
                break;
            case Direction.DOWN:
                dummyPacman.y += Constants.PACMAN_SPEED;
                if (!checkCollision(wall,dummyPacman)){
                    currentDirection = requestedDirection;
                    pacman.y= dummyPacman.y;
                    pacman.image=pacmanDown;
                }
                else{
                    if(currentDirection.equals(requestedDirection)){
                        pacman.y=y;
                    }
                    else{
                        requestedDirection = currentDirection;}
                }
                break;

        }
    }
    public void eat(){
        Block foodItem = null;
        for (Block item : food){
            if(collision(item,pacman)){
                score += Constants.SCORE_INCREMENTER;
                foodItem = item;
            }
        }
        food.remove(foodItem);
        scorePanel.setScore(score);
    }
    public void gameOver(){
        if(food.isEmpty()){
            System.out.println(" You Win!");
            score=0;
            life =3;
            reset();
        }
        else if(life == 0){
            soundManager.play(Constants.DEATH);
            score = 0;
            reset();
        }
        else if(life > 0){
            soundManager.play(Constants.DEATH);
            reset();
        }
    }
    public void reset(){
        scorePanel.setScore(score);
        scorePanel.setLife(life);
        gameStarted=false;
        timer.stop();
        loadMap(tiles);
        timer=null; // so that it starts again
        requestedDirection=Direction.RIGHT; // default direction after pressing space
    }
    public void checkAvailDirections(Block ghost){
        avail.clear();
        for(Direction dir : direction){
            int x = ghost.x;
            int y= ghost.y;
            dummyGhost.width = ghost.width;
            dummyGhost.height=ghost.height;
            dummyGhost.x=x;
            dummyGhost.y=y;
            switch(dir){
                case Direction.LEFT :
                    dummyGhost.x -= Constants.GHOST_SPEED;
                    if(!checkCollision(wall,dummyGhost)){
                        avail.put(dir,dummyGhost.x);
                    }
                    break;
                case Direction.RIGHT:
                    dummyGhost.x += Constants.GHOST_SPEED;
                    if(!checkCollision(wall,dummyGhost)){
                        avail.put(dir,dummyGhost.x);
                    }
                    break;
                case Direction.UP :
                    dummyGhost.y -= Constants.GHOST_SPEED;
                    if(!checkCollision(wall,dummyGhost)){
                        avail.put(dir,dummyGhost.y);
                    }
                    break;
                case Direction.DOWN :
                    dummyGhost.y += Constants.GHOST_SPEED;
                    if(!checkCollision(wall,dummyGhost)){
                        avail.put(dir,dummyGhost.y);
                    }
                    break;
            }

        }
    }
    public void moveghostAsPerDirection(Direction direction, Block block, int coordinate){
        switch(direction){
            case Direction.LEFT : block.x = coordinate;
                block.direction=direction;
                break;
            case Direction.RIGHT : block.x = coordinate;
                block.direction=direction;
                break;
            case Direction.UP : block.y = coordinate;
                block.direction=direction;
                break;
            case Direction.DOWN : block.y = coordinate;
                block.direction=direction;
                break;
        }
    }
    public void moveGhost(){
        for(Block ghost : ghost){
            // check in a particular position ghost can move in which directions
            checkAvailDirections(ghost);

            List <Direction> choose = new ArrayList<>(avail.keySet());
            // randomly choose a direction from available directions
            Direction chosen = choose.get((int)(Math.random() * choose.size()));

            /*        ^
                    | | |
                    | p |      if avail directions are two then don't move randomly keep moving in
                    | | |      in the same direction as before
                      v

             */
                if(avail.size() == 2 && avail.containsKey(ghost.direction)){
                    moveghostAsPerDirection(ghost.direction,ghost, avail.get(ghost.direction));
                }
                else{ moveghostAsPerDirection(chosen,ghost, avail.get(chosen));}


        }
    }

    public boolean checkCollision(List<Block> walls,Block hero ){
        for(Block wall: walls){
            if (collision(wall,hero)){
                return true;};
        }
        return false;
    }
    public boolean collision(Block wall, Block hero){
        return  hero.x+ hero.width > wall.x &&
                hero.x < wall.x +wall.width &&
                hero.y +hero.height > wall.y &&
                hero.y < wall.y+ wall.height;
    };
    public void startGameLoop(){

        if(timer == null) {
            timer = new Timer(Constants.FPS, e -> {
                updateGame();
                repaint();
            });
            if(!timer.isRunning()) {
                timer.start();
            } }


    }
    public void setupGame(){
        inputMap.put(KeyStroke.getKeyStroke("SPACE"),"gameStart");
        actionMap.put("gameStart", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameStarted = true;
                System.out.println("Game started!");
                startGameLoop();
                soundManager.play(Constants.EAT,true);
            }
        });
        inputMap.put(KeyStroke.getKeyStroke("LEFT"),"moveLeft");
        actionMap.put("moveLeft", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                requestedDirection = Direction.LEFT;
            }
        });
        inputMap.put(KeyStroke.getKeyStroke("RIGHT"),"moveRight");
        actionMap.put("moveRight", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                requestedDirection = Direction.RIGHT;
            }
        });
        inputMap.put(KeyStroke.getKeyStroke("UP"),"moveUp");
        actionMap.put("moveUp", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                requestedDirection = Direction.UP;
            }
        });
        inputMap.put(KeyStroke.getKeyStroke("DOWN"),"moveDown");
        actionMap.put("moveDown", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                requestedDirection = Direction.DOWN;
            }
        });


    }
    public void loadMap(String[] tiles){
        wall.clear();
        ghost.clear();
        food.clear();

        for (int i=0;i<tiles.length;i++){
            for(int j=0;j<tiles[i].length();j++){
                switch(tiles[i].charAt(j)){
                    case 'X':  wall.add(
                            new Block(
                                    loadAsset(Constants.WALL),
                                    Constants.PIXEL_SIZE,
                                    Constants.PIXEL_SIZE,
                                    j * Constants.PIXEL_SIZE,
                                    i * Constants.PIXEL_SIZE,
                                    Direction.NONE,
                                    "Wall"
                            )
                    );
                    break;
                    case 'P':  pacman =
                            new Block(
                                    loadAsset(Constants.PACMANRIGHT),
                                    Constants.PIXEL_SIZE,
                                    Constants.PIXEL_SIZE,
                                    j * Constants.PIXEL_SIZE,
                                    i * Constants.PIXEL_SIZE,
                                    Direction.NONE,
                                    "Pacman"
                            );
                    break;
                    case 'o':  ghost.add(
                            new Block(
                                    loadAsset(Constants.ORANGE),
                                    Constants.PIXEL_SIZE,
                                    Constants.PIXEL_SIZE,
                                    j * Constants.PIXEL_SIZE,
                                    i * Constants.PIXEL_SIZE,
                                    Direction.RIGHT,
                                    "Orange"
                            )
                    );
                        break;
                    case 'b':  ghost.add(
                            new Block(
                                    loadAsset(Constants.BLUE),
                                    Constants.PIXEL_SIZE,
                                    Constants.PIXEL_SIZE,
                                    j * Constants.PIXEL_SIZE,
                                    i * Constants.PIXEL_SIZE,
                                    Direction.RIGHT,
                                    "Blue"
                            )
                    );
                    break;
                    case 'r':  ghost.add(
                            new Block(
                                    loadAsset(Constants.RED),
                                    Constants.PIXEL_SIZE,
                                    Constants.PIXEL_SIZE,
                                    j * Constants.PIXEL_SIZE,
                                    i * Constants.PIXEL_SIZE,
                                    Direction.UP,
                                    "Red"
                            )
                    );
                    break;
                    case 'p':  ghost.add(
                            new Block(
                                    loadAsset(Constants.PINK),
                                    Constants.PIXEL_SIZE,
                                    Constants.PIXEL_SIZE,
                                    j * Constants.PIXEL_SIZE,
                                    i * Constants.PIXEL_SIZE,
                                    Direction.LEFT,
                                    "Pink"
                            )
                    );
                    break;
                    case ' ':  food.add(
                            new Block(
                                    null,
                                    Constants.PIXEL_SIZE/8,
                                    Constants.PIXEL_SIZE/8,
                                    j * Constants.PIXEL_SIZE +14,
                                    i * Constants.PIXEL_SIZE +14,
                                    Direction.NONE,
                                    "Food"
                            )
                    );
                    break;
                }
            }
        }
    }
}
