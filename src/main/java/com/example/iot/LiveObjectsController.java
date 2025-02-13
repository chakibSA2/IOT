package com.example.iot;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/liveobjects")
public class LiveObjectsController {

    private final LiveObjectsService liveObjectsService;

    public LiveObjectsController(LiveObjectsService liveObjectsService) {
        this.liveObjectsService = liveObjectsService;
    }

    @GetMapping("/decoded/{deviceId}")
    public SensorData getDecodedData(@PathVariable String deviceId) {
        return liveObjectsService.getDecodedData(deviceId);
    }
}

