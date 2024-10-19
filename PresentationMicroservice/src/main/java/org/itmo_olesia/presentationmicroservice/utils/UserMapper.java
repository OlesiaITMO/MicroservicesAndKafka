package org.itmo_olesia.presentationmicroservice.utils;


import org.itmo_olesia.dto.DTO.UserDTO;
import org.itmo_olesia.presentationmicroservice.jpa.User;

public class UserMapper {
    public static UserDTO UserMapping(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setPassword(user.getPassword());
        userDTO.setBirthday(user.getBirthday());
        userDTO.setRole(user.getRole().toString());

        if (user.getOwner() != null)
            userDTO.setOwnerId(user.getOwner().getId());
        return userDTO;
    }
}
