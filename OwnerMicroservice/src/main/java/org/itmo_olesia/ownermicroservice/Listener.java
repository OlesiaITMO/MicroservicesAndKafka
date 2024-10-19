package org.itmo_olesia.ownermicroservice;


import lombok.RequiredArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;
import org.itmo_olesia.dto.DTO.OwnerDTO;
import org.itmo_olesia.dto.RequestsAndResponses.Requests.Owners.OwnerCreateRequest;
import org.itmo_olesia.dto.RequestsAndResponses.Requests.Total.DeleteRequest;
import org.itmo_olesia.dto.RequestsAndResponses.Requests.Total.GetByIdRequest;
import org.itmo_olesia.dto.RequestsAndResponses.Responses.Owners.OwnerCreateResponse;
import org.itmo_olesia.dto.RequestsAndResponses.Responses.Owners.OwnerDeleteResponse;
import org.itmo_olesia.dto.RequestsAndResponses.Responses.Owners.OwnerGetByIdResponse;
import org.itmo_olesia.ownermicroservice.Repositories.IUserRepository;
import org.itmo_olesia.ownermicroservice.jpa.Owner;
import org.itmo_olesia.ownermicroservice.jpa.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;
import org.itmo_olesia.ownermicroservice.Services.OwnerService;


@Component
@RequiredArgsConstructor
public class Listener {
    private final OwnerService service;
    @Autowired
    private  IUserRepository userRepository;


    @KafkaListener(topics = "owner.getbyid")
    @SendTo("presentation.owner.getById")
    public OwnerGetByIdResponse getById(@NotNull GetByIdRequest request) {
        var response = new OwnerGetByIdResponse();
        response.setOwnerDTO(service.getById(request.getRequestId()));
        return response;
    }

    @KafkaListener(topics = "owner.save")
    @SendTo("presentation.owner.save")
    public OwnerCreateResponse save(@NotNull OwnerCreateRequest request) {
        var response = new OwnerCreateResponse();
        OwnerDTO ownerDTO = request.getOwnerDTO();
        Owner owner = new Owner();
        owner.setId(ownerDTO.getId());
        owner.setName(ownerDTO.getName());
        owner.setBirthday(ownerDTO.getBirthday()); // расписала jpa в dto + прокинула в бд
        service.saveOwnerAsync(owner);
        User user = new User();

        try {
            if (userRepository.getByUsername(ownerDTO.getName()) == null)
                user.setId(owner.getId());
                user.setUsername(owner.getName());
                user.setPassword(owner.getName());
                userRepository.save(user);
                response.setOwnerDTO(ownerDTO);
        } catch (Exception ignore) { }

        return response;
    }

    @KafkaListener(topics = "owner.delete")
    @SendTo("presentation.owner.delete")
    public OwnerDeleteResponse delete(@NotNull DeleteRequest request) {
        service.deleteById(request.getRequestId());
        return new OwnerDeleteResponse();
    }
}

