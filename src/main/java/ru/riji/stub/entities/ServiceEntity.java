package ru.riji.stub.entities;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class ServiceEntity {
    @Id
    private Long id;
}
