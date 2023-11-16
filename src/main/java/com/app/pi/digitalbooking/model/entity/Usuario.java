package com.app.pi.digitalbooking.model.entity;

import com.app.pi.digitalbooking.model.enums.Role;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "usuarios", schema = "0523TDPRON2C03LAED1021PT_GRUPO7")
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idUsuario;
    private String usuario;
    private String contrasenia;
    private Boolean verificado;
    private Boolean indicadorHabilitado;
    private String tokenValidacionCorreo;
    private LocalDateTime fechaExpiracionToken;

    @OneToOne
    @JoinColumn(name = "idPersona", referencedColumnName = "idPersona")
    private Persona persona;

    @OneToOne
    @JoinColumn(name = "idRol", referencedColumnName = "idRol")
    private Rol rol;


    @OneToMany(mappedBy = "usuario", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Token> token;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //SimpleGrantedAuthority authority = new SimpleGrantedAuthority(rol.getNombre());
        //return Collections.singletonList(authority);
        List<GrantedAuthority> list = new ArrayList<>();
        list.add(new SimpleGrantedAuthority(rol.getCodigo()));
        return list;
    }

    @Override
    public String getPassword() {
        return contrasenia;
    }

    @Override
    public String getUsername() {
        return usuario;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return indicadorHabilitado;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return verificado;
    }


}
