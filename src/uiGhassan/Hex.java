/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package uiGhassan;

import modelTestSam.HexModel;

/**
 *
 * @author ghassanansari
 */
public class Hex {
    
	//SAM ADDED - GHASSAN MODIFY
	public HexModel associatedModel;
	
    boolean isRevealed = false;
    int X, Y;

    Hex(int x, int y) {
        X = x;
        Y = y;
    }
    
    
    //SAM ADDED - GHASSAN MODIFY
    public void setModel(HexModel x) {
    	associatedModel = x;
    }
    
    @Override
    public String toString(){
        
        return "Hex(" + X + ", " + Y +")["+isRevealed+"]";
        
    }
}
