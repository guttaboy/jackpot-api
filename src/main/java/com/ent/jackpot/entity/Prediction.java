package com.ent.jackpot.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="PREDICTION")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Prediction {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name="PREDICTION_ID", nullable = false)
    private Integer predictionId;

    @Column(name="JKPT_ID", nullable = false)
    private Integer jackpotId;

    @Column(name="PLR_ACT_ID", nullable = false)
    private Integer playerId;

    @Column(name="MTCH_ID", nullable = false)
    private Integer matchId;

    @Column(name="PREDICTED_TEAM", nullable = false)
    private String predictedTeam;

}
