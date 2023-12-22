package com.ent.jackpot.jpaspecs;

import com.ent.jackpot.entity.Prediction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;

@Component
@Slf4j
public class PredictionSpecs {

    private final EntityManager entityManager;

    public PredictionSpecs(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    public Specification<Prediction> getPredictionDetailsByMatchId(Integer matchId){
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("matchId"), matchId);
    }

}
