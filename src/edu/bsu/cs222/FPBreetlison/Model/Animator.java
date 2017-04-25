package edu.bsu.cs222.FPBreetlison.Model;

import edu.bsu.cs222.FPBreetlison.Controller.BattleView;
import edu.bsu.cs222.FPBreetlison.Model.Objects.Snapshot;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.security.Key;


public class Animator implements java.io.Serializable {

    BattleView battleView;
    HBox heroGraphicsArea;
    HBox enemySelectorArea;
    Group damageDisplayArea;

    ImageView user;
    ImageView target;
    ImageView backButton;
    ImageView airPuff;
    Label damage;

    GameData gameData;
    GameManager game;

    public Animator(GameManager game){
        this.game = game;
        if(game.isBattleUnderway()){
            initAnimatorForBattle();
        }
        initImages();
    }

    private void initAnimatorForBattle() {
        battleView = this.game.getBattleControl();
        damage = battleView.damageDisplay;
        heroGraphicsArea = battleView.heroGraphicsArea;
        enemySelectorArea = battleView.enemySelectorArea;
        backButton = battleView.backButton;
        damageDisplayArea = battleView.damageDisplayArea;
        gameData = battleView.getGameData();
    }

    private void initImages() {
        //airPuff = new ImageView(new Image("/images/battle/effects_puff.png"));
    }

    public void playAnimation(String animationType){
        if(animationType.equals("heroLunge")){heroLunge();}
        else if(animationType.equals("enemyLunge")){enemyLunge();}
        else if(animationType.equals("heroQuickStretch")){heroQuickStretch();}
    }

    private void setUpHeroOrientation(){
        user = (ImageView)heroGraphicsArea.getChildren().get(battleView.selectedUser);
        target = (ImageView)enemySelectorArea.getChildren().get(battleView.selectedTarget);
    }
    private void setUpEnemyOrientation(){
        user = (ImageView)enemySelectorArea.getChildren().get(battleView.selectedTarget);
        target = (ImageView)heroGraphicsArea.getChildren().get(battleView.selectedUser);
    }


    public void heroLunge(){
        setUpHeroOrientation();
        Timeline timeline = new Timeline();
        KeyValue userLunge = new KeyValue(user.translateXProperty(),user.getTranslateX()+50, Interpolator.EASE_BOTH);
        KeyValue targetKnockback = new KeyValue(target.translateXProperty(),target.getTranslateX()+20,Interpolator.EASE_BOTH);
        KeyValue userRetreat = new KeyValue(user.translateXProperty(),user.getTranslateX(),Interpolator.EASE_BOTH);
        KeyValue targetRetreat = new KeyValue(target.translateXProperty(),target.getTranslateX(),Interpolator.EASE_BOTH);
        KeyFrame keyFrame = new KeyFrame(Duration.millis(100),userLunge);
        KeyFrame keyFrame2 = new KeyFrame(Duration.millis(200),userRetreat,targetKnockback);
        KeyFrame keyFrame3 = new KeyFrame(Duration.millis(350),targetRetreat);

        timeline.getKeyFrames().addAll(keyFrame,keyFrame2,keyFrame3);
        timeline.play();
    }

    public void enemyLunge(){
        setUpEnemyOrientation();
        Timeline timeline = new Timeline();
        KeyValue userLunge = new KeyValue(user.translateXProperty(),user.getTranslateX()-50, Interpolator.EASE_BOTH);
        KeyValue targetKnockback = new KeyValue(target.translateXProperty(),target.getTranslateX()-20,Interpolator.EASE_BOTH);
        KeyValue userRetreat = new KeyValue(user.translateXProperty(),user.getTranslateX(),Interpolator.EASE_BOTH);
        KeyValue targetRetreat = new KeyValue(target.translateXProperty(),target.getTranslateX(),Interpolator.EASE_BOTH);
        KeyFrame keyFrame = new KeyFrame(Duration.millis(100),userLunge);
        KeyFrame keyFrame2 = new KeyFrame(Duration.millis(200),userRetreat,targetKnockback);
        KeyFrame keyFrame3 = new KeyFrame(Duration.millis(350),targetRetreat);

        timeline.getKeyFrames().addAll(keyFrame,keyFrame2,keyFrame3);
        timeline.play();
    }

    public void backButtonSlideOut(){
        Timeline timeline = new Timeline();
        KeyValue slideOut = new KeyValue(backButton.translateXProperty(),120,Interpolator.EASE_BOTH);
        KeyFrame keyFrame = new KeyFrame(Duration.millis(240),slideOut);

        timeline.getKeyFrames().add(keyFrame);
        timeline.play();

    }

    public void backButtonSlideIn(){
        Timeline timeline = new Timeline();
        KeyValue slideIn = new KeyValue(backButton.translateXProperty(),-70,Interpolator.EASE_BOTH);
        KeyFrame keyFrame = new KeyFrame(Duration.millis(240),slideIn);

        timeline.getKeyFrames().add(keyFrame);
        timeline.play();
    }

