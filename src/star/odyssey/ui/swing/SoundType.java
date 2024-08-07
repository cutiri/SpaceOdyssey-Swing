package star.odyssey.ui.swing;

public enum SoundType {
    SFX("SFX"), BACKGROUND("Sound");

    private final String display;

    private SoundType(String display) {
        this.display = display;
    }

    public String display(){
        return display;
    }

    public String toString() {
      return display();
    }
}