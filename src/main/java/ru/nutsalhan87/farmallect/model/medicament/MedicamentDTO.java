package ru.nutsalhan87.farmallect.model.medicament;

import lombok.*;
import ru.nutsalhan87.farmallect.model.properties.Contraindication;
import ru.nutsalhan87.farmallect.model.properties.Indication;
import ru.nutsalhan87.farmallect.model.properties.Ingridient;
import ru.nutsalhan87.farmallect.model.properties.SideEffect;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MedicamentDTO {
    private String name;
    private String company;
    private String country;
    private String form;
    private List<String> ingridients = new ArrayList<>();
    private List<String> indications = new ArrayList<>();
    private List<String> sideEffects = new ArrayList<>();
    private List<String> contraindications = new ArrayList<>();
    public MedicamentDTO(Medicament med) {
        this.name = med.getName();
        this.company = med.getCompany();
        this.country = med.getCountry();
        this.form = med.getForm();
        this.ingridients = med.getIngridients().stream().map(Ingridient::getIngridient).toList();
        this.indications = med.getIndications().stream().map(Indication::getIndication).toList();
        this.sideEffects = med.getSideEffects().stream().map(SideEffect::getSideEffect).toList();
        this.contraindications = med.getContraindications().stream().map(Contraindication::getContraindication).toList();
    }
}
