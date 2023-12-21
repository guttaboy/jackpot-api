package com.ent.jackpot.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="JKPT")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Jackpot {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name="JKPT_ID", nullable = false)
    private Integer jackpotId;

    @Column(name="JKPT_NM", nullable = false)
    private String jackpotName;

    @Column(name="JKPT_MNY", nullable = false)
    private String jackpotMoney;

}
