package com.enenim.scaffold.service.dao;

import com.enenim.scaffold.model.dao.LogEvent;
import com.enenim.scaffold.repository.dao.LogEventRepository;
import com.enenim.scaffold.util.PageRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class LogEventService {
    private final LogEventRepository logEventRepository;

    @Autowired
    public LogEventService(LogEventRepository logEventRepository) {
        this.logEventRepository = logEventRepository;
    }

    public Page<LogEvent> getLogEvents(){
        return logEventRepository.findAll(PageRequestUtil.getPageRequest());
    }

    public LogEvent getLogEvent(Long id){
        return logEventRepository.findOrFail(id);
    }

    @Async
    public LogEvent saveLogEvent(LogEvent logEvent){
        return logEventRepository.save(logEvent);
    }
}
