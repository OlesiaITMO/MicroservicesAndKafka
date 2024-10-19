package org.itmo_olesia.presentationmicroservice.Controllers;


import lombok.RequiredArgsConstructor;
import org.itmo_olesia.dto.DTO.CatDTO;
import org.itmo_olesia.presentationmicroservice.BrokersAndServices.BrokerInteraction.CatsBrokers;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

@RestController
@RequestMapping("/cats")
@RequiredArgsConstructor
public class CatController {

    final CatsBrokers catsBrokers;


    @PostMapping("/create")
    public ResponseEntity<CatDTO> createCat(@RequestBody CatDTO catDTO) throws ExecutionException, InterruptedException, TimeoutException {
        CatDTO createdCat = catsBrokers.create(catDTO);
        return new ResponseEntity<>(createdCat, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteCat(@PathVariable Long id) throws ExecutionException, InterruptedException, TimeoutException {
        catsBrokers.delete(id);
        return ResponseEntity.ok("Кот успешно удален");
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<CatDTO> getById(@PathVariable Long id) throws ExecutionException, InterruptedException, TimeoutException {
        Optional<CatDTO> optionalCatDTO = catsBrokers.getById(id);
        CatDTO catDTO = optionalCatDTO.orElseThrow();
        if (catDTO != null) {
            return ResponseEntity.ok(catDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
//    @PatchMapping("/updateName/{id}/{name}")
//    public ResponseEntity<CatDTO> updateName(@PathVariable Long id, @PathVariable String name) {
//        CatDTO updatedCat = CatsBrokers.updateName(id, name);
//        return ResponseEntity.ok(updatedCat);
//    }

//    @PatchMapping("/updateBirthday/{id}/{birthday}")
//    public ResponseEntity<CatDTO> updateBirthday(@PathVariable Long id, @PathVariable LocalDate birthday) {
//        CatDTO updatedCat = CatsBrokers.updateBirthday(id, birthday);
//        return ResponseEntity.ok(updatedCat);
//    }
//
//    @PatchMapping("/updateColor/{id}/{color}")
//    public ResponseEntity<CatDTO> updateColor(@PathVariable Long id, @PathVariable String color) {
//        CatDTO updatedCat = CatsBrokers.updateColor(id, color);
//        return ResponseEntity.ok(updatedCat);
//    }

//    @PatchMapping("/updateBreed/{id}/{breed}")
//    public ResponseEntity<CatDTO> updateBreed(@PathVariable Long id, @PathVariable String breed) {
//        CatDTO updatedCat = CatsBrokers.updateBreed(id, breed);
//        return ResponseEntity.ok(updatedCat);
//    }


//    @GetMapping("/getByName/{name}")
//    public ResponseEntity<CatDTO> getByName(@PathVariable String name) {
//        CatDTO catDTO = catsBrokers.getCatByName(name);
//        if (catDTO != null) {
//            return ResponseEntity.ok(catDTO);
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }

//    @GetMapping("/getByBirthday/{birthday}")
//    public ResponseEntity<List<CatDTO>> getCatsByBirthday(@PathVariable LocalDate birthday) {
//        List<CatDTO> catsDTO = catsBrokers.getFilteredByBirthday(birthday);
//        if (!catsDTO.isEmpty()) {
//            return ResponseEntity.ok(catsDTO);
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }

//    @GetMapping("/getByColor/{color}")
//    public ResponseEntity<List<CatDTO>> getCatsByColor(@PathVariable String color) {
//        List<CatDTO> catsDTO = catsBrokers.getFilteredByColor(color);
//        if (!catsDTO.isEmpty()) {
//            return ResponseEntity.ok(catsDTO);
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }

//    @GetMapping("/getByBreed/{breed}")
//    public ResponseEntity<List<CatDTO>> getCatsByBreed(@PathVariable String breed) {
//        List<CatDTO> catsDTO = catsBrokers.getFilteredByColor(breed);
//        if (!catsDTO.isEmpty()) {
//            return ResponseEntity.ok(catsDTO);
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }
}
