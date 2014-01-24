/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package uiGhassan;

/**
 *
 * @author ghassanansari
 */
public class Hex {
    
    boolean isRevealed = false;
    int X, Y;

    Hex(int x, int y) {
        X = x;
        Y = y;
    }
    
    @Override
    public String toString(){
        
        return "Hex(" + X + ", " + Y +")["+isRevealed+"]";
        
    }
}
