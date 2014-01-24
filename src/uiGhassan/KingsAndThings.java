/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package uiGhassan;

//import java.awt.Insets;
import static java.rmi.Naming.list;

import java.util.ArrayList;

import static java.util.Collections.list;

import java.util.List;

import modelTestSam.GameModel;
import modelTestSam.NetworkedJSONGameLoop;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author ghassanansari
 */
public class KingsAndThings extends Application {
	
	//GHASSAN REMOVE THESE LATER
	GameModel gameModel;
	
	
	
	
     Image back = new Image(getClass().getResourceAsStream("images/Tuile_Back.png"), 100, 100, true, true); 
     Image desert = new Image(getClass().getResourceAsStream("images/Tuile-Desert.png"), 80, 80, true, true);
     Image backTile = new Image(getClass().getResourceAsStream("images/Tuile_Back.png"), 80, 80, true, true);
     Image dice = new Image(getClass().getResourceAsStream("images/D_Dice1.jpeg"), 40, 40, true, true);
     Hex[] hexes = new Hex[48];
     Dice dce;
     final ImageView dicepic = new ImageView();
     final ImageView dicepic2 = new ImageView();
     String name = "Rack";
     Pieces[] piece = new Pieces[351];
     final Label dice1 = new Label("1");
   //  final Label dice2 = new Label("1");
     final Dices[] dices = new Dices[] {
              new Dices("D_Dice1"),
              new Dices("D_Dice2"),
              new Dices("D_Dice3"),
              new Dices("D_Dice4"),
              new Dices("D_Dice5"),
              new Dices("D_Dice6")
             };
 
     
    @Override
    public void start(Stage primaryStage) {
    	gameModel = new GameModel(new NetworkedJSONGameLoop());
    	
    	//so a simple association from the gameModel to the View's stuff could be like
    	
        
        loadHexes();
        loadPieces();
        loadBackTiles();
        
        Pane root = new Pane();
        Image image = new Image(getClass().getResourceAsStream("images/Bowl.png"), 100, 100, true, true);
        Image image2 = new Image(getClass().getResourceAsStream("images/Tuile-Desert.png"), 75, 75, true, true);
        Image Player1_Rack = new Image(getClass().getResourceAsStream("images/Rack.png"), 150, 150, true, true);
        Image Player2_Rack = new Image(getClass().getResourceAsStream("images/Rack.png"), 150, 150, true, true); 
        Image Player3_Rack = new Image(getClass().getResourceAsStream("images/Rack.png"), 150, 150, true, true);
        Image Player4_Rack = new Image(getClass().getResourceAsStream("images/Rack.png"), 150, 150, true, true);
        Button rollButton = new Button("Roll");
  
        
        ImageView img = new ImageView();
        final ImageView img2 = new ImageView();
        ImageView PR1_img = new ImageView();
        ImageView PR2_img = new ImageView();
        ImageView PR3_img = new ImageView();
        ImageView PR4_img = new ImageView();
        
        img.setImage(image);
        img.relocate(400, 0);
        img2.setImage(image2);
        img2.relocate(0, 0);
        PR1_img.setImage(Player1_Rack);
        PR1_img.relocate(550,0);
        PR2_img.setImage(Player2_Rack);
        PR2_img.relocate(550,80);
        PR3_img.setImage(Player3_Rack);
        PR3_img.relocate(550,160);
        PR4_img.setImage(Player4_Rack);
        PR4_img.relocate(550,240);
        rollButton.relocate(100, 550);
      //  pic.setPreserveRatio(true);
        dicepic.relocate(0, 500);
        dicepic.setImage(dice);
       dicepic2.relocate(0, 550);
        dicepic2.setImage(dice);
   //     dicepic.relocate(0, 500);
//        dice2.relocate(0, 550);
       
         
        final VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(0, 10, 0, 10));
       vbox.getChildren().addAll(dicepic,dicepic2);
        
            rollButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
              //Roll();
               // vbox.setVisible(true)
               int index = -1;
                int i = index;
                int a = index;
                while (i == index){
                  i = (int)(Math.random() * dices.length);
                 
                }
                 while (a == index){
                  a = (int)(Math.random() * dices.length);
                 
                }
                dicepic.setImage(dices[i].imagess);
                dicepic2.setImage(dices[a].imagess);
                 index = i;
            }
        });
            
        root.getChildren().add(img);
        root.getChildren().add(img2);
        root.getChildren().add(PR1_img);
        root.getChildren().add(PR2_img);
        root.getChildren().add(PR3_img);
        root.getChildren().add(PR4_img);
        root.getChildren().addAll(rollButton);
