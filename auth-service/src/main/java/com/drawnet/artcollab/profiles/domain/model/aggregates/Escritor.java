package com.drawnet.artcollab.profiles.domain.model.aggregates;


import com.drawnet.artcollab.iam.domain.model.aggregates.User;

import com.drawnet.artcollab.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
public class Escritor extends AuditableAbstractAggregateRoot<Escritor> {

    @Id
    @JoinColumn(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private User user;


    public Escritor(){}

    public User getUserId(){
        return user;
    }


}


