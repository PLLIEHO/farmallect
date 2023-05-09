package ru.nutsalhan87.farmallect.model.properties;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import ru.nutsalhan87.farmallect.model.medicament.Medicament;
import ru.nutsalhan87.farmallect.service.IngridientService;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ingridients")
@Data
@NoArgsConstructor
public class Ingridient {
    @Id
    private String ingridient;
    @ManyToMany
    private List<Medicament> medicaments = new ArrayList<>();

    public Ingridient(String ingridient) {
        this.ingridient = ingridient;
    }
}
