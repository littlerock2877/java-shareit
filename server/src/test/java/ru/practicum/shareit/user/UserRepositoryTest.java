package ru.practicum.shareit.user;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.practicum.shareit.user.storage.UserRepository;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;
    private User user;

    @BeforeEach
    void before() {
        user = userRepository.save(new User(1L, "user1", "user1@email"));
    }

    @AfterEach
    void after() {
        userRepository.deleteAll();
    }

    @Test
    void findUserByIdTest() {
        final Optional<User> user1 = userRepository.findById(user.getId());
        assertNotNull(user1);
    }
}
