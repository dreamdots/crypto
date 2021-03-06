package com.dots.crypto.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tokens")
public class Token {

    @Id
    private String contract;

    @OneToMany
    @ToString.Exclude
    private List<Transaction> transactions = new ArrayList<>();
}
