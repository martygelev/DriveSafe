package com.team14.carservice.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserIdentification {
   
   public static String getLoggedInUserEmail() {
      Authentication authentication = SecurityContextHolder.getContext().
              getAuthentication();
      return authentication.getName();
   }
}
