package ru.nutsalhan87.farmallect.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nutsalhan87.farmallect.model.properties.Indication;
import ru.nutsalhan87.farmallect.model.properties.Ingridient;
import ru.nutsalhan87.farmallect.repository.IndicationRepository;

import java.util.List;

@Service
public class IndicationService {
    private final IndicationRepository indicationRepository;

    @Autowired
    public IndicationService(IndicationRepository indicationRepository) {
        this.indicationRepository = indicationRepository;
    }

    @Transactional
    public void addIndications(List<Indication> indications) {
        this.indicationRepository.saveAll(indications);
    }

    @Transactional
    public List<Indication> getIndications() {
        return this.indicationRepository.findAll();
    }

    @Transactional
    public Indication getById(String id) {
        return indicationRepository.findById(id).orElse(new Indication(id));
    }
}
