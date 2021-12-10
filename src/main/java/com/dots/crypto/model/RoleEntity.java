package com.dots.crypto.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "role")
public class RoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "role_seq_gen")
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private APIRole name;
}
