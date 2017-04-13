package edu.bsu.cs222.FPBreetlison.Controller;


import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.File;

/**
 *
 *
 * Having issues with displaying second window for the dragging/dropping but the logic should be "OK"
 *
 *
 *
 */




public class DragAndDrop extends Application {

    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage){
        primaryStage.setTitle("Test Drag/Drop Window");
        Group root = new Group();
        Scene scene = new Scene(root, 500, 500);

        HBox hBox = new HBox();

        setGestureTarget(scene, hBox);

        root.getChildren().add(hBox);
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.show();
    }


    void setGestureTarget(final Scene target, final HBox targetBox){

        target.setOnDragOver(new EventHandler<DragEvent>(){
            @Override
            public void handle(DragEvent event){


                Dragboard dragboard = event.getDragboard();
                if (dragboard.hasFiles()){
                    event.acceptTransferModes(TransferMode.COPY);
                }

                event.consume();
            }
        });

        target.setOnDragDropped(new EventHandler<DragEvent>(){
            @Override
            public void handle(DragEvent event){

                Dragboard dragboard = event.getDragboard();

                if (dragboard.hasFiles()){

                    for (File file : dragboard.getFiles()){
                        String absolutePath = file.getAbsolutePath();
                        Image dragboardImage = new Image(absolutePath);
                        ImageView dragboardImageView = new ImageView();
                        dragboardImageView.setImage(dragboardImage);
                        targetBox.getChildren().add(dragboardImageView);
                    }

                    event.setDropCompleted(true);
                }
                else{
                    event.setDropCompleted(false);
                }

                event.consume();
            }
        });

    }

}