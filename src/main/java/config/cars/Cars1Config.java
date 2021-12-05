package config.cars;

import car.Car;
import car.Move;
import car.Position;
import position.Coordinates;
import position.Point;

import java.util.Arrays;
import java.util.List;

public class Cars1Config implements CarsConfig {

    public final List<Car> carsLeft = Arrays.asList(
            new Car(new Coordinates(new Point(-2, -1)), Position.LEFT, Move.UP, 1),
            new Car(new Coordinates(new Point(-3, -1)), Position.LEFT, Move.UP, 2),
            new Car(new Coordinates(new Point(-4, -1)), Position.LEFT, Move.UP, 3),
            new Car(new Coordinates(new Point(-5, -1)), Position.LEFT, Move.UP,4)
    );

    public final List<Car> carsRight = Arrays.asList();

    public List<Car> carsUp = Arrays.asList(
            new Car(new Coordinates(new Point(1, -2)), Position.UP, Move.UP, 5),
            new Car(new Coordinates(new Point(1, -3)), Position.UP, Move.UP, 6),
            new Car(new Coordinates(new Point(1, -4)), Position.UP, Move.UP, 7),
            new Car(new Coordinates(new Point(1, -5)), Position.UP, Move.UP,8,true)
    );

    public final List<Car> carsDown = Arrays.asList(
            new Car(new Coordinates(new Point(-1, 2)), Position.DOWN, Move.DOWN, 9),
            new Car(new Coordinates(new Point(-1, 3)), Position.DOWN, Move.DOWN, 10),
            new Car(new Coordinates(new Point(-1, 4)), Position.DOWN, Move.DOWN, 11),
            new Car(new Coordinates(new Point(-1, 5)), Position.DOWN, Move.DOWN, 12)
    );

    @Override
    public List<Car> getRightCars() {
        return carsRight;
    }

    @Override
    public List<Car> getLeftCars() {
        return carsLeft;
    }

    @Override
    public List<Car> getUpCars() {
        return carsUp;
    }

    @Override
    public List<Car> getDownCars() {
        return carsDown;
    }

}
