package hacks.epstein.peanut.modules.settings;

public class Setting {
    private boolean enabled = false;
    private String name;

    public void setName(String name) {this.name = name;}

    public void toggle(){
        this.enabled=!enabled;
    }

    public void setStatus(boolean status){
        this.enabled = status;
    }

    public boolean isEnabled(){
        return this.enabled;
    }

    public String getName(){
        return this.name;
    }
}
