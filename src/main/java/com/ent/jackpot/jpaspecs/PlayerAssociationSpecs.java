package com.ent.jackpot.jpaspecs;

import com.ent.jackpot.entity.PlayerAssociation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;

@Component
@Slf4j
public class PlayerAssociationSpecs {

    private final EntityManager entityManager;

    public PlayerAssociationSpecs(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    public Specification<PlayerAssociation> getPlayerAssociationByJackpotId(Integer jackpotId){
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("jackpotId"), jackpotId);
    }

    public Specification<PlayerAssociation> getPlayerAssociationByPlayerId(Integer playerId){
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("playerId"), playerId);
    }

}
