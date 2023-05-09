package ru.nutsalhan87.farmallect.model.communication;

import lombok.Getter;
import lombok.Setter;
import ru.nutsalhan87.farmallect.model.medicament.MedicamentDTO;

import java.util.ArrayList;
import java.util.List;

@Getter
public class AddMedicamentsRequest {
    private final ArrayList<MedicamentDTO> medicaments = new ArrayList<>();
    private String token;
}
