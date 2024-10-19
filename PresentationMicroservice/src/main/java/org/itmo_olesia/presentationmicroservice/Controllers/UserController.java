
package org.itmo_olesia.presentationmicroservice.Controllers;

import lombok.RequiredArgsConstructor;
import org.itmo_olesia.dto.DTO.OwnerDTO;
import org.itmo_olesia.dto.DTO.UserDTO;
import org.itmo_olesia.presentationmicroservice.BrokersAndServices.BrokerInteraction.OwnersBrokers;
import org.itmo_olesia.presentationmicroservice.BrokersAndServices.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private OwnersBrokers ownersBroker;

    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@RequestBody UserDTO userDTO) throws ExecutionException, InterruptedException, TimeoutException {
        UserDTO current = (UserDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //throw new IllegalArgumentException(current.getRole().toString() + current.getUsername().toString());
        //if (current.getRole().equals("ROLE_USER"))
          //  return ResponseEntity.badRequest().body(null);


        OwnerDTO createdOwner = new OwnerDTO();
        createdOwner.setName(userDTO.getUsername());
        createdOwner.setBirthday(userDTO.getBirthday());
        ownersBroker.create(createdOwner);
        userDTO.setOwnerId(createdOwner.getId());
        UserDTO createdUser = userService.createUser(userDTO);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }



    @GetMapping("/getUser/{username}")
    public ResponseEntity<UserDTO> getUser(@PathVariable String username) {
        var current = (UserDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDTO userDTO = userService.getByUsername(username);
        if (current.getRole().equals("ROLE_USER") && !current.getUsername().equals(username)) // админ может смотреть кого угодно
            return ResponseEntity.badRequest().body(null); // юзер лишь себя
        if (userService.getByUsername(username) == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        return ResponseEntity.ok(userDTO);
    }


    @GetMapping("/getCats/{username}")
    public ResponseEntity<List<Long>> getCatsByUsername(@PathVariable String username) {
        var current = (UserDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (current.getRole().toString().equals("ROLE_USER") && !current.getUsername().equals(username))    // пользователь смотрит только своих котов
            return ResponseEntity.badRequest().body(null);
        if (userService.getByUsername(username) == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);

        List<Long> cats = userService.getCats(username);
        if (cats == null)
            return ResponseEntity.ok(null);
        return ResponseEntity.ok(cats);
    }


    @DeleteMapping("/delete/{username}")
    public ResponseEntity<String> deleteUserByUsername(@PathVariable String username) throws ExecutionException, InterruptedException, TimeoutException {
        var current = (UserDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (userService.getByUsername(username) == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User" + username + "not found");
        if (current.getRole().toString().equals("ROLE_USER"))
            return ResponseEntity.badRequest().body(null);

        ownersBroker.delete(userService.getByUsername(username).getId());
        userService.deleteByUsername(username);
        return ResponseEntity.ok("User deleted successfully");
    }

//    @PatchMapping("/updateBirthday/{username}/{birthday}")
//    public ResponseEntity<UserDTO> updateBirthday(@PathVariable String username, @PathVariable LocalDate birthday) {
//        var current = (UserDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        if (current.getRole().toString().equals("ROLE_USER") && !current.getUsername().equals(username))    // пользователь смотрит только своих котов
//            return ResponseEntity.badRequest().body(null);
//        if (userService.getByUsername(username) == null)
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
//        UserDTO updatedUser = userService.updateBirthday(username, birthday);
//        owners.updateBirthday(username, birthday);
//        return ResponseEntity.ok(updatedUser);
//    }

//    @PatchMapping("/updateUsername/{username}/{newName}")
//    public ResponseEntity<UserDTO> updateUsername(@PathVariable String username, @PathVariable String newName) {
//        var current = (UserDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        if (current.getRole().toString().equals("ROLE_USER") && !current.getUsername().equals(username))    // пользователь смотрит только своих котов
//            return ResponseEntity.badRequest().body(null);
//        if (userService.getByUsername(username) == null)
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
//        UserDTO updatedUser = userService.updateUsername(username, newName);
//        ownerService.updateUsername(username, newName);
//        return ResponseEntity.ok(updatedUser);
//    }
}
