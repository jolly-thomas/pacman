package org.example.impl;

import javax.swing.*;
import java.awt.*;

public class Block {
     protected Image image;
     protected int width;
     protected int height;
     protected int x;
     protected int y;
     protected Direction direction;
     protected String name;
     Block(Image image, int width, int height, int x,int y,Direction direction,String name){
         this.image=image;
         this.width=width;
         this.height=height;
         this.x=x;
         this.y=y;
         this.direction=direction;
         this.name=name;
     };

}
