package com.ent.jackpot.repository;

import com.ent.jackpot.entity.Prediction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PredictionRepository extends JpaRepository<Prediction, Integer>, JpaSpecificationExecutor<Prediction> {
}
