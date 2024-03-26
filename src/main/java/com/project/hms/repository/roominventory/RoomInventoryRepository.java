package com.project.hms.repository.roominventory;

import com.project.hms.model_nrdb.RoomInventory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomInventoryRepository extends MongoRepository<RoomInventory, Integer> {
}
