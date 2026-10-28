package com.workintech.s18d1.dao;

import com.workintech.s18d1.entity.BreadType;
import com.workintech.s18d1.entity.Burger;
import com.workintech.s18d1.exceptions.BurgerException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;

@Repository
@Transactional
public class BurgerDaoImpl implements BurgerDao {

    private final EntityManager entityManager;

    public BurgerDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Burger save(Burger burger) {
        entityManager.persist(burger);
        return burger;
    }

    @Override
    @Transactional(readOnly = true)
    public Burger findById(Long id) {
        Burger burger = entityManager.find(Burger.class, id);

        if (burger == null) {
            throw new BurgerException(
                "Burger not found", HttpStatus.NOT_FOUND
            );
        }

        return burger;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Burger> findAll() {
        return entityManager.createQuery(
            "SELECT b FROM Burger b", Burger.class
        ).getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Burger> findByPrice(int price) {
        TypedQuery<Burger> query = entityManager.createQuery(
            "SELECT b FROM Burger b WHERE b.price > :price " +
            "ORDER BY b.price DESC",
            Burger.class
        );
        query.setParameter("price", price);
        return query.getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Burger> findByBreadType(BreadType breadType) {
        TypedQuery<Burger> query = entityManager.createQuery(
            "SELECT b FROM Burger b WHERE b.breadType = :breadType " +
            "ORDER BY b.name ASC",
            Burger.class
        );
        query.setParameter("breadType", breadType);
        return query.getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Burger> findByContent(String content) {
        TypedQuery<Burger> query = entityManager.createQuery(
            "SELECT b FROM Burger b WHERE LOWER(b.contents) " +
            "LIKE :content",
            Burger.class
        );
        query.setParameter(
            "content", "%" + content.toLowerCase(Locale.ROOT) + "%"
        );
        return query.getResultList();
    }

    @Override
    public Burger update(Burger burger) {
        return entityManager.merge(burger);
    }

    @Override
    public Burger remove(Long id) {
        Burger burger = findById(id);
        entityManager.remove(burger);
        return burger;
    }
}
