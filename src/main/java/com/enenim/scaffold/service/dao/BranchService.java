package com.enenim.scaffold.service.dao;

import com.enenim.scaffold.model.dao.Branch;
import com.enenim.scaffold.repository.dao.BranchRepository;
import com.enenim.scaffold.util.PageRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class BranchService {
    private final BranchRepository branchRepository;

    @Autowired
    public BranchService(BranchRepository branchRepository) {
        this.branchRepository = branchRepository;
    }

    public Page<Branch> getBranches(){
        return branchRepository.findAll(PageRequestUtil.getPageRequest());
    }

    public Branch getBranch(Long id){
        return branchRepository.findOrFail(id);
    }

    public Branch saveBranch(Branch branch){
        return branchRepository.save(branch);
    }
}
