package road;

import car.Car;
import lombok.NonNull;
import traffic_light.Color;
import traffic_light.TrafficLight;

import java.util.List;

public class CarsRenderer {

    private final Integer startMinBottomCarPosition = 8;
    private final Integer startMaxTopCarPosition = 8;

    private String[][] carsMatrix = new String[][] {};
    private String[][] carsUpdatedMatrix = new String[][] {};

    public void init() {
        initMatrix();
    }

    public void updateCarsPosition(List<Car> cars, @NonNull List<TrafficLight> lights){
        updateMatrix(cars, lights);
        for(int i = 0; i < ((startMaxTopCarPosition + 2) * 2); i++){
            for(int j = 0; j < ((startMinBottomCarPosition + 2) * 2); j++){
                System.out.print(carsUpdatedMatrix[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
        for(int i = 0; i < ((startMaxTopCarPosition + 2) * 2); i++){
            for(int j = 0; j < ((startMinBottomCarPosition + 2) * 2); j++){
                System.out.print(carsMatrix[i][j] + " ");
            }
            System.out.println();
        }

    }

    private void updateMatrix(List<Car> cars, @NonNull List<TrafficLight> lights) {
        int x = (startMaxTopCarPosition + 2) * 2;
        int y = (startMinBottomCarPosition + 2) * 2;
        carsUpdatedMatrix = new String[x][y];
        for(int i = 0; i < x; i++){
            for(int j = 0; j < y; j++){
                carsUpdatedMatrix[i][j] = "    ";
                for(Car car : cars){
                    if(carsMatrix[i][j].replaceAll(" ", "").equals(car.coordinates.toString())){
                        switch (car.moveFrom){
                            case RIGHT:
                                if(car.isActive){
                                    carsUpdatedMatrix[i][j] = "  " + car.index + " ";
                                } else {
                                    carsUpdatedMatrix[i][j] = " RR ";
                                }
                                break;
                            case UP:
                                if(car.isActive){
                                    carsUpdatedMatrix[i][j] = "  " + car.index + " ";
                                } else {
                                    carsUpdatedMatrix[i][j] = " UR ";
                                }
                                break;
                            case DOWN:
                                if(car.isActive){
                                    carsUpdatedMatrix[i][j] = "  " + car.index + " ";
                                } else {
                                    carsUpdatedMatrix[i][j] = " DR ";
                                }
                                break;
                            case LEFT:
                                if(car.isActive){
                                    carsUpdatedMatrix[i][j] = "  " + car.index + " ";
                                } else {
                                    carsUpdatedMatrix[i][j] = " LR ";
                                }
                                break;
                        }
                        if(car.isMain && !car.isActive){
                            carsUpdatedMatrix[i][j] = " MM ";
                        } else if(car.isMain){
                            carsUpdatedMatrix[i][j] = " MC ";
                        }
                    }
                }

                switch (carsMatrix[i][j]) {
                    case "-2 2":
                        carsUpdatedMatrix[i][j] = getLightColor(lights.get(0).getColor());
                        break;
                    case " 2 2":
                        carsUpdatedMatrix[i][j] = getLightColor(lights.get(1).getColor());
                        break;
                    case " 2-2":
                        carsUpdatedMatrix[i][j] = getLightColor(lights.get(2).getColor());
                        break;
                    case "-2-2":
                        carsUpdatedMatrix[i][j] = getLightColor(lights.get(3).getColor());
                        break;
                }

            }
        }
    }

    private String getLightColor(Color lightColor) {
        String color = "";
        switch (lightColor){
            case RED:
                color = " RR ";
               break;
            case GREEN:
                color = " GG ";
                break;
            case YELLOW:
                color = " YY ";
                break;
        }
        return color;
    }

    private void initMatrix() {
        int x = (startMaxTopCarPosition + 2) * 2;
        int y = (startMinBottomCarPosition + 2) * 2;
        carsMatrix = new String[x][y];
        for(int i = 0; i < x; i++){
            for(int j = 0; j < y; j++){

                int xIndex = j - startMaxTopCarPosition;
                int yIndex = startMaxTopCarPosition - i;

                String xString;
                String yString;

                if(xIndex >= 0){
                    xString = " " + xIndex;
                } else {
                    xString = String.valueOf(xIndex);
                }
                if(yIndex >= 0){
                    yString = " " + yIndex;
                } else {
                    yString = String.valueOf(yIndex);
                }
                carsMatrix[i][j] = xString + yString;
            }
        }
    }

}
