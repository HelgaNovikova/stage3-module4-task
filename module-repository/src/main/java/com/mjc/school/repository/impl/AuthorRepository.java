package com.mjc.school.repository.impl;

import com.mjc.school.repository.BaseRepository;
import com.mjc.school.repository.model.AuthorModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.support.TransactionTemplate;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class AuthorRepository implements BaseRepository<AuthorModel, Long> {

    private static final String SELECT_ALL_AUTHORS = "select a from AuthorModel a";
    private static final String SELECT_ALL_AUTHORS_ORDER_BY = "select a from AuthorModel a order by ";
    private final NewsRepository newsRepository;
    private final TransactionTemplate transactionTemplate;
    EntityManager entityManager;

    @Autowired
    public AuthorRepository(EntityManager entityManager, TransactionTemplate transactionTemplate, NewsRepository newsRepository) {
        this.entityManager = entityManager;
        this.transactionTemplate = transactionTemplate;
        this.newsRepository = newsRepository;
    }

    public void saveAuthorToDB(AuthorModel author) {
        transactionTemplate.executeWithoutResult(s -> entityManager.merge(author));
    }

    @Override
    public List<AuthorModel> readAll(Integer page, Integer size, String sortBy) {
        List<String> sorting = List.of(sortBy.split(","));
        String request = SELECT_ALL_AUTHORS_ORDER_BY + sorting.get(0) + " " + sorting.get(1);
        return entityManager.createQuery(request, AuthorModel.class)
                .setFirstResult((page - 1) * size)
                .setMaxResults(page * size)
                .getResultList();
    }

    @Override
    public Optional<AuthorModel> readById(Long id) {
        AuthorModel author = entityManager.find(AuthorModel.class, id);
        return Optional.ofNullable(author);
    }

    @Override
    public AuthorModel create(AuthorModel entity) {
        setAbsentData(entity);
        return transactionTemplate.execute(s -> entityManager.merge(entity));
    }

    @Override
    public AuthorModel update(AuthorModel entity) {
        setAbsentData(entity);
        AuthorModel author = entityManager.find(AuthorModel.class, entity.getId());
        author.setName(entity.getName());
        author.setLastUpdateDate(entity.getLastUpdateDate());
        entityManager.persist(author);
        entityManager.flush();
        return author;
    }

    @Override
    public boolean deleteById(Long id) {
        newsRepository.deleteNewsByAuthorId(id);
        entityManager.remove(readById(id).orElseThrow());
        return !existById(id);
    }

    @Override
    public boolean existById(Long id) {
        return readById(id).isPresent();
    }

    private void setAbsentData(AuthorModel author) {
        if (author.getId() == null) {
            author.setCreateDate(LocalDateTime.now());
        }
        author.setLastUpdateDate(LocalDateTime.now());
    }
}
