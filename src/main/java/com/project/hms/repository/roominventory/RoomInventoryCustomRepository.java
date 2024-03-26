package com.project.hms.repository.roominventory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

@Component
public class RoomInventoryCustomRepository {

    @Autowired
    RoomInventoryRepository roomInventoryRepository;

    @Autowired
    MongoTemplate mongoTemplate;




}
