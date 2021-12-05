package road;

import car.Car;
import car.Position;
import config.RoadMapConfig;
import lombok.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import position.Coordinates;

public class RoadController {

    public @NonNull RoadMapConfig config;

    private static final int movementUp = -1;
    private static final int movementDown = 1;

    public RoadController(@NonNull RoadMapConfig config) {
        this.config = config;
    }

    public boolean needPassCars(Position position, @Nullable Car car){
        if(car != null){
            boolean needPass = false;
            switch (position){
                case UP:
                    needPass = predictPositionsOfLeftCars(car, movementUp) || predictPositionsOfRightCars(car, movementUp);
                    break;
                case DOWN:
                    needPass = predictPositionsOfRightCars(car, movementDown) || predictPositionsOfLeftCars(car, movementDown);
                    break;
                case RIGHT:
                    needPass = predictPositionsOfDownCars(car);
                    break;
                case LEFT:
                    needPass = predictPositionsOfUpCars(car);
                    break;
            }
            return needPass;
        }
        return false;
    }

    public boolean needPassCarsFromDiffRoadsAfterLight(Car car) {
        boolean needPassCars = false;
        for(Car _car : config.getAllCars()){
            if(
                    isCoordinatesEqual(_car.getCoordinates(), car.nextStep()) && _car.getIndex() != car.getIndex() ||
                            isCoordinatesEqual(_car.nextStep(), car.nextStep()) && _car.getIndex() != car.getIndex()
            ){
                if(car.index == 12){
                    System.out.println(false);
                }
                // On the same road
                needPassCars = true;
//                if(_car.moveFrom == car.moveFrom){
//                    if(!needPassCarsFromDiffRoadsAfterLight(_car)){
//                        needPassCars = false;
//                    }
//                }
                break;
            }
        }
        return needPassCars;
    }

    public boolean needPassCarsFromDiffRoads(Car car) {
        boolean needPassCars = false;
        for(Car _car : config.getAllCars()){
            if(isCoordinatesEqual(_car.getCoordinates(), car.nextStep()) && _car.getIndex() != car.getIndex()){

                needPassCars = true;
                break;
            }
        }
        return needPassCars;
    }

    private boolean checkPositions(Car _car, Car car) {
        Position position = Position.LEFT;
        switch (car.moveFrom){
            case DOWN: position = Position.LEFT;
            case RIGHT: position = Position.DOWN;
            case UP: position = Position.RIGHT;
            case LEFT: position = Position.UP;
        }
        return _car.moveFrom == position;
    }

    private Boolean getGiveUpRoad(Car car, Position moveFrom) {
        Position position = Position.LEFT;
        switch (moveFrom){
            case DOWN: position = Position.LEFT;
            case RIGHT: position = Position.UP;
            case UP: position = Position.RIGHT;
            case LEFT: position = Position.DOWN;
        }
        return car.moveFrom == position;
    }

    private boolean isCoordinatesEqual(Coordinates toCar, Coordinates fromCar){
        return toCar.getPoint().getX().equals(fromCar.getPoint().getX()) && toCar.getPoint().getY().equals(fromCar.getPoint().getY());
    }

    public boolean needPassCar(Car car) {
        boolean needPassCars = false;
        for(Car _car : config.getAllCars()){
            if(
                    _car.getCoordinates() == car.nextStep() && _car.getIndex() != car.getIndex() ||
                    _car.nextStep() == car.nextStep() && _car.getIndex() != car.getIndex()
            ){
                needPassCars = true;
                break;
            }
        }
        return needPassCars;
    }

    public boolean isMoving(Car car) {
        return needPassCars(car.moveFrom, car);
    }

    private boolean predictPositionsOfUpCars(Car car) {

        return false;
    }

    private boolean predictPositionsOfDownCars(Car car) {

        return false;
    }

    private boolean predictPositionsOfRightCars(Car car, int movement) {
        boolean needPass = false;
        if(car.index == 5) {
            System.out.println("right car 5: ");
        }
        for(int i = 0; i < config.carsRight.size(); i++){
            if(
                    config.carsRight.get(i).getCoordinates().getPoint().getX() + movement == car.getCoordinates().getPoint().getX() &&
                    (car.getCoordinates().getPoint().getY() - movement) == (config.carsRight.get(i).getCoordinates().getPoint().getY())
            ){
                needPass = true;
                break;
            }
            System.out.println();
        }
        return needPass;
    }

    public boolean isCrash(Car car) {
        boolean isCrash = false;
        for(Car _car : config.getAllCars()){
            if(_car.getCoordinates() == car.getCoordinates() && _car.getIndex() != car.getIndex()){
                isCrash = true;
                break;
            }
        }
        return isCrash;
    }

    private boolean predictPositionsOfLeftCars(Car car, int movement) {
        boolean needPass = false;
        if(car.index == 9){
            System.out.println("f");
        }
        for(int i = 0; i < config.carsLeft.size(); i++){
            if(
                    (car.getCoordinates().getPoint().getY() - movement) == config.carsLeft.get(i).getCoordinates().getPoint().getY() &&
                    (car.getCoordinates().getPoint().getX().equals((config.carsLeft.get(i).getCoordinates().getPoint().getX() + movement)))
            ){
                needPass = true;
                break;
            }
            System.out.println();
        }
        return needPass;
    }

    private void predictPositions() {

    }

}
