
package com.enenim.scaffold.service.dao;

import com.enenim.scaffold.enums.VerifyStatus;
import com.enenim.scaffold.model.dao.Login;
import com.enenim.scaffold.repository.dao.LoginRepository;
import com.enenim.scaffold.util.PageRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    private final LoginRepository loginRepository;

    @Autowired
    public LoginService(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    public Page<Login> getLogins() {
        return loginRepository.findAll(PageRequestUtil.getPageRequest());
    }

    public Login getLogin(Long id) {
        return loginRepository.findOrFail(id);
    }

    public Login getLoginByUsername(String username) {
        return loginRepository.findByUsername(username).orElse(null);
    }

    public Login saveLogin(Login login) {
        return loginRepository.save(login);
    }

    @Async
    public void updateVerifyStatus(VerifyStatus verifyStatus, String username) {
        loginRepository.updateVerifyStatus(verifyStatus, username);
    }

    public void deleteLogin(Long id) {
        loginRepository.deleteById(id);
    }
}