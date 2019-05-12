package com.team14.carservice.service.common;

import org.springframework.security.core.userdetails.User;

public interface PasswordResetTokenService {
   
   void disablePreviousTokensForUser(Integer id);
   
   String validatePasswordResetToken(String username, String token);
   
   String generateToken(User user);
}
