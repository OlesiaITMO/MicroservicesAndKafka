package org.itmo_olesia.catmicroservice;

import lombok.RequiredArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;
import org.itmo_olesia.catmicroservice.Services.CatService;
import org.itmo_olesia.catmicroservice.jpa.Cat;
import org.itmo_olesia.dto.DTO.CatDTO;
import org.itmo_olesia.dto.RequestsAndResponses.Requests.Cats.CatCreateRequest;
import org.itmo_olesia.dto.RequestsAndResponses.Requests.Total.GetByIdRequest;
import org.itmo_olesia.dto.RequestsAndResponses.Responses.Cats.CatCreateResponse;
import org.itmo_olesia.dto.RequestsAndResponses.Responses.Cats.CatGetByIdResponse;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Listener {
    private final CatService service;

    @KafkaListener(topics = "cat.getbyid")
    @SendTo("presentation.cat.getbyid")
    public CatGetByIdResponse getById(@NotNull GetByIdRequest request) {
        var response = new CatGetByIdResponse();
        response.setCatDTO(service.getCatById(request.getRequestId()));
        return response;
    }

    @KafkaListener(topics = "cat.save")
    @SendTo("presentation.cat.save")
    public CatCreateResponse save(@NotNull CatCreateRequest request) {
        var response = new CatCreateResponse();
        CatDTO catDTO = request.getCatDTO();
        Cat cat = new Cat();
        cat.setId(catDTO.getId());
        cat.setName(catDTO.getName());
        cat.setBirthday(catDTO.getBirthday());
        cat.setColor(catDTO.getColor());
        cat.setBreed(catDTO.getBreed());
        service.saveCatAsync(cat);
        response.setCatDTO(catDTO);
        return response;
    }

    @KafkaListener(topics = "cat.delete")
    public void delete(@NotNull Long id) {
        service.deleteById(id);
    }
}

