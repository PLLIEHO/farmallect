package ru.nutsalhan87.farmallect.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nutsalhan87.farmallect.model.properties.Contraindication;
import ru.nutsalhan87.farmallect.repository.ContraindicationRepository;

import java.util.List;

@Service
public class ContraindicationService {
    private final ContraindicationRepository contraindicationRepository;

    public ContraindicationService(ContraindicationRepository contraindicationRepository) {
        this.contraindicationRepository = contraindicationRepository;
    }

    @Transactional
    public void addContraindications(List<Contraindication> contraindications) {
        contraindicationRepository.saveAll(contraindications);
    }

    @Transactional
    public List<Contraindication> getContraindications() {
        return this.contraindicationRepository.findAll();
    }

    @Transactional
    public Contraindication getById(String id) {
        return contraindicationRepository.findById(id).orElse(new Contraindication(id));
    }
}
