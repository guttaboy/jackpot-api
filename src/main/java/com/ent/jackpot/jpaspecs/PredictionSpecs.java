package com.ent.jackpot.jpaspecs;

import com.ent.jackpot.entity.Prediction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.criteria.Predicate;

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

    public Specification<Prediction> getPredictionDetailsByMatchIdAndPlayerId(Integer matchId, Integer playerId){
        return (root, query, criteriaBuilder) -> {
            Predicate predicatePlayerId = criteriaBuilder.equal(root.get("playerId"), playerId);
            Predicate predicateMatchId = criteriaBuilder.equal(root.get("matchId"), matchId);

            return criteriaBuilder.and(predicatePlayerId, predicateMatchId);
        };
    }

}
