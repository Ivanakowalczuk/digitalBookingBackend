package com.app.pi.digitalbooking.model.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tokens", schema = "0523TDPRON2C03LAED1021PT_GRUPO7")
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idToken;
    private String token;
    private Boolean expirado;
    private Boolean revocado;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "idUsuario")
    private Usuario usuario;
}