//       root.getChildren().add(dicepic);
      //  root.getChildren().add(vbox);
        root.getChildren().add(dicepic);
        root.getChildren().add(dicepic2);
       
        addHexesTo(root);
        addPiecesTo(root);
        addBackTiles(root);
        
        HBox hBox1 = new HBox();
        hBox1.setPrefWidth(75);
        hBox1.setPrefHeight(75);
        hBox1.setStyle("-fx-border-color: grey;"
              + "-fx-border-width: 1;"
              + "-fx-border-style: solid;");
        hBox1.relocate(0,0);
        root.getChildren().add(hBox1);
        
        
            
           //  pieceHBox.relocate(450,30);
        //root.getChildren().add(pieceHBox);
        
        Scene scene = new Scene(root, 750, 600);
        scene.getStylesheets().add
            (KingsAndThings.class.getResource("style.css").toExternalForm());
       // ((VBox) scene.getRoot()).getChildren().addAll(vbox);
        primaryStage.setTitle("Kings And Things");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
        
      
        
    }
    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    
    public static void main(String[] args) {
        launch(args);
    }
    

   
    private void loadPieces(){
        int y = 30;
        for (int i =0; i<351; i++){
            piece[i] = new Pieces(450,y);
           
        }
    }

    private void addPiecesTo(Pane root) {
        ObservableList<Node> children = root.getChildren();
        
        for (Pieces p : piece ) {
            if(p==null){ continue; }
            HBox pieceHBox = new HBox();
            pieceHBox.setPrefWidth(20);
            pieceHBox.setPrefHeight(20);
            pieceHBox.setStyle("-fx-border-color: grey;"
              + "-fx-border-width: 1;"
              + "-fx-border-style: solid;"
            + "-fx-background-color: blue;");
            pieceHBox.relocate(p.X,p.Y);
            children.add(pieceHBox);
        }
    }
    
    private void loadHexes() {
    	
    	//SAM ADDED, GHASSAN MODIFY
    	
        int y = 193;
        for (int i=0; i<4; i++){
            hexes[i] = new Hex(16, y);
            y += 75;
        }
        
        //SAM ADDED, GHASSAN MODIFY
        System.out.println("Model has " + gameModel.hexes.size());
        
        for (int i = 0; i < gameModel.hexes.size(); i++) {
        	hexes[i].setModel(gameModel.hexes.get(i));
        	System.out.printf("BOUND %s to %s\n", hexes[i], gameModel.hexes.get(i));
        }
        
        //lets say you want the model to change based on your view actions
        //you'd do like
        /*onDrag(Hex h) {
        	hex.associatedModel.<whatever function to change the model>
    	}
          
        */
    }
    
   private void addHexesTo(Pane root) {
       
        ObservableList<Node> children = root.getChildren();
       
        for(Hex h : hexes){
             
            if(h==null){ continue; }
            System.out.println(h);
            ImageView view = new ImageView();
            view.setImage(desert);
            view.setX(h.X);
            view.setY(h.Y);
            children.add(view);
            
        }
       
      /*  ImageView view = new ImageView();
            view.setImage(desert);
            view.relocate(16, 193);
            children.add(view);*/
    
     }
   
   public void loadBackTiles(){
       int y = 0;
       int x = 0;
       for (int i = 0; i<48; i++){
           hexes[i] = new Hex(x,y);
       }
   }
  
   
   public void addBackTiles(Pane root){
       ObservableList<Node> children = root.getChildren();
       
        for(Hex h : hexes){
             
            if(h==null){ continue; }
            System.out.println(h);
            ImageView view = new ImageView();
            view.setImage(backTile);
            view.setX(h.X);
            view.setY(h.Y);
            children.add(view);
   }
   }
     public void Roll(){
        int index = -1;
        int i = index;
        while (i == index){
        i = (int) (Math.random() * dce.dice.length);
        }
        dice1.setText(dce.dice[i].name);
        index = i;
    }
     
     private static final class DragContext {
        public double mouseAnchorX;
        public double mouseAnchorY;
        public double initialTranslateX;
        public double initialTranslateY;
    }
     
      private class Dices {
        public String name;
        public String description;
        public String binNames;
        public Image imagess;
        public Dices(String name) {
            this.name = name;
            imagess = new Image(getClass().getResourceAsStream("images/" + name + ".jpeg"),40, 40, true, true);
        }
    }

     
}

