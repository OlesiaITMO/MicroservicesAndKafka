package org.itmo_olesia.catmicroservice.utils;

import lombok.NoArgsConstructor;
import org.itmo_olesia.catmicroservice.jpa.Cat;
import org.itmo_olesia.dto.DTO.CatDTO;


import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
public class CatMapper {
    public CatDTO CatMapping(Cat cat) {
        CatDTO catDTO = new CatDTO();
        catDTO.setId(cat.getId());
        catDTO.setName(cat.getName());
        catDTO.setBirthday(cat.getBirthday());
        catDTO.setColor(cat.getColor());
        catDTO.setBreed(cat.getBreed());
        catDTO.setOwnerId(cat.getOwner().getId());

        if (cat.getFriends() != null) {
            List<Cat> friends = cat.getFriends().stream().toList();
            List<Long> allId = new ArrayList<>();
            for (Cat kitti : friends) {
                allId.add(kitti.getId());
            }
            catDTO.setFriends(allId);
        }

        return catDTO;
    }
}

