package Models;

public class Play {
    String name;
    String type;

    public Play(String name, String type){
        this.name = name;
        this.type = type;
    }

    public Play(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
