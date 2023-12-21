package com.ent.jackpot.jpaspecs;

import com.ent.jackpot.entity.Jackpot;
import com.ent.jackpot.entity.Player;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class PlayerSpecs {

    private final EntityManager entityManager;
    public PlayerSpecs(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    public Specification<Player>  getPlayerDetailsByUserName(String userName){
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("userName"), userName);
    }

    public List<Player> getPlayerDetailsByPlayerIds(List<Integer> playerIds){
        var criteriaBuilder = entityManager.getCriteriaBuilder();
        var query = criteriaBuilder.createQuery(Player.class);
        Root<Player> root = query.from(Player.class);

        // Create a Predicate for the "playerId" field using the "in" condition
        Predicate playerIdPredicate = root.get("playerActivationId").in(playerIds);

        // Add the Predicate to the list of predicates
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(playerIdPredicate);

        // Add the WHERE condition to the CriteriaQuery
        query.where(predicates.toArray(new Predicate[0]));

        var resulSet = entityManager.createQuery(query).getResultList();

        return resulSet;
    }
}
