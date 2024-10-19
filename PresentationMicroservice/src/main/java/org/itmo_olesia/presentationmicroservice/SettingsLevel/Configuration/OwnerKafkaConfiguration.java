package org.itmo_olesia.presentationmicroservice.SettingsLevel.Configuration;


import org.antlr.v4.runtime.misc.NotNull;
import org.itmo_olesia.dto.RequestsAndResponses.Requests.Owners.OwnerCreateRequest;
import org.itmo_olesia.dto.RequestsAndResponses.Requests.Total.DeleteRequest;
import org.itmo_olesia.dto.RequestsAndResponses.Requests.Total.GetByIdRequest;
import org.itmo_olesia.dto.RequestsAndResponses.Responses.Owners.OwnerCreateResponse;
import org.itmo_olesia.dto.RequestsAndResponses.Responses.Owners.OwnerDeleteResponse;
import org.itmo_olesia.dto.RequestsAndResponses.Responses.Owners.OwnerGetByIdResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.stereotype.Component;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;

// допиши на проброс через несколько микросервисов
@Component
public class OwnerKafkaConfiguration {

    @Bean
    public ReplyingKafkaTemplate<Long, GetByIdRequest, OwnerGetByIdResponse>
    getByIdOwnerReplyingKafkaTemplate(@NotNull ProducerFactory<Long, GetByIdRequest> pf,
                                      @NotNull ConcurrentKafkaListenerContainerFactory<Long, OwnerGetByIdResponse> cf) {
        return new ReplyingKafkaTemplate<>(pf, cf.createContainer("presentation.owner.getById"));
    }
    @Bean
    public ReplyingKafkaTemplate<Long, OwnerCreateRequest, OwnerCreateResponse>
    createOwnerReplyingKafkaTemplate(@NotNull ProducerFactory<Long, OwnerCreateRequest> pf,
                                     @NotNull ConcurrentKafkaListenerContainerFactory<Long, OwnerCreateResponse> cf) {
        return new ReplyingKafkaTemplate<>(pf, cf.createContainer("presentation.owner.save"));
    }

    @Bean
    public ReplyingKafkaTemplate<Long, DeleteRequest, OwnerDeleteResponse>
    deleteOwnerReplyingKafkaTemplate(@NotNull ProducerFactory<Long, DeleteRequest> pf,
                                     @NotNull ConcurrentKafkaListenerContainerFactory<Long, OwnerDeleteResponse> cf) {
        return new ReplyingKafkaTemplate<>(pf, cf.createContainer("presentation.owner.delete"));
    }
}
