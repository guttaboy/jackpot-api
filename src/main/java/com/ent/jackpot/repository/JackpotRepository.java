package com.ent.jackpot.repository;

import com.ent.jackpot.entity.Jackpot;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JackpotRepository extends JpaRepository<Jackpot, Integer>, JpaSpecificationExecutor<Jackpot> {

    @Override
    Optional<Jackpot> findOne(Specification<Jackpot> specification);

}
