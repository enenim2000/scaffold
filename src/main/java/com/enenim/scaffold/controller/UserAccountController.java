package com.enenim.scaffold.controller;

import com.enenim.scaffold.annotation.Post;
import com.enenim.scaffold.dto.request.LoginRequest;
import com.enenim.scaffold.dto.request.Request;
import com.enenim.scaffold.dto.response.Response;
import com.enenim.scaffold.dto.response.StringResponse;
import com.enenim.scaffold.enums.EnabledStatus;
import com.enenim.scaffold.enums.LoginStatus;
import com.enenim.scaffold.exception.ScaffoldException;
import com.enenim.scaffold.exception.UnAuthorizedException;
import com.enenim.scaffold.model.cache.LoginCache;
import com.enenim.scaffold.model.dao.Login;
import com.enenim.scaffold.model.dao.Tracker;
import com.enenim.scaffold.service.TokenAuthenticationService;
import com.enenim.scaffold.service.UserResolverService;
import com.enenim.scaffold.service.dao.LoginService;
import com.enenim.scaffold.service.dao.TrackerService;
import com.enenim.scaffold.util.RequestUtil;
import com.enenim.scaffold.util.message.CommonMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Date;
import java.util.HashMap;

@RestController
@RequestMapping("/user/accounts")
public class UserAccountController {

	@Value("${lang}")
	private String lang;

	private final LoginService loginService;
    private final TrackerService trackerService;
	private final TokenAuthenticationService tokenAuthenticationService;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private final UserResolverService userResolverService;

	@Autowired
	public UserAccountController(LoginService loginService, TrackerService trackerService, TokenAuthenticationService tokenAuthenticationService, BCryptPasswordEncoder bCryptPasswordEncoder, UserResolverService userResolverService) {
		this.loginService = loginService;
		this.trackerService = trackerService;
		this.tokenAuthenticationService = tokenAuthenticationService;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		this.userResolverService = userResolverService;
	}

	@Post("/authenticate")
	public Response<StringResponse> accountAuth(@Valid @RequestBody Request<LoginRequest> request) {
		RequestUtil.setCommonRequestProperties(request);
		RequestUtil.setMessage(CommonMessage.msg("successful_logged_in"));
		Login login = loginService.getLoginByUsername(request.getBody().getUsername());
		if (!StringUtils.isEmpty(login) && bCryptPasswordEncoder.matches(request.getBody().getPassword(), login.getPassword())) {
			if (login.getStatus() == LoginStatus.DISABLED) {
				throw new ScaffoldException("disabled_account");
			} else if (login.getStatus() == LoginStatus.LOCKED) {
				throw new ScaffoldException("blocked_account");
			} else {
				LoginCache loginToken = buildLoginToken(login);
				String token = tokenAuthenticationService.encodeToken(loginToken);
				trackerService.saveTracker(loginToken.getTracker());
				tokenAuthenticationService.saveToken(loginToken);
				return new Response<>(new StringResponse(token));
			}
		}
		throw new UnAuthorizedException("invalid_login");
	}

	private LoginCache buildLoginToken(Login login){
		Date date = new Date();
		Tracker tracker = new Tracker(login, date);

		LoginCache loginCache = new LoginCache();
		loginCache.setCreated(date);
		loginCache.setGlobalSettings(getGlobalSettings());
		loginCache.setUser(userResolverService.getUser(login.getUserType(), login.getUserId()));
		loginCache.setTracker(tracker);

		System.out.println("tracker = " + tracker);

		loginCache.setEnabled(EnabledStatus.ENABLED);
		loginCache.setId(login.getId());
		loginCache.setLastLoggedIn(date);
		loginCache.setUserType(login.getUserType());
		loginCache.setUserId(login.getUserId());
		loginCache.setUsername(login.getUsername());

		return loginCache;
	}

	private HashMap<String, Object> getGlobalSettings(){
		return new HashMap<>();
	}
}