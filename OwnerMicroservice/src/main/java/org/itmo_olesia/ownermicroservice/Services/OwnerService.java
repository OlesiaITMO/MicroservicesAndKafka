package org.itmo_olesia.ownermicroservice.Services;


import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.itmo_olesia.dto.DTO.OwnerDTO;
import org.itmo_olesia.ownermicroservice.Repositories.IOwnerRepository;
import org.itmo_olesia.ownermicroservice.jpa.Cat;
import org.itmo_olesia.ownermicroservice.jpa.Owner;
import org.itmo_olesia.ownermicroservice.utils.OwnerMapper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;



import java.time.LocalDate;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class OwnerService {
    final private IOwnerRepository ownerRepository;
    private final OwnerMapper ownerMapper = new OwnerMapper();


    @Async
    public void saveOwnerAsync(Owner Owner) {
        ownerRepository.save(Owner);
    }

    @Transactional
    public OwnerDTO createOwner(String name, LocalDate date) {
        Owner owner = new Owner();
        owner.setName(name);
        owner.setBirthday(date);

        saveOwnerAsync(owner);
        return ownerMapper.OwnerMapping(owner);
    }

    @Transactional
    public void deleteById(Long OwnerId) {
        ownerRepository.deleteById(OwnerId);
    }

    @Transactional
    public void deleteByUsername(String  username) {
        ownerRepository.deleteByName(username);
    }

    @Transactional
    public OwnerDTO updateNameById(Long id, String name) {
        Owner Owner = ownerRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Owner not found"));
        Owner.setName(name);
        ownerRepository.save(Owner);
        return ownerMapper.OwnerMapping(Owner);
    }

    @Transactional
    public OwnerDTO updateUsername(String username, String newName) {
        Owner Owner = ownerRepository.findByName(username).orElseThrow();
        Owner.setName(newName);
        ownerRepository.save(Owner);
        return ownerMapper.OwnerMapping(Owner);
    }

    @Transactional
    public OwnerDTO updateBirthdayById(Long id, LocalDate birthday) {
        Owner Owner = ownerRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Owner not found"));
        Owner.setBirthday(birthday);
        ownerRepository.save(Owner);
        return ownerMapper.OwnerMapping(Owner);
    }

    @Transactional
    public OwnerDTO updateBirthday(String username, LocalDate birthday) {
        Owner owner = ownerRepository.findByName(username).orElseThrow(() -> new EntityNotFoundException("Owner not found"));
        owner.setBirthday(birthday);
        ownerRepository.save(owner);
        return ownerMapper.OwnerMapping(owner);
    }

    @Transactional
    public OwnerDTO getById(Long id) {
        Owner Owner = ownerRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Owner not found"));
        return ownerMapper.OwnerMapping(Owner);
    }

    @Transactional
    public OwnerDTO getByName(String name) {
        Owner Owner = ownerRepository.findByName(name).orElseThrow(() -> new EntityNotFoundException("Owner not found"));
        return ownerMapper.OwnerMapping(Owner);
    }

    @Transactional
    public List<OwnerDTO> getFilteredByBirthday(LocalDate date) {
        List<Owner> owners = ownerRepository.findByBirthday(date);
        List<OwnerDTO> OwnersDTO = new ArrayList<>();
        for (Owner Owner : owners) {
            OwnersDTO.add(ownerMapper.OwnerMapping(Owner));
        }
        return OwnersDTO;
    }

    @Transactional
    public List<Long> getOwnerCats(Long id) {
        Owner Owner = ownerRepository.getById(id);
        List<Cat> cats = Owner.getCats();
        List<Long> cats_long = new ArrayList<>();
        for (Cat cat : cats) {
            cats_long.add(cat.getId());
        }
        return cats_long;
    }
}
