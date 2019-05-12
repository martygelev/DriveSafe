package com.team14.carservice.service;

import com.team14.carservice.models.PasswordResetToken;
import com.team14.carservice.repository.CustomerRepository;
import com.team14.carservice.repository.PasswordResetTokenRepository;
import com.team14.carservice.service.common.PasswordResetTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PasswordResetTokenServiceImpl implements PasswordResetTokenService {
   
   private final PasswordResetTokenRepository repository;
   private final UserDetailsManager userDetailsManager;
   private final CustomerRepository customerRepository;
   
   @Autowired
   public PasswordResetTokenServiceImpl(CustomerRepository customerRepository,
                                        UserDetailsManager userDetailsManager,
                                        PasswordResetTokenRepository repository) {
      this.repository = repository;
      this.customerRepository = customerRepository;
      this.userDetailsManager = userDetailsManager;
   }
   
   @Override
   public void disablePreviousTokensForUser(Integer id) {
      //disables another use of the same token
      
      List<PasswordResetToken> tokens = repository.findAllByCustomer_Id(id);
      
      if (!tokens.isEmpty()) {
         for (PasswordResetToken t : tokens) {
            if (!isExpired(t)) {
               t.setExpiryDate(getExpiredDate());
               repository.save(t);
            }
         }
      }
      
   }
   
   @Override
   public String validatePasswordResetToken(String username, String token) {
      
      PasswordResetToken passToken = repository.findByToken(token);
      if ((passToken == null) || (!passToken.getCustomer().getEmail().equals(username))) {
         return "invalidToken";
      }
      
      if (isExpired(passToken)) return "expired";
      
      User user = userDetailsToUser(userDetailsManager.loadUserByUsername(passToken.getCustomer().getEmail()));
      
      Authentication auth = new UsernamePasswordAuthenticationToken(
              user, null, Arrays.asList(
              new SimpleGrantedAuthority("CHANGE_PASSWORD_PRIVILEGE")));
      
      SecurityContextHolder.getContext().setAuthentication(auth);
      return null;
   }
   
   @Override
   public String generateToken(User user) {
      String token = UUID.randomUUID().toString();
      PasswordResetToken myToken = new PasswordResetToken(token, customerRepository.findByEmail(user.getUsername()));
      repository.save(myToken);
      return myToken.getToken();
   }
   
   
   private User userDetailsToUser(UserDetails userDetails) {
      return new User(userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
   }
   
   private Date getExpiredDate() {
      Calendar cal = Calendar.getInstance();
      cal.set(Calendar.YEAR, 1988);
      cal.set(Calendar.MONTH, Calendar.JANUARY);
      cal.set(Calendar.DAY_OF_MONTH, 1);
      return cal.getTime();
   }
   
   private boolean isExpired(PasswordResetToken token) {
      Calendar cal = Calendar.getInstance();
      return (token.getExpiryDate()
              .getTime() - cal.getTime()
              .getTime()) <= 0;
      
   }
}
