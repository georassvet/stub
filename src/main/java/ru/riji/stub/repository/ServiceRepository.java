package ru.riji.stub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.riji.stub.entities.ServiceEntity;

public interface ServiceRepository extends JpaRepository<ServiceEntity,Long> {
}
