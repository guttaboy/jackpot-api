package com.ent.jackpot.jpaspecs;

import com.ent.jackpot.entity.Jackpot;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.hibernate.criterion.CriteriaQuery;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class JackpotSpecs {

    private final EntityManager entityManager;

    public JackpotSpecs(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    public Specification<Jackpot> getJackpotDetailsById(Integer jackpotId){
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("jackpotId"), jackpotId);
    }

    public List<Jackpot> getJackpotDetailsByIds(List<Integer> jackpotIds){
        var criteriaBuilder = entityManager.getCriteriaBuilder();
        var query = criteriaBuilder.createQuery(Jackpot.class);
        Root<Jackpot> root = query.from(Jackpot.class);

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(criteriaBuilder.in(root.get("jackpotId").in(jackpotIds)));

        var resulSet = entityManager.createQuery(query).getResultList();

        return resulSet;
    }

}
