package com.example.SpringBootApp1.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

/*
    https://www.javaguides.net/2019/10/spring-security-hibernate-database-authentication-example.html
    https://www.javaguides.net/2019/10/spring-security-in-memory-authentication-example.html
    https://mkyong.com/spring-security/spring-security-form-login-using-database/
    https://www.javainuse.com/spring/boot_security_jdbc_authentication
    https://www.javainuse.com/spring/boot_security_csrf

 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, proxyTargetClass = true) // It help to keep method level security
public class SpringSecurityConfigDemo  extends WebSecurityConfigurerAdapter
{

   /* @Autowired
    DataSource dataSource;

	//Enable jdbc authentication
    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception
    {
        auth.jdbcAuthentication().dataSource(dataSource);
    }*/

   @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception
    {
        auth.inMemoryAuthentication().withUser("user").password("{noop}123456").roles("USER");
        auth.inMemoryAuthentication().withUser("admin").password("{noop}123456").roles("ADMIN");
        auth.inMemoryAuthentication().withUser("dba").password("{noop}123456").roles("DBA");

        /* For Database Authenetcation
             @Autowired
            DataSource dataSource;

             auth.jdbcAuthentication().dataSource(dataSource).usersByUsernameQuery(
            "select username,password, enabled from users where username=?")
            .authoritiesByUsernameQuery("select username, role from user_roles where username=?");

           auth.inMemoryAuthentication().withUser("ramesh").password("{noop}ramesh").roles("USER").and().withUser("admin")
            .password("{noop}admin").credentialsExpired(true).accountExpired(true).accountLocked(true)
            .authorities("WRITE_PRIVILEGES", "READ_PRIVILEGES").roles("ADMIN");

            Note that we have added a password storage format, for plain text, add {noop}. Prior to Spring Security 5.0, the default PasswordEncoder was NoOpPasswordEncoder which required plain text passwords.
            In Spring Security 5, the default is DelegatingPasswordEncoder, which required Password Storage Format like {noop}.
         */
    }

  @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        http.csrf().disable().authorizeRequests()
                .antMatchers("/h2/**").permitAll()
                .anyRequest().authenticated().and().formLogin();

        // disable frame options
        http.headers().frameOptions().disable();

      /*  http.authorizeRequests().antMatchers("/h2/**").permitAll()
                .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
                .antMatchers("/dba/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_DBA')")
                .and().formLogin();*/


          /*  http.antMatcher("/**").authorizeRequests().anyRequest().hasRole("USER")
				.and().formLogin().loginPage("/login.jsp")
				.failureUrl("/login.jsp?error=1").loginProcessingUrl("/login")
				.permitAll().and().logout()
				.logoutSuccessUrl("/listEmployees.html");*/

			/*http.csrf().disable().authorizeRequests().anyRequest().authenticated().and().httpBasic().and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);*/

    }

}
