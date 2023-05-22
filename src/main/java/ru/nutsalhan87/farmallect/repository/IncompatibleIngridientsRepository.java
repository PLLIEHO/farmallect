package ru.nutsalhan87.farmallect.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.nutsalhan87.farmallect.model.properties.IncompatibleIngridients;
import ru.nutsalhan87.farmallect.model.properties.Ingridient;

@Repository
public interface IncompatibleIngridientsRepository extends JpaRepository<IncompatibleIngridients, Long> {
    @Query("select case when count(i) > 0 then true else false end from IncompatibleIngridients i where (i.ingridient1 = ?1 and i.ingridient2 = ?2) or (i.ingridient1 = ?2 and i.ingridient2 = ?1) ")
    boolean existsByIngridients(String ingridient1, String ingridient2);
}