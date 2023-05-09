package ru.nutsalhan87.farmallect.model.communication;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.nutsalhan87.farmallect.model.medicament.MedicamentDTO;

import java.util.List;

@AllArgsConstructor
@Getter
public class MedicamentResponse {
    private List<MedicamentDTO> medicaments;
    private int pages;
}
