package edu.bsu.cs222.FPBreetlison.Model;

public class Skill {

    String SkillType;
    int BasePower;

    public Skill(String skillType){
        SkillType = skillType;
    }
    public void activate(Fighter target){
        if(SkillType.equals("buff")){
            triggerBuff(target);
        }
        else if(SkillType.equals("heal")){
            triggerHeal(target);
        }
        else if(SkillType.equals("attack")){
            triggerAttack(target);
        }
        else if(SkillType.equals("status")){
            triggerStatus(target);
        }
    }

    private void triggerStatus(Fighter target) {

    }

    private void triggerAttack(Fighter target) {
    }

    private void triggerHeal(Fighter target) {
    }

    private void triggerBuff(Fighter target) {
    }
}
