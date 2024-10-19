package org.itmo_olesia.dto.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.itmo_olesia.dto.Entities.Color;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CatDTO {
    private Long id;
    private String name;
    private LocalDate birthday;
    private String breed;
    private Color color;
    private Long ownerId;
    private List<Long> friends = new ArrayList<>();
}
