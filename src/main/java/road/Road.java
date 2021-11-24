package road;

import car.Car;
import car.Move;
import car.Position;
import config.RoadMapConfig;
import lombok.NonNull;
import lombok.SneakyThrows;
import org.checkerframework.checker.nullness.qual.Nullable;
import position.Coordinates;
import position.Point;
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

    private boolean canMove = false;

    public Road(RoadMapConfig config, @NonNull Position position, @NonNull RoadController roadController){
        this.cars = new ArrayList<>(config.getNumberOfCars());
        this.config = config;
        this.position = position;
        this.roadController = roadController;
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
        if(canMove){
            for(Car car : cars){
                car.moveForward(car.getCoordinates());
            }
        } else if (moveTenPercent()){
            moveOnRedLight();
        } else {
            moveForwardAfterLight();
        }
    }

    private void moveOnRedLight() {
        for(Car car : cars){
            car.moveForwardAfterCrossRoad();
        }
    }

    private boolean moveTenPercent() {
        Random random = new Random();
        return random.nextInt(10) == 0;
    }

    private boolean needPassCar(Car car) {
        return roadController.needPassCar(car);
    }

    private @Nullable Car getCar(int index) {
        if(cars.isEmpty() || index < 0){
            return null;
        } else {
            return cars.get(index);
        }
    }

    private void moveForwardAfterLight() {
        for(Car car : cars){
            if(car.isAfterLight){
                car.moveForwardAfterCrossRoad();
            }
        }
    }

    private boolean needPassCarByCar(List<Car> cars) {
        List<Boolean> locks = new ArrayList<>();
        for(int i = 0; i < cars.size() - 1; i++){
            locks.add(roadController.needPassCars(position, cars.get(i)));
        }
        boolean needPass = false;
        for(int i = 0; i < locks.size() - 1; i++){
            if(locks.get(i)){
                needPass = true;
                break;
            }
        }
        return needPass;
    }

    private boolean needPassCars(@Nullable Car car) {
        return roadController.needPassCars(position, car);
    }

    private void moveForwardAfterCrossRoad() {
        for(Car car : cars){
            if(car.isAfterLight){
                car.moveForwardAfterCrossRoad();
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
            canMove = positions;
        }
    }

    @Override
    public void canMoveCarsLeft(boolean positions) {
        if(position == Position.LEFT){
            canMove = positions;
        }
    }

    @Override
    public void canMoveCarsRight(boolean positions) {
        if(position == Position.RIGHT){
            canMove = positions;
        }
    }

    @Override
    public void canMoveCarsUp(boolean positions) {
        if(position == Position.UP){
            canMove = positions;
        }
    }
}
