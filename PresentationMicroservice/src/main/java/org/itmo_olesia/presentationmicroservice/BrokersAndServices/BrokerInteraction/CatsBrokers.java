package org.itmo_olesia.presentationmicroservice.BrokersAndServices.BrokerInteraction;

import lombok.RequiredArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.itmo_olesia.dto.DTO.CatDTO;
import org.itmo_olesia.dto.RequestsAndResponses.Requests.Cats.CatCreateRequest;
import org.itmo_olesia.dto.RequestsAndResponses.Requests.Cats.CatDeleteResponse;
import org.itmo_olesia.dto.RequestsAndResponses.Requests.Total.DeleteRequest;
import org.itmo_olesia.dto.RequestsAndResponses.Requests.Total.GetByIdRequest;
import org.itmo_olesia.dto.RequestsAndResponses.Responses.Cats.CatCreateResponse;
import org.itmo_olesia.dto.RequestsAndResponses.Responses.Cats.CatGetByIdResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Component
@RequiredArgsConstructor
public class CatsBrokers {
    private final ReplyingKafkaTemplate<Long, GetByIdRequest, CatGetByIdResponse> getByIdReplyingKafkaTemplate;
    private final ReplyingKafkaTemplate<Long, CatCreateRequest, CatCreateResponse> createReplyingKafkaTemplate;
    private final ReplyingKafkaTemplate<Long, DeleteRequest, CatDeleteResponse> deleteReplyingKafkaTemplate;

    @Value("${spring.kafka.properties.standardTimeout}")
    private Long timeout;


    public Optional<CatDTO> getById(Long id) throws ExecutionException, InterruptedException, TimeoutException {
        var getByIdRequest = new GetByIdRequest();
        getByIdRequest.setRequestId(id);
        return Optional.ofNullable(getByIdReplyingKafkaTemplate
                .sendAndReceive(new ProducerRecord<>("cat.getbyid", getByIdRequest))
                .get(timeout, TimeUnit.HOURS)
                .value().getCatDTO());
    }

    public CatDTO create(@NotNull CatDTO catDTO) throws ExecutionException, InterruptedException, TimeoutException {
        var createRequest = new CatCreateRequest();
        catDTO.setId(null);
        createRequest.setCatDTO(catDTO);
        return createReplyingKafkaTemplate
                .sendAndReceive(new ProducerRecord<>("cat.save", createRequest))
                .get(timeout, TimeUnit.HOURS)
                .value().getCatDTO();
    }

    public CatDTO update(CatDTO catDTO) throws ExecutionException, InterruptedException, TimeoutException {
        var createRequest = new CatCreateRequest();
        createRequest.setCatDTO(catDTO);
        return createReplyingKafkaTemplate
                .sendAndReceive(new ProducerRecord<>("cat.save", createRequest))
                .get(timeout, TimeUnit.HOURS)
                .value().getCatDTO();
    }

    public void delete(Long id) throws ExecutionException, InterruptedException, TimeoutException {
        var deleteRequest = new DeleteRequest();
        deleteRequest.setRequestId(id);
        deleteReplyingKafkaTemplate
                .sendAndReceive(new ProducerRecord<>("cat.delete", deleteRequest))
                .get(timeout, TimeUnit.HOURS);
    }
}
