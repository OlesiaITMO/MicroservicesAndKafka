package org.itmo_olesia.dto.RequestsAndResponses.Responses.Cats;

import lombok.Data;
import org.itmo_olesia.dto.DTO.CatDTO;

@Data
public class CatGetByIdResponse {
    private CatDTO catDTO;
}
