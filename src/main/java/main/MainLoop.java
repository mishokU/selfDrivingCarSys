package main;

import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;
import config.RoadMapConfig;
import org.checkerframework.checker.nullness.qual.Nullable;
import road.RoadController;

public abstract class MainLoop {

    public RoadMapConfig config;

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    protected volatile CrosswayStatus status = CrosswayStatus.STOPPED;

    protected RoadController roadController;

    private @Nullable Thread mainThread;

    public MainLoop(RoadMapConfig config) {
        this.roadController = new RoadController(config);
        this.config = config;
    }

    protected Boolean isRoadRunning(){
        return status == CrosswayStatus.RUNNING;
    }

    protected void updateStatus(CrosswayStatus status) {
        this.status = status;
    }

    protected abstract void processMainLoop();

    public void run() {
        updateStatus(CrosswayStatus.RUNNING);
        mainThread = new Thread(this::processMainLoop);
        mainThread.start();
    }

    public void destroy(){
        status = CrosswayStatus.STOPPED;
        mainThread = null;
    }

    public abstract void init();
}
