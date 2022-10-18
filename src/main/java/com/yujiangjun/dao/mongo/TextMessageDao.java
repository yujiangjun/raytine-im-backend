package com.yujiangjun.dao.mongo;

import com.yujiangjun.message.TextMessage;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TextMessageDao extends MongoRepository<TextMessage,String> {
}
