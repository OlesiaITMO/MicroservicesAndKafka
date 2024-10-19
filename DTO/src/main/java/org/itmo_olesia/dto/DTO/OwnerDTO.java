package org.itmo_olesia.dto.DTO;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class OwnerDTO {
    private Long id;
    private String name;
    private LocalDate birthday;
    private List<Long> cats;
}
