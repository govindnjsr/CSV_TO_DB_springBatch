package com.example.springbatch.batch;

import com.example.springbatch.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;

@Slf4j
public class UserProcessor implements ItemProcessor<User,User> {
    @Override
    public User process(User item) throws Exception {
//        log.info("Processing processor",item);
        return item;
    }
}
