package ru.nutsalhan87.farmallect.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nutsalhan87.farmallect.model.properties.SideEffect;

@Repository
public interface SideEffectRepository extends JpaRepository<SideEffect, String> {}
