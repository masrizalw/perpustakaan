package com.perpustakaan.app.security;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.perpustakaan.app.service.LoginAttemptService;

@Component
public class AuthFailedListener implements ApplicationListener<AuthenticationFailureBadCredentialsEvent>{
	
	@Autowired private LoginAttemptService loginAttemptService;
	
	@SuppressWarnings("NullableProblems")
    @Override
	public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent event) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest();
        loginAttemptService.loginFailed(request.getRemoteAddr());
	}

}
