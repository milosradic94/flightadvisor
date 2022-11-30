package com.losmilos.flightadvisor.model.persistance;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.losmilos.flightadvisor.enumeration.Role;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
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
    @JsonBackReference
    private List<UserEntity> users;
}
