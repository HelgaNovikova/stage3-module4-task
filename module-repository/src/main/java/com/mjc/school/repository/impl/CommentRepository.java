package com.mjc.school.repository.impl;

import com.mjc.school.repository.BaseRepository;
import com.mjc.school.repository.ExtraCommentRepository;
import com.mjc.school.repository.model.CommentModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.support.TransactionTemplate;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class CommentRepository implements BaseRepository<CommentModel, Long>, ExtraCommentRepository {

    private static final String SELECT_ALL_COMMENTS_BY_NEWS_ID = "select c from CommentModel c where newsId=:newsId";
    private static final String SELECT_ALL_COMMENTS_ORDER_BY = "select c from CommentModel c order by ";
    private final TransactionTemplate transactionTemplate;
    EntityManager entityManager;

    @Autowired
    public CommentRepository(TransactionTemplate transactionTemplate, EntityManager entityManager) {
        this.transactionTemplate = transactionTemplate;
        this.entityManager = entityManager;
    }

    @Override
    public List<CommentModel> readAll(Integer page, Integer size, String sortBy) {
        List<String> sorting = List.of(sortBy.split(","));
        String request = SELECT_ALL_COMMENTS_ORDER_BY + sorting.get(0) + " " + sorting.get(1);
        return entityManager.createQuery(request, CommentModel.class)
                .setFirstResult((page - 1) * size)
                .setMaxResults(page * size)
                .getResultList();
    }

    @Override
    public Optional<CommentModel> readById(Long id) {
        CommentModel comment = entityManager.find(CommentModel.class, id);
        return Optional.ofNullable(comment);
    }

    @Override
    public CommentModel create(CommentModel entity) {
        setAbsentData(entity);
        return transactionTemplate.execute(s -> entityManager.merge(entity));
    }

    private void setAbsentData(CommentModel entity) {
        if (entity.getId() == null) {
            entity.setCreateDate(LocalDateTime.now());
        }
        entity.setLastUpdateDate(LocalDateTime.now());
    }

    @Override
    public CommentModel update(CommentModel entity) {
        setAbsentData(entity);
        CommentModel comment = entityManager.find(CommentModel.class, entity.getId());
        comment.setContent(entity.getContent());
        comment.setNews(entity.getNews());
        comment.setLastUpdateDate(entity.getLastUpdateDate());
        entityManager.persist(comment);
        entityManager.flush();
        return comment;
    }

    @Override
    public boolean deleteById(Long id) {
        entityManager.remove(readById(id).orElseThrow());
        return !existById(id);
    }

    @Override
    public boolean existById(Long id) {
        return readById(id).isPresent();
    }

    @Override
    public List<CommentModel> getCommentsByNewsId(Long id) {
        return entityManager.createQuery(SELECT_ALL_COMMENTS_BY_NEWS_ID, CommentModel.class)
                .setParameter("newsId", id)
                .getResultList();
    }
}
