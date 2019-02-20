package com.gliesereum.karma.service.business.impl;

import com.gliesereum.karma.aspect.annotation.UpdateCarWashIndex;
import com.gliesereum.karma.model.entity.business.WorkTimeEntity;
import com.gliesereum.karma.model.repository.jpa.business.WorkTimeRepository;
import com.gliesereum.karma.service.business.WorkTimeService;
import com.gliesereum.karma.service.servicetype.ServiceTypeFacade;
import com.gliesereum.share.common.converter.DefaultConverter;
import com.gliesereum.share.common.exception.client.ClientException;
import com.gliesereum.share.common.model.dto.karma.business.WorkTimeDto;
import com.gliesereum.share.common.model.dto.karma.enumerated.ServiceType;
import com.gliesereum.share.common.service.DefaultServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.gliesereum.share.common.exception.messages.KarmaExceptionMessage.WORKING_TIME_EXIST_IN_THIS_BUSINESS;
import static com.gliesereum.share.common.exception.messages.KarmaExceptionMessage.WORKING_TIME_NOT_FOUND;

/**
 * @author vitalij
 * @version 1.0
 */
@Slf4j
@Service
public class WorkTimeServiceImpl extends DefaultServiceImpl<WorkTimeDto, WorkTimeEntity> implements WorkTimeService {

    private static final Class<WorkTimeDto> DTO_CLASS = WorkTimeDto.class;
    private static final Class<WorkTimeEntity> ENTITY_CLASS = WorkTimeEntity.class;

    private final WorkTimeRepository workTimeRepository;

    @Autowired
    private ServiceTypeFacade serviceTypeFacade;

    public WorkTimeServiceImpl(WorkTimeRepository workTimeRepository, DefaultConverter defaultConverter) {
        super(workTimeRepository, defaultConverter, DTO_CLASS, ENTITY_CLASS);
        this.workTimeRepository = workTimeRepository;
    }

    @Override
    public List<WorkTimeDto> getByObjectId(UUID objectId) {
        List<WorkTimeDto> result = null;
        if (objectId != null) {
            List<WorkTimeEntity> entities = workTimeRepository.findByObjectId(objectId);
            result = converter.convert(entities, dtoClass);
        }
        return result;
    }

    @Override
    @UpdateCarWashIndex
    public WorkTimeDto create(WorkTimeDto dto) {
        WorkTimeDto result = null;
        if (dto != null) {
            checkDayExist(dto);
            serviceTypeFacade.throwExceptionIfUserDontHavePermissionToAction(dto.getServiceType(), dto.getObjectId());
            result = super.create(dto);
        }
        return result;
    }

    @Override
    @UpdateCarWashIndex
    public WorkTimeDto update(WorkTimeDto dto) {
        WorkTimeDto result = null;
        if (dto != null) {
            WorkTimeDto time = getById(dto.getId());
            if (time == null) {
                throw new ClientException(WORKING_TIME_NOT_FOUND);
            }
            if (!dto.getDayOfWeek().equals(time.getDayOfWeek())) {
                checkDayExist(dto);
            }
            serviceTypeFacade.throwExceptionIfUserDontHavePermissionToAction(dto.getServiceType(), dto.getObjectId());
            result = super.update(dto);
        }
        return result;
    }

    @Override
    public void delete(UUID id, ServiceType serviceType) {
        if ((id != null) && (serviceType != null)) {
            Optional<WorkTimeEntity> entity = repository.findById(id);
            entity.ifPresent(i -> {
                serviceTypeFacade.throwExceptionIfUserDontHavePermissionToAction(serviceType, i.getObjectId());
                repository.delete(i);
            });
        }
    }

    private void checkDayExist(WorkTimeDto dto) {
        if (workTimeRepository.existsByObjectIdAndDayOfWeek(dto.getObjectId(), dto.getDayOfWeek())) {
            throw new ClientException(WORKING_TIME_EXIST_IN_THIS_BUSINESS);
        }
    }
}