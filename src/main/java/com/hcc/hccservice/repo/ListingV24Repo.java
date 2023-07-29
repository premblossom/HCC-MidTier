package com.hcc.hccservice.repo;

import com.hcc.hccservice.entity.ListingV24;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ListingV24Repo extends JpaRepository<ListingV24,Long> {
}
