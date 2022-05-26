package hu.aranyosipeter.kockapp.model.move;

public class Separator extends Move{
    private final int sectionID;

    public Separator(int sectionID) {
        this.sectionID = sectionID;
    }

    public int getSectionID() {
        return sectionID;
    }
}
