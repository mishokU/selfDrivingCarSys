package inputer;

import car.Car;
import config.RoadMapConfig;
import lombok.Getter;
import lombok.NonNull;

import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import static java.util.stream.Collectors.toList;

public class InputConsole {

    public Scanner scanner = new Scanner(System.in);
    public @Getter InputState inputState = InputState.INIT;
    public @NonNull RoadMapConfig config;

    private final int minNumberOfRoads = 2;
    private final int maxNumberOfRoads = 4;

    private final int minNumberOfCars = 1;
    private final int maxNumberOfCars = 5;

    public InputConsole(@NonNull RoadMapConfig config) {
        this.config = config;
    }

    public void requestRoads(){
        System.out.print("Write roads number from 2 to 4: ");
        int value = parseString(scanner.nextLine());
        if(value == -1 || notInRange(value, minNumberOfRoads, maxNumberOfRoads)){
            requestRoads();
        } else {
            config.setNumberOfRoads(value);
            config.setNumberOfLights(value);
        }
    }

    public void requestCars(){
        System.out.print("Write cars for road number from 1 to 5: ");
        int value = parseString(scanner.nextLine());
        if(value == -1 || notInRange(value, minNumberOfCars, maxNumberOfCars)){
            requestCars();
        } else {
            config.setNumberOfCars(value);
        }
    }

    public void requestMainNumberCar(){
        List<Integer> numbers = getCarNumbers();
        System.out.print("Choose main number from these: " + getCarNumbers() + ": ");
        int value = parseString(scanner.nextLine());
        if(value == -1 || notInRange(value, numbers)){
            requestMainNumberCar();
        } else {
            config.setMainCarIndex(value);
        }
    }

    private int parseString(String line) {
        int value = -1;
        try {
            value = Integer.parseInt(line);
        } catch (Exception exception){
            System.out.println("Bad input, try again...");
        }
        return value;
    }

    private boolean notInRange(int value, int minNumberOfCars, int maxNumberOfCars) {
        return value < minNumberOfCars || value > maxNumberOfCars;
    }

    private boolean notInRange(int value, List<Integer> numbers) {
        boolean findExistNumber = true;
        for(Integer number: numbers){
            if (number == value) {
                findExistNumber = false;
                break;
            }
        }
        return findExistNumber;
    }

    private List<Integer> getCarNumbers() {
        int carsOnSingleRoad = config.getNumberCars();
        List<Car> cars = new java.util.ArrayList<>(Collections.emptyList());
        if(config.getNumberOfRoads() == 2){
            cars.addAll(config.carsUp.subList(0,carsOnSingleRoad));
            cars.addAll(config.carsDown.subList(0, carsOnSingleRoad));
        } else if(config.getNumberOfRoads() == 3){
            cars.addAll(config.carsLeft.subList(0, carsOnSingleRoad));
            cars.addAll(config.carsUp.subList(0,carsOnSingleRoad));
            cars.addAll(config.carsDown.subList(0, carsOnSingleRoad));
        } else if(config.getNumberOfRoads() == 4){
            cars.addAll(config.carsLeft.subList(0,carsOnSingleRoad));
            cars.addAll(config.carsRight.subList(0, carsOnSingleRoad));
            cars.addAll(config.carsUp.subList(0,carsOnSingleRoad));
            cars.addAll(config.carsDown.subList(0, carsOnSingleRoad));
        }
        return cars.stream().map(value -> value.index).collect(toList());
    }

}
