package edu.bsu.cs222.FPBreetlison.Model;

import edu.bsu.cs222.FPBreetlison.Controller.BattleView;
import edu.bsu.cs222.FPBreetlison.Model.Objects.Fighter;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.util.Duration;


public class Animator {

    BattleView battleView;
    HBox heroGraphicsArea;
    HBox enemySelectorArea;

    public Animator(BattleView battleView){
        this.battleView = battleView;
        heroGraphicsArea = battleView.heroGraphicsArea;
        enemySelectorArea = battleView.enemySelectorArea;
    }

    public void playAnimation(int userIndex, int targetIndex){
        //This is incredibly frail. Needs simple refactoring
        String animationType = "lunge";
        if(animationType.equals("lunge")){
            lunge(heroGraphicsArea.getChildren().get(userIndex),enemySelectorArea.getChildren().get(targetIndex));
        }
    }


    public void lunge(Node user,Node target){
        ImageView userImage = (ImageView)user;
        ImageView targetImage = (ImageView)target;
        Timeline timeline = new Timeline();
        KeyValue userLunge = new KeyValue(userImage.translateXProperty(),50, Interpolator.EASE_BOTH);
        KeyValue targetKnockback = new KeyValue(targetImage.translateXProperty(),50,Interpolator.EASE_BOTH);
        KeyValue userRetreat = new KeyValue(userImage.translateXProperty(),0,Interpolator.EASE_BOTH);
        KeyValue targetRetreat = new KeyValue(targetImage.translateXProperty(),0,Interpolator.EASE_BOTH);
        KeyFrame keyFrame = new KeyFrame(Duration.millis(100),userLunge);
        KeyFrame keyFrame2 = new KeyFrame(Duration.millis(200),userRetreat,targetKnockback);
        KeyFrame keyFrame3 = new KeyFrame(Duration.millis(300),targetRetreat);

        timeline.getKeyFrames().addAll(keyFrame,keyFrame2,keyFrame3);
        timeline.play();

    }

    public void knockBack1(Node node){
        ImageView object = (ImageView)node;
        Timeline timeline = new Timeline();
        KeyValue keyValue = new KeyValue(object.translateXProperty(),50, Interpolator.EASE_BOTH);
        KeyValue keyValue2 = new KeyValue(object.translateXProperty(),0,Interpolator.EASE_BOTH);
        KeyFrame keyFrame = new KeyFrame(Duration.millis(100),keyValue);
        KeyFrame keyFrame2 = new KeyFrame(Duration.millis(200),keyValue2);
        timeline.getKeyFrames().addAll(keyFrame,keyFrame2);
        timeline.play();
    }

}
