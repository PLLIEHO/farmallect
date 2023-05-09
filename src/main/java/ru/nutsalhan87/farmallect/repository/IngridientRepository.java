package ru.nutsalhan87.farmallect.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nutsalhan87.farmallect.model.properties.Ingridient;

@Repository
public interface IngridientRepository extends JpaRepository<Ingridient, String> {}
