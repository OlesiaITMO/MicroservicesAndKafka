package org.itmo_olesia.presentationmicroservice.BrokersAndServices.BrokerInteraction;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.itmo_olesia.dto.DTO.OwnerDTO;

import org.itmo_olesia.dto.RequestsAndResponses.Requests.Owners.OwnerCreateRequest;
import org.itmo_olesia.dto.RequestsAndResponses.Requests.Total.DeleteRequest;
import org.itmo_olesia.dto.RequestsAndResponses.Requests.Total.GetByIdRequest;
import org.itmo_olesia.dto.RequestsAndResponses.Responses.Owners.OwnerCreateResponse;
import org.itmo_olesia.dto.RequestsAndResponses.Responses.Owners.OwnerDeleteResponse;
import org.itmo_olesia.dto.RequestsAndResponses.Responses.Owners.OwnerGetByIdResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


@Component
@RequiredArgsConstructor
public class OwnersBrokers {
    private final ReplyingKafkaTemplate<Long, GetByIdRequest, OwnerGetByIdResponse> getByIdTemplate;
    private final ReplyingKafkaTemplate<Long, OwnerCreateRequest, OwnerCreateResponse> createTemplate;
    private final ReplyingKafkaTemplate<Long, DeleteRequest, OwnerDeleteResponse> deleteTemplate;

    @Value("${spring.kafka.properties.standardTimeout}")
    private Long timeout;


    public Optional<OwnerDTO> getById(Long Id) throws ExecutionException, InterruptedException, TimeoutException {
        var getByIdRequest = new GetByIdRequest();
        getByIdRequest.setRequestId(Id);
        return Optional.ofNullable(getByIdTemplate
                .sendAndReceive(new ProducerRecord<>("owner.getbyid", getByIdRequest))
                .get(timeout, TimeUnit.HOURS)
                .value().getOwnerDTO());
    }

    public OwnerDTO create(OwnerDTO ownerDTO) throws ExecutionException, InterruptedException, TimeoutException {
        var createRequest = new OwnerCreateRequest();
        ownerDTO.setId(null);
        createRequest.setOwnerDTO(ownerDTO);
        return createTemplate
                .sendAndReceive(new ProducerRecord<>("owner.save", createRequest))
                .get(timeout, TimeUnit.HOURS)
                .value().getOwnerDTO();
    }

    public OwnerDTO update(OwnerDTO ownerDTO) throws ExecutionException, InterruptedException, TimeoutException {
        var createRequest = new OwnerCreateRequest();
        createRequest.setOwnerDTO(ownerDTO);
        return createTemplate
                .sendAndReceive(new ProducerRecord<>("owner.save", createRequest))
                .get(timeout, TimeUnit.HOURS)
                .value().getOwnerDTO();
    }

    public void delete(Long id) throws ExecutionException, InterruptedException, TimeoutException {
        var deleteRequest = new DeleteRequest();
        deleteRequest.setRequestId(id);
        deleteTemplate
                .sendAndReceive(new ProducerRecord<>("owner.delete", deleteRequest))
                .get(timeout, TimeUnit.HOURS);
    }
}
