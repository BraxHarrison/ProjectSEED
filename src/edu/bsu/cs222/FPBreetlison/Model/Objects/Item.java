package edu.bsu.cs222.FPBreetlison.Model.Objects;

import java.util.Arrays;
import java.util.List;

public class Item implements java.io.Serializable {

    private String name;
    private String description;
    private String quickSummary;
    private int affectAmt;
    private String type;
    private String type2;
    private String imagePath;

    public Item(String info){

        List<String> itemInfo = stringParser(info);
        this.name = itemInfo.get(0);
        this.description = itemInfo.get(1);
        this.quickSummary = itemInfo.get(2);
        this.affectAmt = Integer.parseInt(itemInfo.get(3));
        this.type = itemInfo.get(4);
        this.type2 = itemInfo.get(5);
        this.imagePath = itemInfo.get(6);
    }


    public void activate(Fighter user){
        if(type.equals("heal")){
            triggerHeal(user);
        }
        else if(type.equals("buff")){
            triggerBuff(user);
        }
        else if(type.equals("debuff")){

        }
        else if(type.equals("selfDebuff")){

        }
    }
    private void triggerHeal(Fighter user){
        user.recoverHealth(affectAmt);
    }
    private void triggerBuff(Fighter user){
        user.strengthenStat(type2, affectAmt);
    }
    private void triggerDebuff(Fighter target){
        target.weakenStat(type2,affectAmt);
    }


    private List<String> stringParser(String info){

        return Arrays.asList(info.split(","));
    }

    public String getName() {
        return name;
    }
    public String getImagePath() {
        if(imagePath.equals("null")){
            return "/images/system/system_undefined.png";
        }
        return imagePath;
    }
    public String getDescription() {
        return description;
    }
    public String getQuickSummary() {
        return quickSummary;
    }
}
