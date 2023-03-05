package edu.northeastern.team43;

public class StickerDetail {
    private String stickerId;
    private int count;
    public StickerDetail(String stickerId,int count){
        this.stickerId=stickerId;
        this.count=count;
    }

    public String getStickerId() {
        return stickerId;
    }

    public int getCount() {
        return count;
    }
}
