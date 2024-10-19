package org.itmo_olesia.dto.RequestsAndResponses.Requests.Users;

import lombok.Data;
import org.itmo_olesia.dto.DTO.UserDTO;

@Data
public class UserCreateRequest {
    private UserDTO userDTO;
}
