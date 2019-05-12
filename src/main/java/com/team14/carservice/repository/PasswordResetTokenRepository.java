package com.team14.carservice.repository;

import com.team14.carservice.models.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Integer> {
   
   PasswordResetToken findByToken(String token);
   
   List<PasswordResetToken> findAllByCustomer_Id(Integer id);
   
   List<PasswordResetToken> getAllByCustomer_Id(Integer id);
}
