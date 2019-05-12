package com.team14.carservice.repository;


import com.team14.carservice.models.Customer;
import com.team14.carservice.models.dto.CustomerDto;
import com.team14.carservice.models.dto.EmailAddressDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
   
   Customer findByEmail(String email);
   
   @Query(value = "SELECT new com.team14.carservice.models.dto.CustomerDto" +
           "(id, name) from Customer where deleted = false order by name")
   List<CustomerDto> getAllCustomersToDto();
   
   @Query(value = "SELECT new com.team14.carservice.models.dto.EmailAddressDto" +
           "(id, name, email, phone) from Customer where deleted = false order by name")
   List<EmailAddressDto> getAllCustomersEmails();
   
   
}
