package com.losmilos.flightadvisor.model.persistance;

import com.losmilos.flightadvisor.enumeration.Role;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "role")
@Getter @Setter
public class RoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy="role")
    private List<UserEntity> users;
}
