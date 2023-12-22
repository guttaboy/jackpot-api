package com.ent.jackpot.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="POINTS")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Points {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name="PTS_ID", nullable = false)
    private Integer pointsId;

    @Column(name="MTCH_ID", nullable = false)
    private Integer matchId;

    @Column(name="PTS")
    private String points;

    @Column(name="MAX_PTS", nullable = false)
    private Integer maximumPoints;

}
