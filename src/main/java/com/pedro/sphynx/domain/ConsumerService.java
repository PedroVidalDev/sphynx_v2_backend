package com.pedro.sphynx.domain;

import com.pedro.sphynx.application.dtos.consumer.ConsumerDataComplete;
import com.pedro.sphynx.application.dtos.consumer.ConsumerDataEditInputDTO;
import com.pedro.sphynx.application.dtos.consumer.ConsumerDataInputDTO;
import com.pedro.sphynx.infrastructure.entities.Consumer;
import com.pedro.sphynx.infrastructure.repository.ConsumerRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ResourceBundle;

@Service
public class ConsumerService {

    @Autowired
    private ConsumerRepository repository;

    private ResourceBundle messages = ResourceBundle.getBundle("messages");

    public ConsumerDataComplete createVerify(ConsumerDataInputDTO data){
        if(repository.existsByRa(data.ra())){
            throw new RuntimeException(messages.getString("error.raAlreadyExists"));
        } else{
            Consumer consumer = new Consumer(data);
            repository.save(consumer);
            return new ConsumerDataComplete(consumer);
        }
    }

    public ConsumerDataComplete updateVerify(ConsumerDataEditInputDTO data, String ra){
        if(repository.existsByRa(ra)){
            var consumer = repository.getReferenceByRa(ra);
            consumer.actualizeData(data);
            return new ConsumerDataComplete(consumer);
        } else{
            throw new RuntimeException(messages.getString("error.raDontExists"));
        }
    }
}
