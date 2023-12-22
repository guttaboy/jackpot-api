package com.ent.jackpot.repository;

import com.ent.jackpot.entity.PlayerAssociation;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlayerAssociationRepository extends JpaRepository<PlayerAssociation, Integer>, JpaSpecificationExecutor<PlayerAssociation> {

    @Override
    Optional<PlayerAssociation> findOne(Specification<PlayerAssociation> specification);

    @Override
    List<PlayerAssociation> findAll(Specification<PlayerAssociation> specification);

}
