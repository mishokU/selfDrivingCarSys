package crossway;

import car.Car;
import car.Position;
import config.RoadMapConfig;
import lombok.NonNull;
import lombok.SneakyThrows;
import main.CrosswayStatus;
import main.MainLoop;
import road.CarsRenderer;
import road.Road;
import road.RoadController;
import traffic_light.TrafficLight;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CrosswayRoadImpl extends MainLoop implements CrosswayRoad {

    private @NonNull List<Road> roads;
    private @NonNull List<TrafficLight> lights;
    private @NonNull CarsRenderer carsRenderer = new CarsRenderer();
    private @NonNull RoadController roadController;

    private Integer currentProgramTime = 0;
    private final Integer programTick = 2000;

    private final List<Position> positions = Arrays.asList(
            Position.DOWN,
            Position.LEFT,
            Position.UP,
            Position.RIGHT
    );

    public CrosswayRoadImpl(RoadMapConfig config) {
        super(config);
        this.roads = new ArrayList<>(config.getNumberOfRoads());
        this.lights = new ArrayList<>(config.getNumberOfLights());
        this.roadController = new RoadController(config);
    }

    @Override
    public void init() {
        updateStatus(CrosswayStatus.RUNNING);
        carsRenderer.init();
        initRoads();
        initLights();
    }

    private void initLights() {
        for(int i = 0; i < config.getNumberOfLights(); i++){
            TrafficLight light = new TrafficLight(config, roads.get(i), positions.get(i));
            light.init();
            lights.add(light);
        }
    }

    private void initRoads() {
        for(int i = 0; i < config.getNumberOfRoads(); i++){
            Road road = new Road(config, positions.get(i), roadController);
            road.initCars();
            roads.add(road);
        }
    }

    @SneakyThrows
    @Override
    protected void processMainLoop() {
        while (isRoadRunning() && hasActiveCars()) {

            update();

            render();

            findMainCar();


            currentProgramTime += programTick;

            if(currentProgramTime > 20000){
                updateStatus(CrosswayStatus.STOPPED);
            }
        }
    }

    @Override
    public void render() {
        Integer activeCars = getActiveCarsFromAllRoads();
        System.out.println("Current time: " + currentProgramTime);
        System.out.println("Lights state: " +
                "UP: " + lights.get(0).getColor().name() +
                " RIGHT: " + lights.get(1).getColor().name() +
                " LEFT: " + lights.get(2).getColor().name() +
                " DOWN: " + lights.get(3).getColor().name()
        );
        System.out.println("Cars that crosse the road: " + activeCars);
        carsRenderer.updateCarsPosition(getAllCars(), lights);
    }

    private List<Car> getAllCars() {
        List<Car> cars = new ArrayList<>();
        for(Road road : roads){
            cars.addAll(road.getCars());
        }
        return cars;
    }

    private Integer getActiveCarsFromAllRoads() {
        Integer activeCars = 0;
        for(Road road : roads){
            activeCars += road.getActiveCars();
        }
        return activeCars;
    }

    private boolean hasActiveCars() {
        boolean hasActiveCars = false;
        for(Road road : roads){
            if(road.hasActiveCars()){
                hasActiveCars = true;
            }
        }
        return hasActiveCars;
    }

    protected void findMainCar(){
        for(Road road : roads){
            if(road.hasMainCar()) {
                if(road.getMainCar() != null){
                    if(!road.getMainCar().isActive){
                        updateStatus(CrosswayStatus.FINISHED);
                    }
                }
            }
        }
    }

    protected void update() {
        for(TrafficLight light : lights){
            light.changeLight();
        }
        for(Road road : roads){
            road.moveAllCars();
        }
    }

}
