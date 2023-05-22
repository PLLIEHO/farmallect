package ru.nutsalhan87.farmallect.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import ru.nutsalhan87.farmallect.model.communication.AddIncompatibleRequest;
import ru.nutsalhan87.farmallect.model.communication.CompatibilityResponse;
import ru.nutsalhan87.farmallect.model.communication.MedicamentsCompatibilityCheck;
import ru.nutsalhan87.farmallect.model.medicament.Medicament;
import ru.nutsalhan87.farmallect.model.properties.IncompatibleIngridients;
import ru.nutsalhan87.farmallect.model.properties.Ingridient;
import ru.nutsalhan87.farmallect.repository.IncompatibleIngridientsRepository;
import ru.nutsalhan87.farmallect.repository.MedicamentRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Supplier;

@Service
public class IncompatibleIngridientsService {
    private final IncompatibleIngridientsRepository incompatibleIngridientsRepository;
    private final MedicamentRepository medicamentRepository;

    @Autowired
    public IncompatibleIngridientsService(IncompatibleIngridientsRepository incompatibleIngridientsRepository,
                                          MedicamentRepository medicamentRepository) {
        this.incompatibleIngridientsRepository = incompatibleIngridientsRepository;
        this.medicamentRepository = medicamentRepository;
    }

    @Transactional
    public CompatibilityResponse checkCompatibility(MedicamentsCompatibilityCheck medicamentsCompatibilityCheck) {
        List<IncompatibleIngridients> incompatibleIngridients = incompatibleIngridientsRepository.findAll();
        try {
            Medicament medicament1 = medicamentRepository.findById(medicamentsCompatibilityCheck.getId1()).orElseThrow();
            Medicament medicament2 = medicamentRepository.findById(medicamentsCompatibilityCheck.getId2()).orElseThrow();
            for (Ingridient ingridient1 : medicament1.getIngridients()) {
                if (medicament2.getIngridients().parallelStream().anyMatch(ingridient2 ->
                        incompatibleIngridientsRepository.existsByIngridients(
                                ingridient1.getIngridient(), ingridient2.getIngridient()))) {
                    return new CompatibilityResponse(false);
                }
            }
            return new CompatibilityResponse(true);
        } catch (NoSuchElementException neexc) {
            return  new CompatibilityResponse(true);
        }
    }

    @Transactional
    public void addIncompatible(List<IncompatibleIngridients> incompatibleIngridients) {
        incompatibleIngridients.forEach(ingridients -> {
            if (!ingridients.getIngridient1().equals(ingridients.getIngridient2())
                    && incompatibleIngridientsRepository.findOne(Example.of(ingridients)).isEmpty()) {
                incompatibleIngridientsRepository.save(ingridients);
            }
        });
    }
}
