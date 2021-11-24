package traffic_light;

import com.google.inject.Inject;
import config.RoadMapConfig;

public class ColorToTimeConverterImpl implements ColorToTimeConverter {

    @Inject
    @SuppressWarnings("initialization.fields.uninitialized")
    public RoadMapConfig config;

    @Override
    public TrafficLightTime convert(Color color) {
        TrafficLightTime time;
        switch (color){
            case RED:
                time = new TrafficLightTime(config.getLightActiveRedTime());
                break;
            case YELLOW:
                time = new TrafficLightTime(config.getLightActiveYellowTime());
                break;
            case GREEN:
                time = new TrafficLightTime(config.getLightActiveGreenTime());
                break;
            default:
                time = new TrafficLightTime(0);
                break;
        }
        return time;
    }
}
