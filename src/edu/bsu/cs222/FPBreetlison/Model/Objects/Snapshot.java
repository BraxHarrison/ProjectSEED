package edu.bsu.cs222.FPBreetlison.Model.Objects;

public class Snapshot {

    //This class is responsible for recalling information about a character at a given time
    //used in conjunction with timelines to allow for incremental updating of the UI

    private int index;
    private double hpPercent;
    private boolean KOState;
    private String hpString;

    public void calcHPPercent(Fighter fighter){
        if(fighter == null){
            hpPercent=0;
            hpString = "HP: " + 0 + "/" + 0;
        }
        else{
            hpPercent = (double)fighter.getHp()/(double)fighter.getMaxHP();
            hpString = "HP: " + fighter.getHp() + "/" + fighter.getMaxHP();
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

    public String getHpString(){
        return hpString;
    }

    public boolean getKOState() {
        return KOState;
    }

    public void setKOState(boolean KOState) {
        this.KOState = KOState;
    }


}
