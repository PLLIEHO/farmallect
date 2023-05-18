package ru.nutsalhan87.farmallect.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.nutsalhan87.farmallect.model.communication.*;
import ru.nutsalhan87.farmallect.model.medicament.MedicamentDTO;
import ru.nutsalhan87.farmallect.model.properties.*;
import ru.nutsalhan87.farmallect.service.*;

import java.util.List;

@RestController
@RequestMapping("/api/med")
public class MedicamentController {
    private final String secretKey;
    private final MedicamentService medicamentService;
    private final IngridientService ingridientService;
    private final IndicationService indicationService;
    private final SideEffectService sideEffectService;
    private final ContraindicationService contraindicationService;
    private final IncompatibleIngridientsService incompatibleIngridientsService;

    @Autowired
    public MedicamentController(MedicamentService medicamentService,
                                IngridientService ingridientService, IndicationService indicationService,
                                SideEffectService sideEffectService, ContraindicationService contraindicationService,
                                IncompatibleIngridientsService incompatibleIngridientsService, Environment environment) {
        this.medicamentService = medicamentService;
        this.ingridientService = ingridientService;
        this.indicationService = indicationService;
        this.sideEffectService = sideEffectService;
        this.contraindicationService = contraindicationService;
        this.incompatibleIngridientsService = incompatibleIngridientsService;
        secretKey = environment.getProperty("secret-key");
    }

    private void checkTokenOrThrow(String token) {
        if (!token.equals(secretKey)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token is not correct");
        }
    }
    @GetMapping("/medicament/{id}")
    public MedicamentDTO medicament(@PathVariable Long id) {
        return medicamentService.getMedicament(id);
    }

    @PostMapping("/medicaments")
    public MedicamentResponse medicaments(@RequestBody MedicamentRequest medicamentRequest) {
        return medicamentService.getMedicaments(medicamentRequest);
    }

    @GetMapping("/companies")
    public List<String> companies() {
        return medicamentService.getCompanies();
    }

    @GetMapping("/countries")
    public List<String> countries() {
        return medicamentService.getCountries();
    }

    @GetMapping("/forms")
    public List<String> forms() {
        return medicamentService.getForms();
    }

    @GetMapping("/ingridients")
    public List<String> ingridients() {
        return ingridientService.getIngridients().stream().map(Ingridient::getIngridient).toList();
    }

    @GetMapping("/indications")
    public List<String> indications() {
        return indicationService.getIndications().stream().map(Indication::getIndication).toList();
    }

    @GetMapping("/side-effects")
    public List<String> sideEffects() {
        return sideEffectService.getSideEffects().stream().map(SideEffect::getSideEffect).toList();
    }

    @GetMapping("/contraindications")
    public List<String> contraindications() {
        return contraindicationService.getContraindications().stream().
                map(Contraindication::getContraindication).toList();
    }

    @PostMapping("/add-medicaments")
    public void add(@RequestBody AddMedicamentsRequest addMedicamentsRequest) {
        checkTokenOrThrow(addMedicamentsRequest.getToken());
        medicamentService.addMedicaments(addMedicamentsRequest.getMedicaments());
    }

    @PostMapping("/compatibility")
    public CompatibilityResponse checkCompatibility(@RequestBody IncompatibleIngridients compatibilityRequest) {
        return incompatibleIngridientsService.checkCompatibility(compatibilityRequest);
    }

    @PostMapping("/add-incompatible")
    public void addIncompatible(@RequestBody AddIncompatibleRequest addIncompatibleRequest) {
        checkTokenOrThrow(addIncompatibleRequest.getToken());
        incompatibleIngridientsService.addIncompatible(addIncompatibleRequest.getIncompatibleIngridients());
    }
}
