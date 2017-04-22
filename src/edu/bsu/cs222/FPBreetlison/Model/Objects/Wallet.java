package edu.bsu.cs222.FPBreetlison.Model.Objects;

public class Wallet implements java.io.Serializable {

    private double amount;
    private double displayAmount;
    private String type;

    public Wallet(){
        amount = 300;
        type = "KB";
    }

    public void spend(int amount, String type){
        if(type.equals("MB")){
            this.amount-=amount*1000;
        }
        else if(type.equals("GB")){
            this.amount-=amount*1000000;
        }
        else{
            this.amount-=amount;
        }
    }
    public void collect(int amount, String type){
        if(type.equals("MB")){
            this.amount+=amount*1000;
        }
        else if(type.equals("GB")){
            this.amount+=amount*1000000;
        }
        else{
            this.amount+=amount;
        }

    }

    public String getDisplayAmount(){
        convertForDisplay();
        round();
        return displayAmount + type;
    }

    private void convertForDisplay(){
        displayAmount = amount;
        type = "KB";
        if(type.equals("KB") && displayAmount>=1000){
            type = "MB";
            displayAmount = displayAmount/1000;
        }
        if(type.equals("MB")&& displayAmount>=1000){
            type = "GB";
            displayAmount = displayAmount/1000;
        }
    }

    private void round() {
        if(displayAmount % 1 != 0){
            displayAmount = Math.round(displayAmount*100);
            displayAmount = displayAmount/100;
        }
    }

}
