/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package uiGhassan;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author ghassanansari
 */
public class Dice {
    public String name;
    public Image image;
  //  public int index = 0;
    final ImageView pic = new ImageView();
    
    public Dice(String name){
        this.name = name;
      //  image = new Image(getClass().getResourceAsStream("images/" + name + ".jpeg"), 75, 75, true, true);
    }
       final Dice[] dice = new Dice[]{
        new Dice ("D_Dice1"),
        new Dice ("D_Dice2"),
        new Dice ("D_Dice3"),
        new Dice ("D_Dice4"),
        new Dice ("D_Dice5"),
        new Dice ("D_Dice6")
    };
       
 /*
  /*  public void Roll(){
        int i = index;
        while (i == index){
        i = (int) (Math.random() * dice.length);
        }
        pic.setImage(dice[i].image);
        index = i;
    }*/
}
