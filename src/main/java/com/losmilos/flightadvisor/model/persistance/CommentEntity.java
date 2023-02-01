package com.losmilos.flightadvisor.model.persistance;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "comment")
@Getter @Setter
@Audited
public class CommentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String description;

    @ManyToOne
    @JoinColumn(name = "city_id", nullable = false)
    @JsonBackReference
    @NotAudited
    private CityEntity city;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonManagedReference
    @NotAudited
    private UserEntity user;

    @Column
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Column
    private Boolean inappropriate;
}
