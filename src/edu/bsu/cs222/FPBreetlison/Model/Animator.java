package edu.bsu.cs222.FPBreetlison.Model;

import edu.bsu.cs222.FPBreetlison.Controller.BattleView;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.util.Duration;


public class Animator {

    BattleView battleView;
    HBox heroGraphicsArea;
    HBox enemySelectorArea;

    ImageView user;
    ImageView target;

    public Animator(BattleView battleView){
        this.battleView = battleView;
        heroGraphicsArea = battleView.heroGraphicsArea;
        enemySelectorArea = battleView.enemySelectorArea;
    }

    public void playAnimation(String animationType){
        //This is incredibly frail. Needs simple refactoring
        if(animationType.equals("herolunge")){heroLunge();}
        else if(animationType.equals("enemylunge")){enemyLunge();}
    }

    private void setUpHeroOrientation(){
        user = (ImageView)heroGraphicsArea.getChildren().get(battleView.selectedUser);
        target = (ImageView)enemySelectorArea.getChildren().get(battleView.selectedEnemy);
    }
    private void setUpEnemyOrientation(){
        user = (ImageView)enemySelectorArea.getChildren().get(battleView.selectedEnemy);
        target = (ImageView)heroGraphicsArea.getChildren().get(battleView.selectedUser);
    }


    public void heroLunge(){
        setUpHeroOrientation();
        Timeline timeline = new Timeline();
        KeyValue userLunge = new KeyValue(user.translateXProperty(),50, Interpolator.EASE_BOTH);
        KeyValue targetKnockback = new KeyValue(target.translateXProperty(),20,Interpolator.EASE_BOTH);
        KeyValue userRetreat = new KeyValue(user.translateXProperty(),0,Interpolator.EASE_BOTH);
        KeyValue targetRetreat = new KeyValue(target.translateXProperty(),0,Interpolator.EASE_BOTH);
        KeyFrame keyFrame = new KeyFrame(Duration.millis(100),userLunge);
        KeyFrame keyFrame2 = new KeyFrame(Duration.millis(200),userRetreat,targetKnockback);
        KeyFrame keyFrame3 = new KeyFrame(Duration.millis(350),targetRetreat);

        timeline.getKeyFrames().addAll(keyFrame,keyFrame2,keyFrame3);
        timeline.play();

    }

    public void enemyLunge(){
        setUpEnemyOrientation();
        Timeline timeline = new Timeline();
        KeyValue userLunge = new KeyValue(user.translateXProperty(),-50, Interpolator.EASE_BOTH);
        KeyValue targetKnockback = new KeyValue(target.translateXProperty(),-20,Interpolator.EASE_BOTH);
        KeyValue userRetreat = new KeyValue(user.translateXProperty(),0,Interpolator.EASE_BOTH);
        KeyValue targetRetreat = new KeyValue(target.translateXProperty(),0,Interpolator.EASE_BOTH);
        KeyFrame keyFrame = new KeyFrame(Duration.millis(100),userLunge);
        KeyFrame keyFrame2 = new KeyFrame(Duration.millis(200),userRetreat,targetKnockback);
        KeyFrame keyFrame3 = new KeyFrame(Duration.millis(350),targetRetreat);

        timeline.getKeyFrames().addAll(keyFrame,keyFrame2,keyFrame3);
        timeline.play();

    }

}
