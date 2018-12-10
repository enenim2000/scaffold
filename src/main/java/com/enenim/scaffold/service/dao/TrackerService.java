package com.enenim.scaffold.service.dao;

import com.enenim.scaffold.model.dao.Tracker;
import com.enenim.scaffold.repository.dao.TrackerRepository;
import com.enenim.scaffold.util.PageRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class TrackerService {
    private final TrackerRepository trackerRepository;

    @Autowired
    public TrackerService(TrackerRepository trackerRepository) {
        this.trackerRepository = trackerRepository;
    }

    public Page<Tracker> getTrackers(){
        return trackerRepository.findAll(PageRequestUtil.getPageRequest());
    }

    public Tracker getTracker(Long id){
        return trackerRepository.findOrFail(id);
    }

    public Tracker getTrackerBySessionId(String sessionId){
        return trackerRepository.findBySessionId(sessionId).orElse(null);
    }

    public Tracker saveTracker(Tracker tracker){
        return trackerRepository.save(tracker);
    }

    public void deleteTracker(Long id){
        trackerRepository.deleteById(id);
    }
}