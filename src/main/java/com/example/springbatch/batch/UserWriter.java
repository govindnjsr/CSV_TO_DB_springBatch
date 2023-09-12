package com.example.springbatch.batch;

import com.example.springbatch.Repository.UserRepository;
import com.example.springbatch.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class UserWriter implements ItemWriter<User> {


    @Autowired
    private UserRepository userRepository;

    @Override
    public void write(Chunk<? extends User> chunk) throws Exception {
//      log.info("Writing ",chunk.getItems().size());
      userRepository.saveAll(chunk.getItems());

    }
}
