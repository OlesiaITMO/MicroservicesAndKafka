package org.itmo_olesia.dto.RequestsAndResponses.Requests.Cats;

import lombok.Data;
import lombok.Setter;
import org.itmo_olesia.dto.DTO.CatDTO;

@Data
@Setter

public class CatCreateRequest {
    private CatDTO catDTO;
}
