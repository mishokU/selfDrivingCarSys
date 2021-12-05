package config.cars;

import car.Car;

import java.util.List;

public interface CarsConfig {

    List<Car> getRightCars();

    List<Car> getLeftCars();

    List<Car> getUpCars();

    List<Car> getDownCars();

}
