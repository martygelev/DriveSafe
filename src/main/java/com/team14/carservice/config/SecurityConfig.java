package com.team14.carservice.config;

import com.team14.carservice.web.LoggingAccessDeniedHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
   
   private final DataSource securityDataSource;
   private final LoggingAccessDeniedHandler accessDeniedHandler;
   private final AuthenticationSuccessHandler authenticationSuccessHandler;
   
   private final String[] allowAllAccess = {
           "/pdf/**", "/pdf",
           "/js/**", "/css/**", "/fonts/**",
           "/images/**",
           "/common/**", "/error/**",
           "/fragments/**", "/anonymous/**"
      
   };
   
   private final String[] anonymousAccess = {"/anonymous/updatePassword*",
           "/user/savePassword*",};
   
   private final String[] adminAccess = {"/cars", "/emailPage", "/service", "/customers", "/event", "/admin"};
   
   @Autowired
   public SecurityConfig(DataSource dataSource,
                         LoggingAccessDeniedHandler accessDeniedHandler,
                         AuthenticationSuccessHandler authenticationSuccessHandler) {
      this.securityDataSource = dataSource;
      this.accessDeniedHandler = accessDeniedHandler;
      this.authenticationSuccessHandler = authenticationSuccessHandler;
   }
   
   
   @Bean
   public UserDetailsManager userDetailsManager() {
      JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager();
      jdbcUserDetailsManager.setDataSource(securityDataSource);
      return jdbcUserDetailsManager;
      
   }
   
   @Override
   protected void configure(AuthenticationManagerBuilder auth) throws Exception {
      auth.jdbcAuthentication().dataSource(securityDataSource).passwordEncoder(new BCryptPasswordEncoder());
   }
   
   @Override
   protected void configure(HttpSecurity http) throws Exception {
      
      http
              .csrf().disable()
              .authorizeRequests()
              .antMatchers(allowAllAccess).permitAll()
              .antMatchers(anonymousAccess).hasAuthority("CHANGE_PASSWORD_PRIVILEGE")
              .antMatchers("/admin").hasRole("ADMIN")
//              .antMatchers("/user").hasRole("USER")
//              .and().authorizeRequests()
              .anyRequest().authenticated().and()
              .formLogin().loginPage("/login")
              .loginProcessingUrl("/authenticate")
              .successHandler(authenticationSuccessHandler)
              .permitAll().and().logout()
              .invalidateHttpSession(true)
              .clearAuthentication(true)
              .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
              .logoutSuccessUrl("/login?logout")
              .permitAll().and().exceptionHandling()
              .accessDeniedHandler(accessDeniedHandler);
   }
   
   
   @Bean
   public PasswordEncoder passwordEncoder() {
      return new BCryptPasswordEncoder();
   }
   
   
}

