package com.ent.jackpot.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="MTCH")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Match {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name="MTCH_ID", nullable = false)
    private Integer matchId;

    @Column(name="MTCH_NUMBER", nullable = false)
    private String matchNumber;

    @Column(name="MTCH_NAME", nullable = false)
    private String matchName;

    @Column(name="JKPT_ID", nullable = false)
    private Integer jackpotId;

    @Column(name="TEAM_1", nullable = false)
    private String team1;

    @Column(name="TEAM_2", nullable = false)
    private String team2;

}
