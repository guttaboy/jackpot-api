package com.ent.jackpot.repository;

import com.ent.jackpot.entity.Jackpot;
import com.ent.jackpot.entity.Match;
import com.ent.jackpot.entity.PlayerAssociation;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatchRepository extends JpaRepository<Match, Integer>, JpaSpecificationExecutor<Match> {

    @Override
    List<Match> findAll(Specification<Match> specification);

}
