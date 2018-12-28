package com.enenim.scaffold.service.dao;

import com.enenim.scaffold.model.dao.PublicHoliday;
import com.enenim.scaffold.repository.dao.PublicHolidayRepository;
import com.enenim.scaffold.util.PageRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class PublicHolidayService {
    private final PublicHolidayRepository publicHolidayRepository;

    @Autowired
    public PublicHolidayService(PublicHolidayRepository publicHolidayRepository) {
        this.publicHolidayRepository = publicHolidayRepository;
    }

    public Page<PublicHoliday> getPublicHolidays(){
        return publicHolidayRepository.findAll(PageRequestUtil.getPageRequest());
    }

    public PublicHoliday getPublicHoliday(Long id){
        return publicHolidayRepository.findOrFail(id);
    }

    public PublicHoliday savePublicHoliday(PublicHoliday publicHoliday){
        return publicHolidayRepository.save(publicHoliday);
    }

    public void deletePublicHoliday(Long id){
        publicHolidayRepository.deleteById(id);
    }
}