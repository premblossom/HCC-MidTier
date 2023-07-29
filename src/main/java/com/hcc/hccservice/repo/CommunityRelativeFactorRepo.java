package com.hcc.hccservice.repo;

import com.hcc.hccservice.entity.CommunityRelativeFactor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommunityRelativeFactorRepo extends JpaRepository<CommunityRelativeFactor,Long> {
    Optional<List<CommunityRelativeFactor>> findByCommunityAndGender(String community, String gender);
}
