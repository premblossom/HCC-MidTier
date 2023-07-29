package com.hcc.hccservice.repo;

import com.hcc.hccservice.entity.SortByIcd10Code;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SortByIcd10CodeRepo extends JpaRepository<SortByIcd10Code,String > {
}
