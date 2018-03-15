package com.dawaegames.characters;

import com.dawaegames.objects.*;
import com.dawaegames.framework.GameObject;
import com.dawaegames.framework.ObjectId;
import com.dawaegames.game.Handler;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.LinkedList;
//All the imports for the player class

public class Player extends GameObject{
    
    private float width = 48, height = 96;
    private float gravity = 0.5f;
    private Handler handler;
    //Intiates all the variables for the player object
    
    public Player(float x, float y,Handler handler, ObjectId id)
    {
        super(x, y, id);
        this.handler = handler;
    }

    @Override
    public void tick(LinkedList <GameObject> object) {
        x += velX;
        y += velY;
        
        if(jumping || falling) {
            velY += gravity;
        }
        Collision(object);
        
        //This sets the x and y co-ordinents to wherever they move and will constantly update over and over
        //This checks whether the player is jumping or falling and if they are it will then activate gravity and their vertical velocity to jump
    }
    
    private void Collision(LinkedList<GameObject> object) 
    {
        for(int i = 0; i < handler.object.size(); i++)
        {
            GameObject tempObject = handler.object.get(i);
            
            if(tempObject.getId() == ObjectId.Block)
            {
                if(getBoundsTop().intersects(tempObject.getBounds()))
                {
                    y = tempObject.getY() + 32;
                    velY = 0;
                    //This checks to see if the top of the players model is colliding with the block object
                    //If they are then the verticla velocity is set to 0 so that they cannot move any higher
                } 
                
                if(getBounds().intersects(tempObject.getBounds()))
                {
                    y = tempObject.getY() - height;
                    velY = 0;
                    falling = false;
                    jumping = false;
                } 
                else falling = true;
                
                //This is where the collision with the floor takes place
                //It will first take the y co-ordinent of the block and reduce the height
                //If the player is colliding with the block itll change their vertical velocity to 0
                //It will then set both variables falling and jumping to false
                //However if the player object is not colliding with the block obejct then they will be falling
                //Hnece the falling variable is set to true
                
                if(getBoundsLeft().intersects(tempObject.getBounds()))
                {
                    x = tempObject.getX() + 35;
                    //This is where part of the collision detections take place
                    //It will check where the objects x co-ordinents and add 35 pixles to it so that they dont glitch inside
                } 
                if(getBoundsRight().intersects(tempObject.getBounds()))
                {
                    x = tempObject.getX() - width;
                    //This is where part of the collision detections take place
                    //It will check where the objects x co-ordinents and subtract the width of the object again so they dont glitch inside
                } 
            }
        }
    }
    
    
    @Override
    public void render(Graphics g){
        g.setColor(Color.green);
        g.fillRect((int)x, (int)y, (int)width, (int)height);
        
        Graphics2D g2d = (Graphics2D) g;
        g.setColor(Color.red);
        g2d.draw(getBounds());
        g2d.draw(getBoundsRight());
        g2d.draw(getBoundsLeft());
        g2d.draw(getBoundsTop());
        
        //This part draws all the collision blocks on screen
        //These shall not be visible in the end product
        //This is a test to make sure that they work in the correct way
    }
    
    @Override
    public Rectangle getBounds() {
        return new Rectangle((int) ((int)x+(width/2)-((width/2)/2)), (int) ((int)y+(height/2)), (int)width/2, (int)height/2);
    }
    public Rectangle getBoundsTop() {
        return new Rectangle((int) ((int)x+(width/2) - ((width/2)/2)), (int)y, (int)width/2, (int)height/2);
    } 
    public Rectangle getBoundsRight() {
        return new Rectangle((int) ((int)x+width-5), (int)y+5, (int)5, (int)height-10);
    }    
    public Rectangle getBoundsLeft() {
        return new Rectangle((int)x, (int)y+5, (int)5, (int)height-10);
    }    
   //This sets the collison blocks on the player object
    //They are based off of the size of the player object and will stay a view pixels off the edge of the object
    //This way they will not phase into the block and get stuck
}
