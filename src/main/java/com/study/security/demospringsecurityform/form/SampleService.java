package com.study.security.demospringsecurityform.form;

import com.study.security.demospringsecurityform.common.SecurityLogger;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class SampleService {
    public void dashboard() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //인증한 사용자의 정보
        Object principal = authentication.getPrincipal();
        //사용자의 권한
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Object credentials = authentication.getCredentials();
        boolean authenticated = authentication.isAuthenticated();
        Object details = authentication.getDetails();
    }

    /*
     * Async 어노테이션을 붙이면, 특정 빈안에 있는 메소드를 호출할 때
     * 별도의 스레드를 만들어서 비동기적으로 호출을 해줌
     */
    @Async
    public void asyncService() {
        SecurityLogger.log("Async service");
        System.out.println("Async service has been called");
    }
}
