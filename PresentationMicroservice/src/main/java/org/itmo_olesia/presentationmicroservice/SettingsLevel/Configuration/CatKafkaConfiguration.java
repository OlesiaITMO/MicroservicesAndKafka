package org.itmo_olesia.presentationmicroservice.SettingsLevel.Configuration;

import org.antlr.v4.runtime.misc.NotNull;
import org.itmo_olesia.dto.RequestsAndResponses.Requests.Cats.CatCreateRequest;
import org.itmo_olesia.dto.RequestsAndResponses.Requests.Cats.CatDeleteResponse;
import org.itmo_olesia.dto.RequestsAndResponses.Requests.Total.DeleteRequest;
import org.itmo_olesia.dto.RequestsAndResponses.Requests.Total.GetByIdRequest;
import org.itmo_olesia.dto.RequestsAndResponses.Responses.Cats.CatCreateResponse;
import org.itmo_olesia.dto.RequestsAndResponses.Responses.Cats.CatGetByIdResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class CatKafkaConfiguration {

    @Bean
    public ReplyingKafkaTemplate<Long, GetByIdRequest, CatGetByIdResponse>
    getByIdTemplate(
            @NotNull ProducerFactory<Long, GetByIdRequest> pf,
            @NotNull ConcurrentKafkaListenerContainerFactory<Long, CatGetByIdResponse> cf) {
        return new ReplyingKafkaTemplate<>(pf, cf.createContainer("presentation.cat.getbyid"));
    }


    @Bean
    public ReplyingKafkaTemplate<Long, CatCreateRequest, CatCreateResponse>
    createTemplate(
            @NotNull ProducerFactory<Long, CatCreateRequest> pf,
            @NotNull ConcurrentKafkaListenerContainerFactory<Long, CatCreateResponse> cf) {
        return new ReplyingKafkaTemplate<>(pf, cf.createContainer("presentation.cat.save"));
    }

    @Bean
    public ReplyingKafkaTemplate<Long, DeleteRequest, CatDeleteResponse>
    deleteTemplate(
            @NotNull ProducerFactory<Long, DeleteRequest> pf,
            @NotNull ConcurrentKafkaListenerContainerFactory<Long, CatDeleteResponse> cf) {
        return new ReplyingKafkaTemplate<>(pf, cf.createContainer("presentation.cat.delete"));
    }

}
