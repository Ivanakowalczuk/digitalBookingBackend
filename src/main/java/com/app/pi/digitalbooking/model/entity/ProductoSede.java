package com.app.pi.digitalbooking.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "sedesproductos", schema = "0523TDPRON2C03LAED1021PT_GRUPO7")
public class ProductoSede {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idSedeProducto;
    private Integer idProducto;
    private Integer idSede;
    private Boolean indicadorHabilitado;

    @OneToOne
    @JoinColumn(name = "idSede", referencedColumnName = "idSede", insertable = false, updatable = false)
    private Sede sede;
}
