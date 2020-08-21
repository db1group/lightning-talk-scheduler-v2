package com.db1group.ltscheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("schedule")
public class SchedulerController {

    private LightningTalkSchedulerService lightningTalkScheduler;

    @Autowired
    public SchedulerController(LightningTalkSchedulerService lightningTalkScheduler) {
        this.lightningTalkScheduler = lightningTalkScheduler;
    }

    @PostMapping
    ResponseEntity<?> scheduleNew() {
        return lightningTalkScheduler.schedule() ? ResponseEntity.status(201).build() : ResponseEntity.status(400).body("It Failed");
    }
}
