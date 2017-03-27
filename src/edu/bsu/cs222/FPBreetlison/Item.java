package edu.bsu.cs222.FPBreetlison;

import java.util.Arrays;
import java.util.List;

public class Item {

    private String name;
    private String description;
    private String type;
    private int amount;
    private String buffType;

    public Item(String info){

        List<String> itemInfo = stringParser(info);
        this.name = itemInfo.get(0);
        this.description = itemInfo.get(1);
        this.amount = Integer.parseInt(itemInfo.get(2));
        this.type = itemInfo.get(3);
        if(type.equals("buff")){
            this.buffType = itemInfo.get(4);
        }
    }

    public void activate(Fighter user){
        if(type.equals("heal")){
            triggerHeal(user);
        }
        else if(type.equals("buff")){
            triggerBuff(user);
        }
    }
    private void triggerHeal(Fighter user){
        user.recoverHealth(amount);
    }
    private void triggerBuff(Fighter user){
        user.buffer(buffType,amount);
    }

    private List<String> stringParser(String info){

        List<String> attributes = Arrays.asList(info.split(","));
        return attributes;
    }


    public String getName() {
        return name;
    }
    public String getType() {
        return type;
    }
}
