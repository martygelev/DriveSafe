package com.team14.carservice.repository;

import com.team14.carservice.models.RepairService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepairServiceRepository extends JpaRepository<RepairService, Integer> {
   Boolean existsByIdAndDeletedFalse(Integer id);
   
   Boolean existsByNameAndDeletedFalse(String name);
   
   List<RepairService> findAllByDeletedFalseOrderByName();
   
   RepairService findByIdAndDeletedFalse(Integer id);
   
}
