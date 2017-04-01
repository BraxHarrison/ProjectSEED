package edu.bsu.cs222.FPBreetlison.Model.Objects;

public class Snapshot {

    //This class is responsible for recalling information about a character at a given time
    //used in conjunction with timelines to allow for incremental updating of the UI

    private int index;
    private double hpPercent;
    private boolean KOState;

    public void calcHPPercent(Fighter fighter){
        if(fighter == null){
            hpPercent=0;
        }
        else{
            hpPercent = (double)fighter.getHp()/(double)fighter.getMaxHP();
        }

    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public double getHpPercent() {
        return hpPercent;
    }

    public boolean getKOState() {
        return KOState;
    }

    public void setKOState(boolean KOState) {
        this.KOState = KOState;
    }


}
