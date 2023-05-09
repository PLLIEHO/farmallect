package ru.nutsalhan87.farmallect.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nutsalhan87.farmallect.model.properties.Indication;
import ru.nutsalhan87.farmallect.model.properties.SideEffect;
import ru.nutsalhan87.farmallect.repository.ContraindicationRepository;
import ru.nutsalhan87.farmallect.repository.SideEffectRepository;

import java.util.List;

@Service
public class SideEffectService {
    private final SideEffectRepository sideEffectRepository;

    @Autowired
    public SideEffectService(SideEffectRepository sideEffectRepository) {
        this.sideEffectRepository = sideEffectRepository;
    }

    @Transactional
    public void addSideEffects(List<SideEffect> sideEffects) {
        this.sideEffectRepository.saveAll(sideEffects);
    }

    @Transactional
    public List<SideEffect> getSideEffects() {
        return this.sideEffectRepository.findAll();
    }

    @Transactional
    public SideEffect getById(String id) {
        return sideEffectRepository.findById(id).orElse(new SideEffect(id));
    }
}
