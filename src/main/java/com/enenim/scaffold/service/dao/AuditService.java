package com.enenim.scaffold.service.dao;

import com.enenim.scaffold.model.dao.Audit;
import com.enenim.scaffold.repository.dao.AuditRepository;
import com.enenim.scaffold.util.PageRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class AuditService {
    private final AuditRepository auditRepository;

    @Autowired
    public AuditService(AuditRepository auditRepository) {
        this.auditRepository = auditRepository;
    }

    public Page<Audit> getAudits(){
        return auditRepository.findAll(PageRequestUtil.getPageRequest());
    }

    public Audit getAudit(Long id){
        return auditRepository.findOrFail(id);
    }

    public Audit saveAudit(Audit audit){
        return auditRepository.save(audit);
    }
}
