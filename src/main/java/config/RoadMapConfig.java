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
                cars = carsLeft;
                break;
            case DOWN:
                cars = carsDown;
                break;
            case RIGHT:
                cars = carsRight;
                break;
            case UP:
                cars = carsUp;
                break;
        }
        return cars;
    }

    public final List<Car> cars = new ArrayList<>();

    public List<Car> getAllCars(){
        cars.addAll(carsLeft);
        cars.addAll(carsUp);
        cars.addAll(carsRight);
        cars.addAll(carsDown);
        return cars;
    }

    public final List<Car> carsLeft = Arrays.asList();

    public final List<Car> carsRight = Arrays.asList();

    public List<Car> carsUp = Arrays.asList(
            new Car(new Coordinates(new Point(1, -2)), Position.UP, Move.UP, 5),
            new Car(new Coordinates(new Point(1, -3)), Position.UP, Move.UP, 6),
            new Car(new Coordinates(new Point(1, -4)), Position.UP, Move.UP, 7),
            new Car(new Coordinates(new Point(1, -5)), Position.UP, Move.RIGHT,8,true)
    );

    public final List<Car> carsDown = Arrays.asList(
            new Car(new Coordinates(new Point(-1, 2)), Position.DOWN, Move.DOWN, 9),
            new Car(new Coordinates(new Point(-1, 3)), Position.DOWN, Move.DOWN, 10),
            new Car(new Coordinates(new Point(-1, 4)), Position.DOWN, Move.DOWN, 11),
            new Car(new Coordinates(new Point(-1, 5)), Position.DOWN, Move.DOWN, 12)
    );

    @Getter
    Integer LoopDurationTime = 20;

    //Must be equal
    @Setter @Getter Integer numberOfRoads = 4;
    @Setter @Getter Integer numberOfLights = 4;

    @Setter @Getter Integer numberOfCars = 4 - 1;
    @Setter @Getter Integer mainCarRowPosition = 0;

    //@Setter @Getter Color lightColor = getColor();

    public  boolean picked = false;

    @Setter @Getter Integer lightActiveGreenTime = 4000;
    @Setter @Getter Integer lightActiveRedTime = 4000;
    @Setter @Getter Integer lightActiveYellowTime = 2000;

}
