package edu.bsu.cs222.FPBreetlison;

public class DamageState {

    private int index;
    private double hpPercent;
    private boolean KOState;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public double getHpPercent() {
        return hpPercent;
    }

    public void setHpPercent(double hpPercent) {
        this.hpPercent = hpPercent;
    }

    public boolean getKOState() {
        return KOState;
    }

    public void setKOState(boolean KOState) {
        this.KOState = KOState;
    }


}
