package uiGhassan;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
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

public class KingsAndThings extends Application {
    
    @Override
    public void start(Stage primaryStage) { 
        StackPane root = new StackPane();
        Image image = new Image(getClass().getResourceAsStream("images/Bowl.png"), 100, 100, true, true); 
       // Image image2 = new Image(getClass().getResourceAsStream("images/Tuile-Desert.png"), 100, 100, true, true);
        ImageView img = new ImageView();
    //   final ImageView img2 = new ImageView();
        img.setImage(image);
//        img2.setImage(image2);
        root.getChildren().add(img);
    //    root.getChildren().add(img2);
        
        
        HBox hBox1 = new HBox();
        hBox1.setPrefWidth(100);
        hBox1.setPrefHeight(100);
        hBox1.setStyle("-fx-border-color: red;"
              + "-fx-border-width: 1;"
              + "-fx-border-style: solid;");
        
        HBox hBox2 = new HBox();
        hBox2.setPrefWidth(450);
        hBox2.setPrefHeight(500);
        hBox2.setStyle("-fx-border-color: blue;"
              + "-fx-border-width: 1;"
              + "-fx-border-style: solid;");
         
        
        insertImage(new Image(getClass().getResourceAsStream("images/Tuile-Desert.png"), 100, 100, true, true), hBox1);
        insertImage(new Image(getClass().getResourceAsStream("images/Tuile-Jungle.png"), 100, 100, true, true), hBox2);
        
        setupGestureTarget(hBox1);
        setupGestureTarget(hBox2);
        
     /*   img2.setOnDragDetected(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
        /* drag was detected, start a drag-and-drop gesture*/
        /* allow any transfer mode */
//        Dragboard db = img2.startDragAndDrop(TransferMode.ANY);
        
        /* Put a string on a dragboard */
     /*   ClipboardContent content = new ClipboardContent();
        content.putImage(img2.getImage());
        db.setContent(content);
        
        event.consume();
    }
});*/
        
        
        
        
      //GridPane 
        final GridPane inputGridPane = new GridPane();
            GridPane.setConstraints(img, 3, 0);
//            GridPane.setConstraints(img2, 4, 0);
            inputGridPane.setHgap(6);
            GridPane.setConstraints(hBox1, 4, 0);
            GridPane.setConstraints(hBox2, 0, 2);
            inputGridPane.setVgap(6);
            inputGridPane.getChildren().addAll(img);
            inputGridPane.getChildren().addAll(hBox1);
            inputGridPane.getChildren().addAll(hBox2);
//            inputGridPane.getChildren().addAll(img2);
            inputGridPane.setPadding(new Insets(0, 10, 0, 10));
 
        root.getChildren().addAll(inputGridPane);
                
        Scene scene = new Scene(root, 750, 600);
        scene.getStylesheets().add
            (KingsAndThings.class.getResource("style.css").toExternalForm());
       
        
        primaryStage.setTitle("Kings And Things");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
    
     void insertImage(Image i, HBox hb){
         
        ImageView iv = new ImageView();
        iv.setImage(i);
         
        setupGestureSource(iv);
        hb.getChildren().add(iv);
    }
     
     void setupGestureTarget(final HBox targetBox){
         
        targetBox.setOnDragOver(new EventHandler <DragEvent>() {
            @Override
            public void handle(DragEvent event) {
 
                Dragboard db = event.getDragboard();
                 
                if(db.hasImage()){
                    event.acceptTransferModes(TransferMode.MOVE);
                }
                 
                event.consume();
            }
        });
        
        targetBox.setOnDragDropped(new EventHandler <DragEvent>() {
            @Override
            public void handle(DragEvent event) {
  
                Dragboard db = event.getDragboard();
 
                if(db.hasImage()){
 
                    insertImage(db.getImage(), targetBox);
                     
                    event.setDropCompleted(true);
                }else{
                    event.setDropCompleted(false);
                }
 
                event.consume();
            }
        });
         
    }
     
      void setupGestureSource(final ImageView source){
         
        source.setOnDragDetected(new EventHandler <MouseEvent>() {
           @Override
           public void handle(MouseEvent event) {
                
               /* allow any transfer mode */
               Dragboard db = source.startDragAndDrop(TransferMode.COPY);
                
               /* put a image on dragboard */
               ClipboardContent content = new ClipboardContent();
                
               Image sourceImage = source.getImage();
               content.putImage(sourceImage);
               db.setContent(content);
                
               event.consume();
           }
       });
 
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
    
}
