package org.itmo_olesia.dto.RequestsAndResponses.Responses.User;

import lombok.Data;
import org.itmo_olesia.dto.DTO.UserDTO;

@Data
public class UserGetByIdResponse {
    private UserDTO userDTO;
}
