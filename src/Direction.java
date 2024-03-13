import java.util.Arrays;

public enum Direction {

    UP("U", -1, 0),
    DOWN("D", 1, 0),
    LEFT("L", 0, -1),
    RIGHT("R",0,1);

    private final String inputTag;
    private final int xRelative;
    private final int yRelative;

    // enum for the Direction, which shows the relative on the grid to that direction e.g. up is -1 in the first []
    Direction(String inputTag, int xRelative, int yRelative) {
        this.inputTag = inputTag;
        this.xRelative = xRelative;
        this.yRelative = yRelative;
    }
    // getters
    public String getInputTag() {
        return inputTag;
    }

    public int getxRelative() {
        return xRelative;
    }

    public int getyRelative() {
        return yRelative;
    }

    public static Direction getDirectionFromTag(String tag) {
        // streams through all the values and returns the first one found matching the "tag" or if none are found returns null
        return Arrays.stream(values()).filter(direction -> direction.getInputTag().equalsIgnoreCase(tag)).findFirst().orElse(null);
    }
}
