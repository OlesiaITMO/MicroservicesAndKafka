package org.itmo_olesia.ownermicroservice.utils;



import lombok.RequiredArgsConstructor;
import org.itmo_olesia.dto.DTO.OwnerDTO;
import org.itmo_olesia.ownermicroservice.jpa.Cat;
import org.itmo_olesia.ownermicroservice.jpa.Owner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class OwnerMapper {
    public OwnerDTO OwnerMapping(Owner owner) {
        OwnerDTO ownerDTO = new OwnerDTO();
        ownerDTO.setId(owner.getId());
        ownerDTO.setName(owner.getName());
        ownerDTO.setBirthday(owner.getBirthday());
        ownerDTO.setBirthday(owner.getBirthday());

        if (owner.getCats() != null) {
            List<Cat> cats = owner.getCats();
            List<Long> allId = new ArrayList<>();
            for (Cat kitti : cats) {
                allId.add(kitti.getId());
            }
            ownerDTO.setCats(allId);
        }

        return ownerDTO;
    }
}
