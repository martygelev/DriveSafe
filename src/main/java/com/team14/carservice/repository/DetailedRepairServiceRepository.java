package com.team14.carservice.repository;

import com.team14.carservice.models.DetailedRepairService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetailedRepairServiceRepository extends JpaRepository<DetailedRepairService, Integer> {
}
