package com.ent.jackpot.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name="PLAYER")
public class Player {

    @Column(name = "PLR_ACT_ID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer playerActivationId;

//    @Column(name = "JKPT_ID")
//    private Integer jackpotId;

//    @Column(name = "PLR_CD")
//    private String playerCode;

    @Column(name = "PLR_FIRST_NM")
    private String playerFirstName;

    @Column(name = "PLR_LAST_NM")
    private String playerLastName;

    @Column(name = "USR_NM")
    private String userName;

    @Column(name = "PSWRD")
    private String password;

}
