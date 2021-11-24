package main;


import config.RoadMapConfig;
import crossway.CrosswayRoadImpl;

public class Main {

    public static void main(String[]args){
        try {
            CrosswayRoadImpl road = new CrosswayRoadImpl(new RoadMapConfig());
            road.init();
            road.run();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

}