    public void heroQuickStretch(){
        setUpHeroOrientation();

        Timeline timeline = new Timeline();
        KeyValue userShrink = new KeyValue(user.scaleYProperty(),.75, Interpolator.EASE_BOTH);
        KeyValue userMoveDown = new KeyValue(user.translateYProperty(),40,Interpolator.EASE_BOTH);
        KeyValue userStill = new KeyValue(user.scaleYProperty(),.75,Interpolator.EASE_BOTH);
        KeyValue userPuffBack = new KeyValue(user.scaleYProperty(),1,Interpolator.EASE_BOTH);
        KeyValue userMoveBack = new KeyValue(user.translateYProperty(),0,Interpolator.EASE_BOTH);

        KeyFrame keyFrame = new KeyFrame(Duration.millis(100),userShrink,userMoveDown);
        KeyFrame keyFrame2 = new KeyFrame(Duration.millis(200),userStill,userMoveDown);
        KeyFrame keyFrame3 = new KeyFrame(Duration.millis(400),userStill,userMoveDown);
        KeyFrame keyFrame4 = new KeyFrame(Duration.millis(700),userPuffBack,userMoveBack);

        timeline.getKeyFrames().addAll(keyFrame,keyFrame2,keyFrame3,keyFrame4);
        timeline.play();
    }

    public void airPuff(){
        Timeline timeline = new Timeline();
        KeyValue puffFade = new KeyValue(airPuff.opacityProperty(),.01,Interpolator.EASE_BOTH);
        KeyValue puffMove = new KeyValue(airPuff.translateXProperty(),20,Interpolator.EASE_BOTH);

        KeyFrame keyFrame = new KeyFrame(Duration.millis(100),puffFade,puffMove);

        timeline.getKeyFrames().addAll(keyFrame);
        timeline.play();
    }

    public void animateDamageToEnemy(Snapshot info){
        Label damage = new Label(""+info.getDamage());
        ImageView target = (ImageView)enemySelectorArea.getChildren().get(info.getIndex());

        formatLabel(damage, target);

        Timeline timeline = new Timeline();
        KeyValue damageFade = new KeyValue(damage.opacityProperty(),.01,Interpolator.EASE_BOTH);
        KeyValue damageMove = new KeyValue(damage.translateYProperty(),-50,Interpolator.EASE_BOTH);

        KeyFrame keyFrame = new KeyFrame(Duration.millis(800),damageFade,damageMove);

        timeline.getKeyFrames().addAll(keyFrame);
        timeline.play();
    }

    private void formatLabel(Label damage, ImageView target) {

        damageDisplayArea.getChildren().add(damage);
        damage.getStyleClass().add("damage-text");
        damage.setVisible(true);

        System.out.println("Attacking " + target.getId());

        Bounds boundsInScene = target.localToScene(target.getLayoutBounds());
        double locTemp = boundsInScene.getMaxX() - boundsInScene.getMinX();
        double locx = game.getStage().getWidth()- boundsInScene.getWidth();
        damage.setLayoutX(locx);
        damage.setLayoutY(boundsInScene.getMinY());
    }

    public void heroFlee(){
        Timeline timeline = new Timeline();
        for(int i = 0; i<heroGraphicsArea.getChildren().size();i++){
            ImageView hero = (ImageView)heroGraphicsArea.getChildren().get(i);
            KeyValue spinV = new KeyValue(hero.scaleXProperty(),-1,Interpolator.EASE_BOTH);
            KeyValue moveBackV = new KeyValue(hero.translateXProperty(),hero.getTranslateX()+40,Interpolator.EASE_BOTH);
            KeyValue stillV = new KeyValue(hero.scaleXProperty(),-1,Interpolator.EASE_BOTH);
            KeyValue tiltV = new KeyValue(hero.rotateProperty(),hero.getRotate()+15,Interpolator.EASE_BOTH);
            KeyValue moveFV = new KeyValue(hero.translateXProperty(),hero.getTranslateX()-9000,Interpolator.EASE_BOTH);
            KeyFrame spinK = new KeyFrame(Duration.millis(200),spinV,moveBackV);
            KeyFrame stillK = new KeyFrame(Duration.millis(400),stillV);
            KeyFrame tiltK = new KeyFrame(Duration.millis(700),tiltV);
            KeyFrame moveFK = new KeyFrame(Duration.millis(800),moveFV);
            timeline.getKeyFrames().addAll(spinK,stillK,tiltK,moveFK);
        }
        timeline.play();


    }

    public void showBanner(StackPane navBanner) {
        Timeline timeline = new Timeline();

        KeyValue showBannerV = new KeyValue(navBanner.translateXProperty(),500,Interpolator.EASE_BOTH);
        KeyValue waitV = new KeyValue(navBanner.translateXProperty(),500,Interpolator.EASE_BOTH);
        KeyValue retractV = new KeyValue(navBanner.translateXProperty(),900,Interpolator.EASE_BOTH);

        KeyFrame showBannerK = new KeyFrame(Duration.millis(800),showBannerV);
        KeyFrame waitK = new KeyFrame(Duration.millis(3500),waitV);
        KeyFrame retractK = new KeyFrame(Duration.millis(4500),retractV);

        timeline.getKeyFrames().addAll(showBannerK,waitK,retractK);
        timeline.play();

    }
}
