package ru.nutsalhan87.farmallect.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.nutsalhan87.farmallect.model.medicament.Medicament;

import java.util.List;

@Repository
public interface MedicamentRepository extends JpaRepository<Medicament, Long> {
    @Query("select distinct med.company from Medicament med")
    List<String> getCompanies();

    @Query("select distinct med.country from Medicament med")
    List<String> getCountries();

    @Query("select distinct med.form from Medicament med")
    List<String> getForms();
}
