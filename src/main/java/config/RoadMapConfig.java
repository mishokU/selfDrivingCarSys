package config;

import car.Car;
import car.Move;
import car.Position;
import lombok.Getter;
import lombok.Setter;
import position.Coordinates;
import position.Point;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RoadMapConfig {

    public List<Car> getCarsFromPosition(Position position) {
        List<Car> cars = new ArrayList<>();
        switch (position){
            case LEFT:
                cars = carsLeft.subList(0, numberOfCars);
                break;
            case DOWN:
                cars = carsDown.subList(0, numberOfCars);
                break;
            case RIGHT:
                cars = carsRight.subList(0, numberOfCars);
                break;
            case UP:
                cars = carsUp.subList(0, numberOfCars);
                break;
        }
        return cars;
    }

    public final List<Car> cars = new ArrayList<>();

    public List<Car> getAllCars(){
        cars.clear();
        if(numberOfRoads == 2){
            cars.addAll(carsUp);
            cars.addAll(carsDown);
        } else if(numberOfRoads == 3){
            cars.addAll(carsLeft);
            cars.addAll(carsUp);
            cars.addAll(carsDown);
        } else {
            cars.addAll(carsLeft);
            cars.addAll(carsUp);
            cars.addAll(carsRight);
            cars.addAll(carsDown);
        }
        return cars;
    }

    public void setMainCarIndex(int value) {
        for(Car car: getAllCars()){
            if(car.index == value){
                car.isMain = true;
                break;
            }
        }
    }

    public final List<Car> carsLeft = Arrays.asList(
            new Car(new Coordinates(new Point(-2, -1)), Position.LEFT, Move.LEFT, 1),
            new Car(new Coordinates(new Point(-3, -1)), Position.LEFT, Move.LEFT, 2),
            new Car(new Coordinates(new Point(-4, -1)), Position.LEFT, Move.LEFT, 3),
            new Car(new Coordinates(new Point(-5, -1)), Position.LEFT, Move.LEFT,4),
            new Car(new Coordinates(new Point(-6, -1)), Position.LEFT, Move.LEFT,5)
    );

    public final List<Car> carsRight = Arrays.asList(
            new Car(new Coordinates(new Point(2, 1)), Position.RIGHT, Move.RIGHT, 6),
            new Car(new Coordinates(new Point(3, 1)), Position.RIGHT, Move.RIGHT, 7),
            new Car(new Coordinates(new Point(4, 1)), Position.RIGHT, Move.RIGHT, 8),
            new Car(new Coordinates(new Point(5, 1)), Position.RIGHT, Move.RIGHT,9),
            new Car(new Coordinates(new Point(6, 1)), Position.RIGHT, Move.RIGHT,10)
    );

    public List<Car> carsUp = Arrays.asList(
            new Car(new Coordinates(new Point(1, -2)), Position.UP, Move.UP, 11),
            new Car(new Coordinates(new Point(1, -3)), Position.UP, Move.UP, 12),
            new Car(new Coordinates(new Point(1, -4)), Position.UP, Move.UP, 13),
            new Car(new Coordinates(new Point(1, -5)), Position.UP, Move.UP,14),
            new Car(new Coordinates(new Point(1, -6)), Position.UP, Move.UP,15)
    );

    public final List<Car> carsDown = Arrays.asList(
            new Car(new Coordinates(new Point(-1, 2)), Position.DOWN, Move.DOWN, 16),
            new Car(new Coordinates(new Point(-1, 3)), Position.DOWN, Move.DOWN, 17),
            new Car(new Coordinates(new Point(-1, 4)), Position.DOWN, Move.DOWN, 18),
            new Car(new Coordinates(new Point(-1, 5)), Position.DOWN, Move.DOWN, 19),
            new Car(new Coordinates(new Point(-1, 6)), Position.DOWN, Move.DOWN,20)
    );

    @Getter
    Integer LoopDurationTime = 20;

    //Must be equal
    @Setter @Getter Integer numberOfRoads = 4;
    @Setter @Getter Integer numberOfLights = 4;

    @Setter Integer numberOfCars = 4;
    @Setter @Getter Integer mainCarRowPosition = 0;

    public  boolean picked = false;

    @Setter @Getter Integer lightActiveGreenTime = 4000;
    @Setter @Getter Integer lightActiveRedTime = 4000;
    @Setter @Getter Integer lightActiveYellowTime = 2000;

    public int getNumberCars() {
        return numberOfCars;
    }

    public List<Position> createPositions() {
        if(numberOfRoads == 2){ return Arrays.asList(Position.DOWN, Position.UP); }
        if(numberOfRoads == 3){ return Arrays.asList(Position.DOWN, Position.LEFT, Position.UP); }
        return Arrays.asList(Position.DOWN, Position.LEFT, Position.UP, Position.RIGHT);
    }
}
