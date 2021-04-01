package com.sudreeshya.sms.provider;

import com.sudreeshya.sms.dto.CustomUserDetail;
import com.sudreeshya.sms.model.ApplicationUser;
import com.sudreeshya.sms.util.AuthorityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Manjit Shakya
 * @email manjit.shakya@f1soft.com
 */
public class UsernamePasswordAuthenticationProvider implements AuthenticationProvider {

    private UserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String username = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();

        final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        final CustomUserDetail customUserDetail = (CustomUserDetail) userDetails;
        final ApplicationUser applicationUser = customUserDetail.getApplicationUser();



        final Authentication afterAuthenticated =
                new UsernamePasswordAuthenticationToken(
                        applicationUser,
                        applicationUser.getPassword(),
                        AuthorityUtil
                                .buildAuthorities(applicationUser.getAuthorities())
                );

        if(!validatePassword(password, applicationUser)){
            throw new IncorrectCredentialException("Username/Password is not correct");
        }
        // updating the thread local with the currently logged in user
        SecurityContextHolder.getContext().setAuthentication(afterAuthenticated);
        return authentication;
    }

    @Override
    public boolean supports(Class<?> token) {
        return token.equals(UsernamePasswordAuthenticationToken.class);
    }

    private Boolean validatePassword(String requestedPassword, ApplicationUser applicationUser){
        return requestedPassword.equals(applicationUser.getPassword());
    }

    public void setUserDetails(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }
}
