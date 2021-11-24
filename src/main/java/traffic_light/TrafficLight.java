package traffic_light;


import car.Position;
import config.RoadMapConfig;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.checkerframework.checker.nullness.qual.Nullable;
import road.Road;
import traffic_light.handlers.*;

public class TrafficLight {

    private RoadMapConfig config;

    private @NonNull TrafficHandlerUp handlerUp;
    private @NonNull TrafficHandlerLeft handlerLeft;
    private @NonNull TrafficHandlerRight handlerRight;
    private @NonNull TrafficHandlerDown handlerDown;

    private @Getter @Setter @NonNull Position position;

    private int redTick = 0;
    private int greenTick = 0;

    @Setter @Getter private Color color = Color.RED;

    private @Nullable Color prevColor;

    public TrafficLight(
            RoadMapConfig config,
            @NonNull Road road,
            @NonNull Position position
    ){
        this.config = config;
        this.position = position;
        this.handlerUp = road;
        this.handlerLeft = road;
        this.handlerDown = road;
        this.handlerRight = road;
    }

    private Color getInitColor(){
        if(config.picked){
            config.picked = false;
            return Color.RED;
        } else {
            config.picked = true;
            return Color.GREEN;
        }
    }

    public void changeLight() {
        switch (color){
            case RED:
                prevColor = color;
                if(redTick == 2){
                    color = Color.YELLOW;
                    handleMoveCars(true);
                    redTick = 0;
                } else {
                    handleMoveCars(false);
                }
                redTick += 1;
                break;
            case GREEN:
                prevColor = color;
                if(greenTick == 2){
                    color = Color.YELLOW;
                    greenTick = 0;
                }
                handleMoveCars(true);
                greenTick += 1;
                break;
            case YELLOW:
                if(prevColor == Color.RED){
                    color = Color.GREEN;
                    handleMoveCars(false);
                }
                if(prevColor == Color.GREEN){
                    color = Color.RED;
                    handleMoveCars(false);
                }
                break;
        }
    }

    private void handleMoveCars(boolean canMove) {
        switch (position){
            case LEFT:
                handlerLeft.canMoveCarsLeft(canMove);
                break;
            case RIGHT:
                handlerRight.canMoveCarsRight(canMove);
                break;
            case UP:
                handlerUp.canMoveCarsUp(canMove);
                break;
            case DOWN:
                handlerDown.canMoveCarsDown(canMove);
                break;
        }
    }

    public void init() {
        this.color = getInitColor();
    }
}
