package ru.nutsalhan87.farmallect.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nutsalhan87.farmallect.model.communication.AddIncompatibleRequest;
import ru.nutsalhan87.farmallect.model.communication.CompatibilityResponse;
import ru.nutsalhan87.farmallect.model.properties.IncompatibleIngridients;
import ru.nutsalhan87.farmallect.repository.IncompatibleIngridientsRepository;

import java.util.List;

@Service
public class IncompatibleIngridientsService {
    private final IncompatibleIngridientsRepository incompatibleIngridientsRepository;

    @Autowired
    public IncompatibleIngridientsService(IncompatibleIngridientsRepository incompatibleIngridientsRepository) {
        this.incompatibleIngridientsRepository = incompatibleIngridientsRepository;
    }

    @Transactional
    public CompatibilityResponse checkCompatibility(IncompatibleIngridients ingridients) {
        boolean compatible = incompatibleIngridientsRepository.findOne(Example.of(ingridients)).isEmpty();
        IncompatibleIngridients swapped = new IncompatibleIngridients();
        swapped.setIngridient1(ingridients.getIngridient2());
        swapped.setIngridient2(ingridients.getIngridient1());
        compatible = compatible && incompatibleIngridientsRepository.findOne(Example.of(swapped)).isEmpty();
        return new CompatibilityResponse(compatible);
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
