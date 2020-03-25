package com.example.springsecuritydemo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final DataSource dataSource;

    public SecurityConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//                .withUser("john").password("test123").roles("EMPLOYEE").and()
//                .withUser("mary").password("test123").roles("EMPLOYEE", "MANAGER").and()
//                .withUser("susan").password("test123").roles("EMPLOYEE", "ADMIN");

        auth.jdbcAuthentication().dataSource(dataSource);

        // to specify you own schema
//                                .usersByUsernameQuery("select username, password, enabled "
//                                            + "from users "
//                                            + "where username = ?")
//                                .authoritiesByUsernameQuery("select username, authority "
//                                        + "from authorities "
//                                        + "where username = ?");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
//                .anyRequest().authenticated()
                .antMatchers("/").hasRole("EMPLOYEE")
                .antMatchers("/leaders/**").hasRole("MANAGER")
                .antMatchers("/systems/**").hasRole("ADMIN")
                .and()
                .formLogin()
                .loginPage("/login").loginProcessingUrl("/authenticateTheUser").permitAll()
                .and()
                .logout().permitAll()
                .and()
                .exceptionHandling().accessDeniedPage("/access-denied");


//        http.authorizeRequests()
//                .antMatchers("/").hasRole("EMPLOYEE")
//                .antMatchers("/leaders/**").hasRole("MANAGER")
//                .antMatchers("/systems/**").hasRole("ADMIN")
//                .and().formLogin();
    }
}
