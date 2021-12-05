package main;


import config.RoadMapConfig;
import crossway.CrosswayRoadImpl;
import inputer.InputConsole;

public class Main {

    public static void main(String[]args){
        try {
            RoadMapConfig config = new RoadMapConfig();
            InputConsole inputConsole = new InputConsole(config);

            inputConsole.requestRoads();
            inputConsole.requestCars();
            inputConsole.requestMainNumberCar();

            CrosswayRoadImpl road = new CrosswayRoadImpl(config);
            road.init();
            road.run();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

}
