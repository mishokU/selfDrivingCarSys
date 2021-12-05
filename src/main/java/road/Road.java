package road;

import car.Car;
import car.Move;
import car.Position;
import config.RoadMapConfig;
import lombok.NonNull;
import lombok.SneakyThrows;
import position.Coordinates;
import position.Point;
import road.handlers.TrafficCrashHandler;
import road.handlers.TrafficViolationHandler;
import traffic_light.handlers.TrafficHandlerDown;
import traffic_light.handlers.TrafficHandlerLeft;
import traffic_light.handlers.TrafficHandlerRight;
import traffic_light.handlers.TrafficHandlerUp;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Road implements TrafficHandlerUp, TrafficHandlerRight, TrafficHandlerDown, TrafficHandlerLeft {

    private @NonNull List<Car> cars;
    private @NonNull RoadMapConfig config;
    private @NonNull Position position;
    private @NonNull RoadController roadController;
    private @NonNull TrafficViolationHandler trafficViolationHandler;
    private @NonNull TrafficCrashHandler trafficCrashHandler;

    private boolean isGreenOrYellowLight = false;

    public Road(
            RoadMapConfig config,
            @NonNull Position position,
            @NonNull RoadController roadController,
            @NonNull TrafficViolationHandler trafficViolationHandler,
            @NonNull TrafficCrashHandler trafficCrashHandler
    ){
        this.cars = new ArrayList<>(config.getNumberCars());
        this.config = config;
        this.position = position;
        this.roadController = roadController;
        this.trafficViolationHandler = trafficViolationHandler;
        this.trafficCrashHandler = trafficCrashHandler;
    }

    @SneakyThrows
    public void initCars() {
        cars.addAll(config.getCarsFromPosition(position));
    }

    private Car mainCar = new Car(new Coordinates(new Point(0,0)), Position.LEFT, Move.LEFT, 0);

    public boolean hasMainCar() {
        for(Car car : cars){
            if(car.isMainCar()){
                mainCar = car;
                return true;
            }
        }
        return false;
    }

    public Car getMainCar() {
        return mainCar;
    }

    public void moveAllCars() {
        if(isGreenOrYellowLight){
            for(Car car : cars){
                if(!needPassCarFromDiffRoads(car)){
                    car.moveForward(car.getCoordinates());
                }
            }
        } else if (moveTenPercent()){
            moveOnRedLight();
        } else {
            moveForwardAfterLight();
        }
    }

    private boolean needPassCarFromDiffRoads(Car car) {
        return roadController.needPassCarsFromDiffRoads(car);
    }

    private void moveOnRedLight() {
        for(Car car : cars){
            car.moveForwardAfterCrossRoad();
            if(car.isMain){
                if(car.crosseLight()){
                    if(roadController.isCrash(car)){
                        trafficCrashHandler.crash();
                    } else {
                        trafficViolationHandler.violation();
                    }
                }
                break;
            }
        }
    }

    private boolean moveTenPercent() {
        Random random = new Random();
        return random.nextInt(10) == 0;
    }

    private boolean needPassCar(Car car) {
        return roadController.needPassCarsFromDiffRoadsAfterLight(car);
    }

    private void moveForwardAfterLight() {
        for(Car car : cars){
            if(car.isAfterLight){
                if(!needPassCar(car)){
                    car.moveForwardAfterCrossRoad();
                }
            }
        }
    }

    public boolean hasActiveCars() {
        boolean hasActiveCars = false;
        for (Car car : cars) {
            if (car.isActive) {
                hasActiveCars = true;
                break;
            }
        }
        return hasActiveCars;
    }

    public Integer getActiveCars() {
        int activeCars = 0;
        for(Car car : cars){
            if(!car.isActive){
                activeCars += 1;
            }
        }
        return activeCars;
    }

    public List<Car> getCars() {
        return cars;
    }

    @Override
    public void canMoveCarsDown(boolean positions) {
        if(position == Position.DOWN){
            isGreenOrYellowLight = positions;
        }
    }

    @Override
    public void canMoveCarsLeft(boolean positions) {
        if(position == Position.LEFT){
            isGreenOrYellowLight = positions;
        }
    }

    @Override
    public void canMoveCarsRight(boolean positions) {
        if(position == Position.RIGHT){
            isGreenOrYellowLight = positions;
        }
    }

    @Override
    public void canMoveCarsUp(boolean positions) {
        if(position == Position.UP){
            isGreenOrYellowLight = positions;
        }
    }
}
