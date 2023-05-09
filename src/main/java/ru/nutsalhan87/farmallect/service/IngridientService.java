package ru.nutsalhan87.farmallect.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nutsalhan87.farmallect.model.properties.Indication;
import ru.nutsalhan87.farmallect.model.properties.Ingridient;
import ru.nutsalhan87.farmallect.repository.IngridientRepository;

import java.util.List;

@Service
public class IngridientService {
    private final IngridientRepository ingridientRepository;

    @Autowired
    public IngridientService(IngridientRepository ingridientRepository) {
        this.ingridientRepository = ingridientRepository;
    }

    @Transactional
    public void addIngridients(List<Ingridient> ingridients) {
        this.ingridientRepository.saveAll(ingridients);
    }

    @Transactional
    public List<Ingridient> getIngridients() {
        return this.ingridientRepository.findAll();
    }

    @Transactional
    public Ingridient getById(String id) {
        return ingridientRepository.findById(id).orElse(new Ingridient(id));
    }
}
