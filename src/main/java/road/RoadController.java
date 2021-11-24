package road;

import car.Car;
import car.Position;
import config.RoadMapConfig;
import lombok.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

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

    public boolean needPassCar(Car car) {
        boolean needPassCars = false;
        for(Car _car : config.getAllCars()){
            if(_car.getCoordinates() == car.nextStep()){
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
