package com.travel.service.service.impl;

import com.travel.service.entity.Travel;
import com.travel.service.repository.TravelRepository;
import com.travel.service.service.TravelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class TravelServiceImpl implements TravelService {

    @Autowired
    private TravelRepository travelRepository;

    @Override
    public Travel getTravelById(Long id) {
        return travelRepository.findById(id).orElse(null);
    }

    @Override
    public Long saveTravel(Travel travel) {
        return travelRepository.save(travel).getId();
    }

    @Override
    public void deleteTravel(Long id) {
        travelRepository.deleteById(id);
    }
}
