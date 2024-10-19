package org.itmo_olesia.catmicroservice.Services;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;


import org.itmo_olesia.catmicroservice.jpa.Cat;
import org.itmo_olesia.dto.DTO.CatDTO;
import org.itmo_olesia.dto.Entities.Color;
import org.itmo_olesia.catmicroservice.utils.CatMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.itmo_olesia.catmicroservice.Repositories.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CatService {
    @Autowired
    private ICatRepository catRepository;
    @Autowired
    private IOwnerRepository userRepository;
    private final CatMapper catMapper= new CatMapper();

    @Async
    public void saveCatAsync(Cat cat) {
        catRepository.save(cat);
    }

    public CatDTO createCat(String name, LocalDate date, String catColor, String breed, Long userId) {
        Cat cat = new Cat();
        cat.setName(name);
        cat.setBirthday(date);
        cat.setBreed(breed);
        cat.setOwner(userRepository.getById(userId));
        Color color;
        try {
            color = Color.valueOf(catColor.toUpperCase());
        }
        catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Element " + catColor + " doesn't exist in enum.");
        }
        cat.setColor(color);

        saveCatAsync(cat);
        return catMapper.CatMapping(cat);
    }


    public void deleteById(Long catId) {
        catRepository.deleteById(catId);
    }


    public CatDTO getCatById(Long id) {
        Cat cat = catRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Cat not found"));
        return catMapper.CatMapping(cat);
    }


    public CatDTO getCatByName(String name) {
        Cat cat = catRepository.findByName(name).orElseThrow(() -> new EntityNotFoundException("Cat not found"));
        return catMapper.CatMapping(cat);
    }


    public List<CatDTO> getFilteredByBirthday(LocalDate birthday) {
        List<Cat> cats = catRepository.findByBirthday(birthday);
        List<CatDTO> catsDTO = new ArrayList<>();
        for (Cat cat : cats){
            catsDTO.add(catMapper.CatMapping(cat));
        }
        return catsDTO;
    }


    public List<CatDTO> getFilteredByColor(String catColor) {
        Color color;
        try {
            color = Color.valueOf(catColor.toUpperCase());
        }
        catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Element " + catColor + " doesn't exist in enum.");
        }

        List<Cat> cats = catRepository.findByColor(color);
        List<CatDTO> catsDTO = new ArrayList<>();
        for (Cat cat : cats){
            catsDTO.add(catMapper.CatMapping(cat));
        }
        return catsDTO;
    }


    public List<CatDTO> getFilteredByBreed(String breed) {
        List<Cat> cats = catRepository.findByBreed(breed);
        List<CatDTO> catsDTO = new ArrayList<>();
        for (Cat cat : cats){
            catsDTO.add(catMapper.CatMapping(cat));
        }
        return catsDTO;
    }


    public CatDTO updateName(Long id, String name) {
        Cat cat = catRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Cat not found"));
        cat.setName(name);
        catRepository.save(cat);
        return catMapper.CatMapping(cat);
    }


    public CatDTO updateBirthday(Long id, LocalDate birthday) {
        Cat cat = catRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Cat not found"));
        cat.setBirthday(birthday);
        catRepository.save(cat);
        return catMapper.CatMapping(cat);
    }


    public CatDTO updateColor(Long id, String catColor) {
        Cat cat = catRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Cat not found"));
        Color color;
        try {
            color = Color.valueOf(catColor.toUpperCase());
        }
        catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Element " + catColor + " doesn't exist in enum.");
        }

        cat.setColor(Color.valueOf(catColor));
        catRepository.save(cat);
        return catMapper.CatMapping(cat);
    }


    public CatDTO updateBreed(Long id, String breed) {
        Cat cat = catRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Cat not found"));
        cat.setBreed(breed);
        catRepository.save(cat);
        return catMapper.CatMapping(cat);
    }
}
