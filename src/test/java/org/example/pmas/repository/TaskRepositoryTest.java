package org.example.pmas.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Sql(

)
@Transactional
@Rollback
class TaskRepositoryTest {

    @Autowired
    private TaskRepository taskRepository;

    @Test
    void readAll() {
    }
}