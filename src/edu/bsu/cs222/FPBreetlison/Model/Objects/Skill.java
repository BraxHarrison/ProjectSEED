package edu.bsu.cs222.FPBreetlison.Model.Objects;

import java.util.Arrays;
import java.util.List;

public class Skill {
    String name;
    int affectAmt;
    String type;
    String type2;


    public Skill(String info){
        System.out.println(info);
        List<String> skillInfo = stringParser(info);
        System.out.println(skillInfo.get(1));
        this.name = skillInfo.get(0);
        this.affectAmt = Integer.parseInt(skillInfo.get(1));
        this.type = skillInfo.get(2);
        this.type2 = skillInfo.get(3);
    }

    public void use(Fighter user, Fighter target){
        if(type.equals("heal")){
            user.recoverHealth(affectAmt);
        }
        else if(type.equals("attack")){

        }
        else if(type.equals("debuff")){
            target.weakenStat("attack",affectAmt);
        }
        else if(type.equals("buff")){
            user.strengthenStat("attack",affectAmt);
        }
        else if(type.equals("selfDebuff")){

        }
    }

    private List<String> stringParser(String info){
        return Arrays.asList(info.split(","));
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
