package com.gliesereum.karma.model.repository.jpa.service;

import com.gliesereum.karma.model.entity.service.PackageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

/**
 * @author vitalij
 * @version 1.0
 */
public interface PackageRepository extends JpaRepository<PackageEntity, UUID> {

    List<PackageEntity> getByBusinessId(UUID businessId);
}