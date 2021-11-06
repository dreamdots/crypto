package com.dots.crypto.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "subscriptions")
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "subscriptions_seq_gen")
    private Long id;

    @Column(nullable = false)
    private Long threshold;

    @OneToOne
    private Token token;

    @ManyToOne
    private User user;
}
