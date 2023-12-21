package com.ent.jackpot.jpaspecs;

import com.ent.jackpot.entity.Match;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;

@Component
@Slf4j
public class MatchSpecs {

    private final EntityManager entityManager;

    public MatchSpecs(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    public Specification<Match> getMatchDetailsByJackpotId(Integer jackpotId){
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("jackpotId"), jackpotId);
    }

}
