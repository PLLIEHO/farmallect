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
@Table(name = "indications")
@Data
@NoArgsConstructor
public class Indication {
    @Id
    private String indication;
    @ManyToMany
    private List<Medicament> medicaments = new ArrayList<>();

    public Indication(String indication) {
        this.indication = indication;
    }
}
