package position;


import lombok.Getter;
import lombok.Setter;
import org.checkerframework.checker.nullness.qual.NonNull;

public class Coordinates {

    private @Getter @Setter Point point;

    public Coordinates(@NonNull Point point){
        this.point = point;
    }

    @Override
    public String toString() {
        return "" + point.getX() + point.getY();
    }

    public void plus(Point point){
        this.point.x += point.x;
        this.point.y += point.y;
    }

    public boolean isCenter() {
        return getPoint().getY() == 0 || getPoint().getX() == 0;
    }
}
