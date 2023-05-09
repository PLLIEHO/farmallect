package ru.nutsalhan87.farmallect.model.medicament;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.nutsalhan87.farmallect.model.properties.Contraindication;
import ru.nutsalhan87.farmallect.model.properties.Indication;
import ru.nutsalhan87.farmallect.model.properties.Ingridient;
import ru.nutsalhan87.farmallect.model.properties.SideEffect;
import ru.nutsalhan87.farmallect.service.ContraindicationService;
import ru.nutsalhan87.farmallect.service.IndicationService;
import ru.nutsalhan87.farmallect.service.IngridientService;
import ru.nutsalhan87.farmallect.service.SideEffectService;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "medicaments")
@Data
@NoArgsConstructor
public class Medicament {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String company;
    private String country;
    private String form;
    @ManyToMany(cascade = {CascadeType.ALL})
    private List<Ingridient> ingridients = new ArrayList<>();
    @ManyToMany(cascade = {CascadeType.ALL})
    private List<Indication> indications = new ArrayList<>();
    @ManyToMany(cascade = {CascadeType.ALL})
    private List<SideEffect> sideEffects = new ArrayList<>();
    @ManyToMany(cascade = {CascadeType.ALL})
    private List<Contraindication> contraindications = new ArrayList<>();

    public Medicament(MedicamentDTO med,
                      IngridientService ingridientService, IndicationService indicationService,
                      SideEffectService sideEffectService, ContraindicationService contraindicationService) {
        this.name = med.getName();
        this.company = med.getCompany();
        this.form = med.getForm();
        this.country = med.getCountry();
        this.ingridients = med.getIngridients().stream().map(ingridientName -> {
            Ingridient ingridient = ingridientService.getById(ingridientName);
            ingridient.getMedicaments().add(this);
            return ingridient;
        }).toList();
        this.indications = med.getIndications().stream().map(indicationName -> {
            Indication indication = indicationService.getById(indicationName);
            indication.getMedicaments().add(this);
            return indication;
        }).toList();
        this.sideEffects = med.getSideEffects().stream().map(sideEffectName -> {
            SideEffect sideEffect = sideEffectService.getById(sideEffectName);
            sideEffect.getMedicaments().add(this);
            return sideEffect;
        }).toList();
        this.contraindications = med.getContraindications().stream().map(contraindicationName -> {
            Contraindication contraindication = contraindicationService.getById(contraindicationName);
            contraindication.getMedicaments().add(this);
            return contraindication;
        }).toList();
    }
}

