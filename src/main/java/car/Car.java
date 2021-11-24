package car;

import lombok.Getter;
import lombok.Setter;
import position.Coordinates;
import position.Point;

public class Car {

    @Setter @Getter public Coordinates coordinates;

    @Setter @Getter public Position moveFrom;
    @Setter @Getter public Move moveTo;
    @Setter @Getter public int index = 0;

    @Setter @Getter public Boolean isActive = true;

    //One step to cross the road
    @Setter @Getter public Boolean isAfterLight = false;

    @Getter @Setter public Boolean isAfterCenter = false;

    @Setter @Getter public Boolean isMain = false;

    public Car(Coordinates coordinates, Position moveFrom, Move moveTo, int index, Boolean isMain){
        this.coordinates = coordinates;
        this.isMain = isMain;
        this.moveTo = moveTo;
        this.moveFrom = moveFrom;
        this.index = index;
    }

    public Car(Coordinates coordinates, Position moveFrom, Move moveTo, int index){
        this.coordinates = coordinates;
        this.moveTo = moveTo;
        this.moveFrom = moveFrom;
        this.index = index;
    }

    public boolean isMainCar() {
        return isMain;
    }

    public void moveForwardAfterCrossRoad() {
        moveForward(coordinates);
    }

    public void moveForward(Coordinates coordinates) {
        switch (moveFrom){
            case LEFT:
                if(coordinates.isCenter()){
                    moveToCoordinates(coordinates);
                } else {
                    coordinates.plus(new Point(1,0));
                }
                break;
            case UP:
                if(coordinates.isCenter()){
                    moveToCoordinates(coordinates);
                } else {
                    coordinates.plus(new Point(0,1));
                }
                break;
            case DOWN:
                if(coordinates.isCenter()){
                    moveToCoordinates(coordinates);
                } else {
                    this.coordinates.plus(new Point(0,-1));
                }
                break;
            case RIGHT:
                if(coordinates.isCenter()){
                    moveToCoordinates(coordinates);
                } else {
                    coordinates.plus(new Point(-1,0));
                }
                break;
        }
        checkActiveState();
        checkAfterLightState();
        checkAfterCenterState();
    }

    private void moveToCoordinates(Coordinates coordinates) {
        switch (moveTo){
            case LEFT:
                coordinates.plus(new Point(1,0));
                break;
            case UP:
                coordinates.plus(new Point(0,1));
                break;
            case DOWN:
                coordinates.plus(new Point(0,-1));
                break;
            case RIGHT:
                coordinates.plus(new Point(-1,0));
                break;
        }
    }

    private void checkActiveState() {
        if (crosseRoad()) {
            isActive = false;
        }
    }

    private void checkAfterLightState(){
        if (crosseLight()) {
            isAfterLight = true;
        }
    }

    public void checkAfterCenterState(){
        if(crosseCenter()){
            isAfterCenter = true;
        }
    }

    public Boolean crosseCenter() {
        boolean isCrossRoad = false;
        switch (moveFrom){
            case UP:
                isCrossRoad = coordinates.getPoint().getY() >= 1;
                break;
            case DOWN:
                isCrossRoad = coordinates.getPoint().getY() <= -1;
                break;
            case RIGHT:
                isCrossRoad = coordinates.getPoint().getX() >= 0;
            case LEFT:
                isCrossRoad = coordinates.getPoint().getX() <= 0;
                break;
        }
        return isCrossRoad;
    }

    public Boolean crosseLight() {
        boolean isCrossRoad = false;
        switch (moveFrom){
            case UP:
                isCrossRoad = coordinates.getPoint().getY() >= -1;
                break;
            case DOWN:
                isCrossRoad = coordinates.getPoint().getY() <= 1;
                break;
            case RIGHT:
                isCrossRoad = coordinates.getPoint().getX() >= 0;
            case LEFT:
                isCrossRoad = coordinates.getPoint().getX() >= -1;
                break;
        }
        return isCrossRoad;
    }

    public Boolean crosseRoad() {
        boolean isCrossRoad = false;
        switch (moveTo){
            case UP:
                isCrossRoad = coordinates.getPoint().getY() >= 2;
                break;
            case DOWN:
                isCrossRoad = coordinates.getPoint().getY() <= -2;
                break;
            case RIGHT:
                isCrossRoad = coordinates.getPoint().getX() >= 2;
            case LEFT:
                isCrossRoad = coordinates.getPoint().getX() <= -2;
                break;
        }
        return isCrossRoad;
    }

    public Coordinates nextStep() {
        Coordinates coordinates = new Coordinates(new Point(0,0));
        coordinates.setPoint(this.coordinates.getPoint());
        moveForward(coordinates);
        return coordinates;
    }
}
