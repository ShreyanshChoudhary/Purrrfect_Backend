package com.Purrrfect.Repo;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import com.Purrrfect.Model.User; // Import your Users entity class

import java.util.List;

@Repository
public class CustomEntityManager {

    // Inject EntityManager
    @PersistenceContext
    private EntityManager entityManager;

    // Example method to find an entity by ID
    public User findEntityById(Integer id) {
        return entityManager.find(User.class, id); // Use Integer since userId is Integer
    }

    // Example method to save an entity
    @Transactional
    public User saveEntity(User user) {
        if (user.getUserId() == null) {
            entityManager.persist(user); // Insert if it's a new entity
        } else {
            entityManager.merge(user); // Update if it already exists
        }
        return user;
    }

    // Example method to delete an entity
    @Transactional
    public void deleteEntity(Integer id) {
        User user = findEntityById(id);
        if (user != null) {
            entityManager.remove(user); // Remove entity
        }
    }

    // Example method to execute a custom query
    public List<User> getAllEntities() {
        return entityManager.createQuery("SELECT u FROM Users u", User.class).getResultList();
    }

    // Example method to execute a JPQL query with parameters
    public List<User> findEntitiesBySomeCriteria(String criteria) {
        return entityManager.createQuery("SELECT u FROM Users u WHERE u.userName = :criteria", User.class)
                .setParameter("criteria", criteria)
                .getResultList();
    }
}
