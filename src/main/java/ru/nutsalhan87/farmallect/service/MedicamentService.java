package ru.nutsalhan87.farmallect.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import ru.nutsalhan87.farmallect.model.communication.MedicamentResponse;
import ru.nutsalhan87.farmallect.model.medicament.Medicament;
import ru.nutsalhan87.farmallect.model.medicament.MedicamentDTO;
import ru.nutsalhan87.farmallect.model.communication.MedicamentRequest;
import ru.nutsalhan87.farmallect.repository.MedicamentRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
public class MedicamentService {
    private final MedicamentRepository medicamentRepository;
    private final IngridientService ingridientService;
    private final IndicationService indicationService;
    private final SideEffectService sideEffectService;
    private final ContraindicationService contraindicationService;

    @Autowired
    public MedicamentService(MedicamentRepository medicamentRepository,
                             IngridientService ingridientService, IndicationService indicationService,
                             SideEffectService sideEffectService, ContraindicationService contraindicationService) {
        this.medicamentRepository = medicamentRepository;
        this.ingridientService = ingridientService;
        this.indicationService = indicationService;
        this.sideEffectService = sideEffectService;
        this.contraindicationService = contraindicationService;
    }

    @Transactional
    public MedicamentDTO getMedicament(Long id) {
        return new MedicamentDTO(medicamentRepository.findById(id).
                orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)));
    }

    @Transactional
    public MedicamentResponse getMedicaments(MedicamentRequest medicamentRequest) {
        List<MedicamentDTO> medicaments = medicamentRepository.findAll(Sort.by("name")).stream().
                map(MedicamentDTO::new).toList();
        medicaments = medicaments.stream().filter(medicament -> {
            boolean passed = true;
            if (!medicamentRequest.getName().equals("")) {
                passed = medicament.getName().contains(medicamentRequest.getName());
            }

            if (passed && !medicamentRequest.getCompany().isEmpty()) {
                passed = medicamentRequest.getCompany().contains(medicament.getCompany());
            }
            if (passed && !medicamentRequest.getCountry().isEmpty()) {
                passed = medicamentRequest.getCountry().contains(medicament.getCountry());
            }
            if (passed && !medicamentRequest.getForm().isEmpty()) {
                passed = medicamentRequest.getForm().contains(medicament.getForm());
            }

            if (passed && !medicamentRequest.getIngridients().isEmpty()) {
                passed = medicamentRequest.isIngridientsBoth() ?
                        new HashSet<>(medicament.getIngridients()).containsAll(medicamentRequest.getIngridients())
                        : medicament.getIngridients().stream().
                        anyMatch(ingridient -> medicamentRequest.getIngridients().contains(ingridient));
            }
            if (passed && !medicamentRequest.getIndications().isEmpty()) {
                passed = medicamentRequest.isIndicationsBoth() ?
                        new HashSet<>(medicament.getIndications()).containsAll(medicamentRequest.getIndications())
                        : medicament.getIndications().stream().
                        anyMatch(indication -> medicamentRequest.getIndications().contains(indication));
            }
            if (passed && !medicamentRequest.getSideEffects().isEmpty()) {
                passed = medicamentRequest.isSideEffectsBoth() ?
                        new HashSet<>(medicament.getSideEffects()).containsAll(medicamentRequest.getSideEffects())
                        : medicament.getSideEffects().stream().
                        anyMatch(sideEffect -> medicamentRequest.getSideEffects().contains(sideEffect));
            }
            if (passed && !medicamentRequest.getContraindications().isEmpty()) {
                passed = medicamentRequest.isContraindicationsBoth() ?
                        new HashSet<>(medicament.getContraindications()).containsAll(medicamentRequest.getContraindications())
                        : medicament.getContraindications().stream().
                        anyMatch(contraindication -> medicamentRequest.getContraindications().contains(contraindication));
            }

            return passed;
        }).toList();

        int pages = medicaments.size() % medicamentRequest.getSize() == 0 ? medicaments.size() / medicamentRequest.getSize()
                : medicaments.size() / medicamentRequest.getSize() + 1;

        if (pages < medicamentRequest.getPage()) {
            medicaments = new ArrayList<>();
        } else if (pages == medicamentRequest.getPage()) {
            medicaments = medicaments.subList((medicamentRequest.getPage() - 1) * medicamentRequest.getSize(), medicaments.size());
        } else {
            medicaments = medicaments.subList((medicamentRequest.getPage() - 1) * medicamentRequest.getSize(),
                    medicamentRequest.getPage() * medicamentRequest.getSize());
        }

        return new MedicamentResponse(medicaments, pages);
    }

    @Transactional
    public void addMedicaments(List<MedicamentDTO> medicamentDTOS) {
        medicamentDTOS.forEach(med -> {
            Medicament medicament = new Medicament(med,
                    ingridientService, indicationService, sideEffectService, contraindicationService);
            medicamentRepository.save(medicament);
            ingridientService.addIngridients(medicament.getIngridients());
            indicationService.addIndications(medicament.getIndications());
            sideEffectService.addSideEffects(medicament.getSideEffects());
            contraindicationService.addContraindications(medicament.getContraindications());
        });
    }

    @Transactional
    public List<String> getCompanies() {
        return medicamentRepository.getCompanies();
    }

    @Transactional
    public List<String> getCountries() {
        return medicamentRepository.getCountries();
    }

    @Transactional
    public List<String> getForms() {
        return medicamentRepository.getForms();
    }
}
