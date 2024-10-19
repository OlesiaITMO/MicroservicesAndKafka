package org.itmo_olesia.presentationmicroservice.Controllers;

import lombok.RequiredArgsConstructor;
import org.itmo_olesia.dto.DTO.OwnerDTO;
import org.itmo_olesia.presentationmicroservice.BrokersAndServices.BrokerInteraction.OwnersBrokers;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;


@RequestMapping("/owners")
@RestController
@RequiredArgsConstructor
public class OwnerController {

    final OwnersBrokers ownersBrokers;


    @PostMapping("/create")
    public ResponseEntity<OwnerDTO> createOwner(@RequestBody OwnerDTO ownerDTO) throws ExecutionException, InterruptedException, TimeoutException {
        OwnerDTO createdOwner = ownersBrokers.create(ownerDTO);
        return new ResponseEntity<>(createdOwner, HttpStatus.CREATED);
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteCat(@PathVariable Long id) throws ExecutionException, InterruptedException, TimeoutException {
        ownersBrokers.delete(id);
        return ResponseEntity.ok("Cat successfully deleted");
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<OwnerDTO> getById(@PathVariable Long id) throws ExecutionException, InterruptedException, TimeoutException {
        Optional<OwnerDTO> optionalOwnerDTO = ownersBrokers.getById(id);
        OwnerDTO ownerDTO = optionalOwnerDTO.orElseThrow();
        return ResponseEntity.ok().body(ownerDTO);
    }




//    @GetMapping("/getByName/{name}")
//    public ResponseEntity<OwnerDTO> getByName(@PathVariable String name) {
//        OwnerDTO OwnerDTO = ownerService.getByName(name);
//        if (OwnerDTO != null) {
//            return ResponseEntity.ok(OwnerDTO);
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }


//    @GetMapping("/user/getByBirthday/{birthday}")
//    public ResponseEntity<List<OwnerDTO>> getOwnersByBirthday(@PathVariable LocalDate birthday) {
//        List<OwnerDTO> OwnersDTO = ownerService.getFilteredByBirthday(birthday);
//        if (!OwnersDTO.isEmpty()) {
//            return ResponseEntity.ok(OwnersDTO);
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }

//    @GetMapping("/getOwnerCats/{id}")
//    public ResponseEntity<List<Long>> getOwnerCats(@PathVariable Long id) {
//        List<Long> cats = ownerService.getOwnerCats(id);
//        if (!cats.isEmpty()) {
//            return ResponseEntity.ok(cats);
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }

//    @PatchMapping("/updateName/{id}/{name}")
//    public ResponseEntity<OwnerDTO> updateNameById(@PathVariable Long id, @PathVariable String name) {
//        OwnerDTO updatedOwner = ownerService.updateNameById(id, name);
//        return ResponseEntity.ok(updatedOwner);
//    }

//    @PatchMapping("/updateBirthday/{id}/{birthday}")
//    public ResponseEntity<OwnerDTO> updateBirthdayById(@PathVariable Long id, @PathVariable LocalDate birthday) {
//        OwnerDTO updatedOwner = ownerService.updateBirthdayById(id, birthday);
//        return ResponseEntity.ok(updatedOwner);
//    }

}
