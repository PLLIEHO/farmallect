package ru.nutsalhan87.farmallect.model.properties;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "incompatible_ingridients")
@Data
public class IncompatibleIngridients {
    @Id
    @GeneratedValue
    private Long id;
    private String ingridient1;
    private String ingridient2;
}
