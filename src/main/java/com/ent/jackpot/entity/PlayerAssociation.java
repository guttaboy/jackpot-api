package com.ent.jackpot.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="PLAYER_ASCN")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlayerAssociation {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name="PLR_ASCN_ID", nullable = false)
    private Integer playerAssociationId;

    @Column(name="PLR_ACT_ID", nullable = false)
    private Integer playerId;

    @Column(name="JKPT_ID", nullable = false)
    private Integer jackpotId;
}
