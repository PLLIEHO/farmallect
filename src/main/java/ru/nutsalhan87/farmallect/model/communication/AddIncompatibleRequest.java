package ru.nutsalhan87.farmallect.model.communication;

import lombok.Getter;
import ru.nutsalhan87.farmallect.model.properties.IncompatibleIngridients;

import java.util.List;

@Getter
public class AddIncompatibleRequest {
    private List<IncompatibleIngridients> incompatibleIngridients;
    private String token;
}
