package com.losmilos.flightadvisor.model.persistance;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "user", uniqueConstraints = {
        @UniqueConstraint(columnNames = "username")
})
@Getter @Setter
public class UserEntity  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private RoleEntity role;
}