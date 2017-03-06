package edu.bsu.cs222.FPBreetlison;

public class ElementType {

    //We need a way to construct it with information pointing to weaknesses
    //and strengths


    public ElementType(String name){

    }

    public String[] weaknesses(){
        //This could return an array of strings of the elements this element is \
        //weak against
        String[] weaknesses = {"Pyro", "Metal"};
        return weaknesses;
    }


}
