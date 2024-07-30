package com.example.demo.repository;

import com.example.demo.domain.BulletinBoard;
import com.example.demo.domain.User;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository {
    private final EntityManager em;

    @Autowired
    public UserRepository(EntityManager em) {
        this.em = em;
    }

    public User save(User user) {
        em.persist(user);
        return user;
    }

    public Optional<User> findById(Long id) {
        return Optional.ofNullable(em.find(User.class, id));
    }

    public Optional<User> findByEmail(String email) {
        List<User> users = em.createQuery("select u from User u where u.email = :email", User.class)
                .setParameter("email", email)
                .getResultList();
        return users.stream().findAny();
    }
}
