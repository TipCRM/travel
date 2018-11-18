package com.travel.service.service;


import com.travel.service.entity.Travel;

public interface TravelService {

    Travel getTravelById(Long id);

    Long saveTravel(Travel travel);

    void deleteTravel(Long id);
}
