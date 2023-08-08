package shared;

public class BalloonCounter {
    private final String color;
    private final int count;

    public BalloonCounter(String color, int count) {
        this.color = color;
        this.count = count;
    }

    public String getColor() {
        return color;
    }


    public int getCount() {
        return count;
    }

    @Override
    public String toString() {
        return "Balloon{" +
                "color='" + color + '\'' +
                ", count=" + count +
                '}';
    }

}

