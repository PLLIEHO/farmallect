package ru.nutsalhan87.farmallect.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.nutsalhan87.farmallect.model.communication.*;
import ru.nutsalhan87.farmallect.model.properties.*;
import ru.nutsalhan87.farmallect.service.*;

import java.util.List;

@RestController
@RequestMapping("/api/med")
public class MedicamentController {
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
                                IncompatibleIngridientsService incompatibleIngridientsService) {
        this.medicamentService = medicamentService;
        this.ingridientService = ingridientService;
        this.indicationService = indicationService;
        this.sideEffectService = sideEffectService;
        this.contraindicationService = contraindicationService;
        this.incompatibleIngridientsService = incompatibleIngridientsService;
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
        if (!addMedicamentsRequest.getToken().equals("super_secret_key")) { // TODO: сделать чтение токена с базы данных
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token is not correct");
        }
        medicamentService.addMedicaments(addMedicamentsRequest.getMedicaments());
    }

    @PostMapping("/compatibility")
    public CompatibilityResponse checkCompatibility(@RequestBody IncompatibleIngridients compatibilityRequest) {
        return incompatibleIngridientsService.checkCompatibility(compatibilityRequest);
    }

    @PostMapping("/add-incompatible")
    public void addIncompatible(@RequestBody AddIncompatibleRequest addIncompatibleRequest) {
        if (!addIncompatibleRequest.getToken().equals("super_secret_key")) { // TODO: сделать чтение токена с базы данных
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token is not correct");
        }
        incompatibleIngridientsService.addIncompatible(addIncompatibleRequest.getIncompatibleIngridients());
    }
}
