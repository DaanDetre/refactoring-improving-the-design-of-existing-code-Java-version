package Models;

public class Performance {
    String playID;
    int audience;

    public Performance(String playID, int audience){
        this.playID = playID;
        this.audience = audience;
    }

    public Performance(){

    }

    public String getPlayID() {
        return playID;
    }

    public void setPlayID(String playID) {
        this.playID = playID;
    }

    public int getAudience() {
        return audience;
    }

    public void setAudience(int audience) {
        this.audience = audience;
    }
}
