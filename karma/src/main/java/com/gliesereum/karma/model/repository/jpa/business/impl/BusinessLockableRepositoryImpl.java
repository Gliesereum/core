package com.gliesereum.karma.model.repository.jpa.business.impl;

import com.gliesereum.karma.model.entity.business.BaseBusinessEntity;
import com.gliesereum.karma.model.repository.jpa.business.BusinessLockableRepository;
import org.apache.commons.collections4.CollectionUtils;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author yvlasiuk
 * @version 1.0
 */
public class BusinessLockableRepositoryImpl implements BusinessLockableRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public BaseBusinessEntity findByIdAndLock(UUID id) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<BaseBusinessEntity> query = builder.createQuery(BaseBusinessEntity.class);
        Root<BaseBusinessEntity> root = query.from(BaseBusinessEntity.class);

        List<Predicate> predicates = new ArrayList<>();

        createEqIfNotNull(builder, predicates, root.get("id"), id);

        if (CollectionUtils.isNotEmpty(predicates)) {
            query.where(predicates.toArray(new Predicate[predicates.size()]));
        }
        TypedQuery<BaseBusinessEntity> typedQuery = entityManager.createQuery(query);
        typedQuery.setLockMode(LockModeType.PESSIMISTIC_WRITE);
        return typedQuery.getSingleResult();
    }

    private void createEqIfNotNull(CriteriaBuilder criteriaBuilder, List<Predicate> predicates, Expression<?> expression, Object value) {
        if (value != null) {
            predicates.add(criteriaBuilder.equal(expression, value));
        }
    }
}