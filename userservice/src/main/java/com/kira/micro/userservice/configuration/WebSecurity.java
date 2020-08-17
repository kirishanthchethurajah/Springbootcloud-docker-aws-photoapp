package com.kira.micro.userservice.configuration;

import com.kira.micro.userservice.filter.AuthenticationFilter;
import com.kira.micro.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {

    private Environment environment;
    private UserService userService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    public WebSecurity(Environment environment, UserService userService, BCryptPasswordEncoder bCryptPasswordEncoder) {

        this.environment = environment;
        this.userService = userService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }


//authorization
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        //http.authorizeRequests().antMatchers("/users/**").permitAll();
        //http.authorizeRequests().antMatchers("/**").hasIpAddress(environment.getProperty("gateway-ip"))
        http.authorizeRequests()
            .antMatchers().permitAll()
            .antMatchers("/users/**").permitAll()
            .and()
            .addFilter(getAuthenticationFilter());
        // for H2 which runs on a frame
        http.headers().frameOptions().disable();
    }

    private AuthenticationFilter getAuthenticationFilter() throws Exception {
      AuthenticationFilter authenticationFilter = new AuthenticationFilter(userService, environment,authenticationManager());
      authenticationFilter.setFilterProcessesUrl("/users/login");
      return authenticationFilter;

    }
// authentication
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder);
    }
}
