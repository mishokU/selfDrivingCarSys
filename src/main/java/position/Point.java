package position;

import lombok.Getter;

public class Point {

    @Getter Integer x = 0;
    @Getter Integer y = 0;

    public Point(Integer x, Integer y) {
        this.x = x;
        this.y = y;
    }

}
