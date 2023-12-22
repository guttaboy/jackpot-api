package com.ent.jackpot.jpaspecs;

import com.ent.jackpot.entity.Points;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;

@Component
@Slf4j
public class PointsSpecs {

    private final EntityManager entityManager;
    public PointsSpecs(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    public Specification<Points> getPointsByMatchId(Integer matchId){
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("matchId"), matchId);
    }

}
