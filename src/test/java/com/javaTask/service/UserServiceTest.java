package com.javaTask.service;

import com.javaTask.model.User;
import com.javaTask.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;

/**
 * @author Aleksandr Beryozkin
 */

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserServiceTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testFindAll() {
        User user1 = new User("user1", "password1");
        User user2 = new User("user2", "password2");

        List<User> outputs = null;

        entityManager.persist(user1);
        entityManager.persist(user2);
        entityManager.flush();

        outputs = userRepository.findAll();

        assertThat(outputs).isNotNull().hasSize(2);
    }

    @Test
    public void testSave() {
        User user1 = new User("user1", "password1");
        User output = null;

        userRepository.save(user1);

        output = userRepository.findAll().get(0);

        assertThat(output).isNotNull();
        assertThat(output.getUsername()).isEqualTo(user1.getUsername());
    }

    @Test
    public void testFindByUsername() {
        User user1 = new User("user1", "password1");
        User output = null;

        entityManager.persistAndFlush(user1);

        output = userRepository.findOneByUsername(user1.getUsername());

        assertThat(output).isNotNull();
        assertThat(output.getPassword()).isEqualTo(user1.getPassword());
    }

    @Test
    public void testFindById() {
        User user1 = new User("user1", "password1");
        User output = null;

        entityManager.persistAndFlush(user1);

        output = userRepository.findById(user1.getId()).get();

        assertThat(output).isNotNull();
        assertThat(output.getUsername()).isEqualTo(user1.getUsername());
    }

    @Test
    public void testDeleteById() {
        User user1 = new User("user1", "password1");
        User user2 = new User("user2", "password2");

        List<User> outputs = null;

        entityManager.persist(user1);
        entityManager.persist(user2);
        entityManager.flush();

        userRepository.deleteById(user1.getId());

        outputs = userRepository.findAll();

        assertThat(outputs).isNotNull().hasSize(1);
    }
}
