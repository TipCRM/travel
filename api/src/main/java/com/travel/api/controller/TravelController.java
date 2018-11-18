package com.travel.api.controller;

import com.travel.api.utils.JsonEntity;
import com.travel.api.utils.ResponseHelper;
import com.travel.service.entity.Travel;
import com.travel.service.service.TravelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public/api")
public class TravelController {
    @Autowired
    private TravelService travelService;

    @GetMapping("/travel/{id}")
    public JsonEntity<Travel> getTravelById(@PathVariable("id") Long id) {
        return ResponseHelper.createInstance(travelService.getTravelById(id));
    }

    @PostMapping("/travel")
    public JsonEntity<Long> createNewTravel(@RequestBody Travel travel) {
        return ResponseHelper.createInstance(travelService.saveTravel(travel));
    }

    @DeleteMapping("/travel/{id}")
    public JsonEntity deleteTravel(@PathVariable("id") Long id) {
        travelService.deleteTravel(id);
        return ResponseHelper.ofNothing();
    }

}
