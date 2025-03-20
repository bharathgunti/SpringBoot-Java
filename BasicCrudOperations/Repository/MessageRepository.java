package com.example.BasicCrudOperations.Repository;

import com.example.BasicCrudOperations.Model.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<MessageEntity,Integer> {
}
