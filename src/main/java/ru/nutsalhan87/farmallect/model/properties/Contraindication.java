package ru.nutsalhan87.farmallect.model.properties;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.nutsalhan87.farmallect.model.medicament.Medicament;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "contraindications")
@Data
@NoArgsConstructor
public class Contraindication {
    @Id
    private String contraindication;
    @ManyToMany
    private List<Medicament> medicaments = new ArrayList<>();

    public Contraindication(String contraindication) {
        this.contraindication = contraindication;
    }
}
