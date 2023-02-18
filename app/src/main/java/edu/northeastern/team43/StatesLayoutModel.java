package edu.northeastern.team43;

public class StatesLayoutModel {

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    private String stateName;

    public StatesLayoutModel(){}

    public StatesLayoutModel(String stateName){
        this.stateName=stateName;
    }


}