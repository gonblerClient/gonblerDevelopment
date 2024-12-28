package hacks.epstein.peanut.modules;

public abstract class Hack {
    protected boolean enabled = false;

    public void toggle() {
        this.enabled = !this.enabled;
    }
    public boolean isEnabled() {
        return this.enabled;
    }
    public void tick(){

    }
}