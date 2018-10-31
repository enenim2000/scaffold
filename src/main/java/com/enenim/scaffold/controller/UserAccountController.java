package com.enenim.scaffold.controller;


import com.enenim.scaffold.annotation.Post;
import com.enenim.scaffold.dto.request.LoginRequest;
import com.enenim.scaffold.dto.request.Request;
import com.enenim.scaffold.dto.response.Response;
import com.enenim.scaffold.dto.response.StringResponse;
import com.enenim.scaffold.enums.LoginStatus;
import com.enenim.scaffold.exception.ScaffoldException;
import com.enenim.scaffold.exception.UnAuthorizedException;
import com.enenim.scaffold.model.cache.LoginCache;
import com.enenim.scaffold.model.dao.Login;
import com.enenim.scaffold.service.TokenAuthenticationService;
import com.enenim.scaffold.service.dao.LoginService;
import com.enenim.scaffold.service.dao.TrackerService;
import com.enenim.scaffold.util.message.ExceptionMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;

@RestController
@RequestMapping("/user/accounts")
public class UserAccountController {

	private final LoginService loginService;
    private final TrackerService trackerService;
	private final TokenAuthenticationService tokenAuthenticationService;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	public UserAccountController(LoginService loginService, TrackerService trackerService, TokenAuthenticationService tokenAuthenticationService, BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.loginService = loginService;
		this.trackerService = trackerService;
		this.tokenAuthenticationService = tokenAuthenticationService;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	@Post("/authenticate")
	public Response<StringResponse> accountAuth(@RequestBody Request<LoginRequest> request) {
		request.getBody().validateRequest();
		Login login = loginService.getLoginByUsername(request.getBody().getUsername());
		if(bCryptPasswordEncoder.matches(request.getBody().getUsername(), login.getPassword())){
			if(login.getStatus() == LoginStatus.DISABLED){
				throw new ScaffoldException(ExceptionMessage.msg("disabled_account"));
			}else if(login.getStatus() == LoginStatus.LOCKED){
				throw new ScaffoldException(ExceptionMessage.msg("blocked_account"));
			}else {
				String token = tokenAuthenticationService.encodeToken(buildLoginToken(request));
				return new Response<>(new StringResponse(token));
			}
		}
		throw new UnAuthorizedException(ExceptionMessage.msg("invalid_login"));
	}

	private LoginCache buildLoginToken(Login login){
		LoginCache loginCache = new LoginCache();
		loginCache.setCreated(new Date());
		loginCache.setGlobalSettings(getGlobalSettings());
		loginCache.setUser(null);
	}

	private HashMap<String, Object> getGlobalSettings(){
		return new HashMap<>();
	}
}