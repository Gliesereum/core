package com.gliesereum.karma.service.common.impl;

import com.gliesereum.karma.model.entity.common.PackageEntity;
import com.gliesereum.karma.model.repository.jpa.common.PackageRepository;
import com.gliesereum.karma.service.common.PackageService;
import com.gliesereum.share.common.converter.DefaultConverter;
import com.gliesereum.share.common.model.dto.karma.common.PackageDto;
import com.gliesereum.share.common.service.DefaultServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * @author vitalij
 * @version 1.0
 * @since 12/7/18
 */
@Slf4j
@Service
public class PackageServiceImpl extends DefaultServiceImpl<PackageDto, PackageEntity> implements PackageService {

    @Autowired
    private PackageRepository repository;

    private static final Class<PackageDto> DTO_CLASS = PackageDto.class;
    private static final Class<PackageEntity> ENTITY_CLASS = PackageEntity.class;

    public PackageServiceImpl(PackageRepository repository, DefaultConverter defaultConverter) {
        super(repository, defaultConverter, DTO_CLASS, ENTITY_CLASS);
    }

    @Override
    public List<PackageDto> getByBusinessServiceId(UUID id) {
        List<PackageEntity> entities = repository.getByBusinessServiceId(id);
        return converter.convert(entities, dtoClass);
    }
}