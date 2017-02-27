package edu.bsu.cs222.FPBreetlison;

public class Skill {

    String SkillType;
    int BasePower;

    public Skill(String skillType){
        SkillType = skillType;
    }
    public void activate(Battler target){
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

    private void triggerStatus(Battler target) {

    }

    private void triggerAttack(Battler target) {
    }

    private void triggerHeal(Battler target) {
    }

    private void triggerBuff(Battler target) {
    }
}
