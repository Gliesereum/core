package com.gliesereum.karma.model.repository.jpa.common;

import com.gliesereum.karma.model.entity.common.FilterAttributeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

/**
 * @author vitalij
 * @version 1.0
 */
public interface FilterAttributeRepository extends JpaRepository<FilterAttributeEntity, UUID> {

    List<FilterAttributeEntity> findAllByFilterId(UUID filterId);

    boolean existsById(UUID id);
}
