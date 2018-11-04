package com.enenim.scaffold.service.dao;

import com.enenim.scaffold.enums.EnabledStatus;
import com.enenim.scaffold.model.dao.ActiveHour;
import com.enenim.scaffold.repository.dao.ActiveHourRepository;
import com.enenim.scaffold.shared.IdName;
import com.enenim.scaffold.util.PageRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ActiveHourService {
    private final ActiveHourRepository activeHourRepository;

    @Autowired
    public ActiveHourService(ActiveHourRepository activeHourRepository) {
        this.activeHourRepository = activeHourRepository;
    }

    public Page<ActiveHour> getActiveHours(){
        return activeHourRepository.findAll(PageRequestUtil.getPageRequest());
    }

    public ActiveHour getActiveHour(Long id){
        return activeHourRepository.findOrFail(id);
    }

    public Page<ActiveHour> getActiveHourByStatus(EnabledStatus status){
        return activeHourRepository.findActiveHoursByStatus(status, PageRequestUtil.getPageRequest());
    }

    public ActiveHour saveActiveHour(ActiveHour activeHour){
        return activeHourRepository.save(activeHour);
    }

    public void deleteActiveHour(Long id){
        activeHourRepository.deleteById(id);
    }

    public Map<String , String> options(){
        Map<String, String> options = new HashMap<>();
        for( IdName idName : activeHourRepository.findEnabledActiveHours()){
            options.put(String.valueOf(idName.getId()), idName.getName());
        }
        return options.entrySet().isEmpty() ? null : options;
    }
}