package com.gliesereum.karma.service.business.impl;

import com.gliesereum.karma.aspect.annotation.UpdateCarWashIndex;
import com.gliesereum.karma.model.entity.business.BaseBusinessEntity;
import com.gliesereum.karma.model.repository.jpa.business.BaseBusinessRepository;
import com.gliesereum.karma.service.business.BaseBusinessService;
import com.gliesereum.karma.service.comment.CommentService;
import com.gliesereum.karma.service.media.MediaService;
import com.gliesereum.karma.service.record.BaseRecordService;
import com.gliesereum.karma.service.service.PackageService;
import com.gliesereum.karma.service.service.ServicePriceService;
import com.gliesereum.share.common.converter.DefaultConverter;
import com.gliesereum.share.common.exception.client.ClientException;
import com.gliesereum.share.common.model.dto.karma.business.BaseBusinessDto;
import com.gliesereum.share.common.model.dto.karma.business.BusinessFullModel;
import com.gliesereum.share.common.model.dto.karma.business.WorkTimeDto;
import com.gliesereum.share.common.model.dto.karma.business.WorkingSpaceDto;
import com.gliesereum.share.common.model.dto.karma.comment.CommentDto;
import com.gliesereum.share.common.model.dto.karma.comment.CommentFullDto;
import com.gliesereum.share.common.model.dto.karma.enumerated.StatusRecord;
import com.gliesereum.share.common.model.dto.karma.media.MediaDto;
import com.gliesereum.share.common.model.dto.karma.record.BaseRecordDto;
import com.gliesereum.share.common.model.dto.karma.service.PackageDto;
import com.gliesereum.share.common.model.dto.karma.service.ServicePriceDto;
import com.gliesereum.share.common.service.DefaultServiceImpl;
import com.gliesereum.share.common.util.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static com.gliesereum.share.common.exception.messages.CommonExceptionMessage.ID_NOT_SPECIFIED;
import static com.gliesereum.share.common.exception.messages.CommonExceptionMessage.SERVICE_TYPE_IS_EMPTY;
import static com.gliesereum.share.common.exception.messages.KarmaExceptionMessage.*;

/**
 * @author vitalij
 * @version 1.0
 */
@Slf4j
@Service
public class BaseBusinessServiceImpl extends DefaultServiceImpl<BaseBusinessDto, BaseBusinessEntity> implements BaseBusinessService {

    private static final Class<BaseBusinessDto> DTO_CLASS = BaseBusinessDto.class;
    private static final Class<BaseBusinessEntity> ENTITY_CLASS = BaseBusinessEntity.class;

    private final BaseBusinessRepository baseBusinessRepository;

    @Autowired
    private ServicePriceService servicePriceService;

    @Autowired
    private PackageService packageService;

    @Autowired
    private MediaService mediaService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private BaseRecordService baseRecordService;

    public BaseBusinessServiceImpl(BaseBusinessRepository repository, DefaultConverter defaultConverter) {
        super(repository, defaultConverter, DTO_CLASS, ENTITY_CLASS);
        this.baseBusinessRepository = repository;
    }

    @Override
    @UpdateCarWashIndex
    public BaseBusinessDto create(BaseBusinessDto dto) {
        SecurityUtil.checkUserByBanStatus();
        if (dto != null) {
            checkType(dto);
            checkCorporationId(dto);
            BaseBusinessEntity entity = converter.convert(dto, entityClass);
            entity = repository.saveAndFlush(entity);
            dto = converter.convert(entity, dtoClass);
        }
        return dto;
    }

    @Override
    @UpdateCarWashIndex
    public BaseBusinessDto update(BaseBusinessDto dto) {
        SecurityUtil.checkUserByBanStatus();
        if (dto != null) {
            checkType(dto);
            if (dto.getId() == null) {
                throw new ClientException(ID_NOT_SPECIFIED);
            }
            this.currentUserHavePermissionToActionInBusiness(dto.getId());
            checkCorporationId(dto);
            BaseBusinessEntity entity = converter.convert(dto, entityClass);
            entity = repository.saveAndFlush(entity);
            dto = converter.convert(entity, dtoClass);
        }
        return dto;
    }

    @Override
    public boolean existByIdAndCorporationIds(UUID id, List<UUID> corporationIds) {
        return baseBusinessRepository.existsByIdAndCorporationIdIn(id, corporationIds);
    }

    @Override
    public boolean currentUserHavePermissionToActionInBusiness(UUID businessId) {
        boolean result = false;
        List<UUID> userCorporationIds = SecurityUtil.getUserCorporationIds();
        if (CollectionUtils.isNotEmpty(userCorporationIds)) {
            result = existByIdAndCorporationIds(businessId, userCorporationIds);
        }
        return result;
    }

    @Override
    public List<BaseBusinessDto> getByCorporationIds(List<UUID> corporationIds) {
        List<BaseBusinessDto> result = null;
        if (CollectionUtils.isNotEmpty(corporationIds)) {
            List<BaseBusinessEntity> entities = baseBusinessRepository.findByCorporationIdIn(corporationIds);
            result = converter.convert(entities, dtoClass);
        }
        return result;
    }

    @Override
    public List<BaseBusinessDto> getByCorporationId(UUID corporationId) {
        List<BaseBusinessDto> result = null;
        if (corporationId != null) {
            if (!SecurityUtil.userHavePermissionToCorporation(corporationId)) {
                throw new ClientException(DONT_HAVE_PERMISSION_TO_ACTION_BUSINESS);
            }
            List<BaseBusinessEntity> entities = baseBusinessRepository.findByCorporationId(corporationId);
            result = converter.convert(entities, dtoClass);
        }
        return result;
    }

    @Override
    public BusinessFullModel getFullModelById(UUID id) {
        BusinessFullModel result = new BusinessFullModel();
        if (id == null) {
            throw new ClientException(BUSINESS_ID_EMPTY);
        }
        BaseBusinessDto baseBusinessDto = getById(id);
        if (baseBusinessDto == null) {
            throw new ClientException(BUSINESS_NOT_FOUND);
        }
        result.setBusinessId(id);
        result.setLogoUrl(baseBusinessDto.getLogoUrl());
        result.setName(baseBusinessDto.getName());
        result.setDescription(baseBusinessDto.getDescription());
        result.setAddress(baseBusinessDto.getAddress());
        result.setPhone(baseBusinessDto.getPhone());
        result.setAddPhone(baseBusinessDto.getAddPhone());
        result.setLatitude(baseBusinessDto.getLatitude());
        result.setLongitude(baseBusinessDto.getLongitude());
        result.setTimeZone(baseBusinessDto.getTimeZone());
        result.setRating(commentService.getRating(id));

        if (CollectionUtils.isNotEmpty(baseBusinessDto.getWorkTimes())) {
            result.setWorkTimes(baseBusinessDto.getWorkTimes());
        } else {
            List<WorkTimeDto> emptyList = Collections.emptyList();
            result.setWorkTimes(emptyList);
        }

        if (CollectionUtils.isNotEmpty(baseBusinessDto.getSpaces())) {
            result.setSpaces(baseBusinessDto.getSpaces());
        } else {
            List<WorkingSpaceDto> emptyList = Collections.emptyList();
            result.setSpaces(emptyList);
        }

        List<ServicePriceDto> servicePrices = servicePriceService.getByBusinessId(id);
        if (CollectionUtils.isNotEmpty(servicePrices)) {
            result.setServicePrices(servicePrices);
        } else {
            List<ServicePriceDto> emptyList = Collections.emptyList();
            result.setServicePrices(emptyList);
        }
        List<PackageDto> packages = packageService.getByBusinessId(id);
        if (CollectionUtils.isNotEmpty(packages)) {
            result.setPackages(packages);
        } else {
            List<PackageDto> emptyList = Collections.emptyList();
            result.setPackages(emptyList);
        }
        List<MediaDto> media = mediaService.getByObjectId(id);
        if (CollectionUtils.isNotEmpty(media)) {
            result.setMedia(media);
        } else {
            List<MediaDto> emptyList = Collections.emptyList();
            result.setMedia(emptyList);
        }
        List<CommentFullDto> comments = commentService.findFullByObjectId( id);
        if (CollectionUtils.isNotEmpty(comments)) {
            result.setComments(comments);
        } else {
            List<CommentFullDto> emptyList = Collections.emptyList();
            result.setComments(emptyList);
        }
        List<BaseRecordDto> records = baseRecordService.getByBusinessIdAndStatusRecord(id, StatusRecord.CREATED,
                LocalDate.now().atStartOfDay(), LocalDate.now().atTime(LocalTime.MAX));
        if (CollectionUtils.isNotEmpty(records)) {
            result.setRecords(records);
        } else {
            List<BaseRecordDto> emptyList = Collections.emptyList();
            result.setRecords(emptyList);
        }
        return result;
    }

    @Override
    public void delete(UUID id) {
        if (id == null) {
            throw new ClientException(ID_NOT_SPECIFIED);
        }
        this.currentUserHavePermissionToActionInBusiness(id);

        super.delete(id);
    }

    private void checkCorporationId(BaseBusinessDto business) {
        UUID corporationId = business.getCorporationId();
        if (corporationId == null) {
            throw new ClientException(CORPORATION_ID_REQUIRED_FOR_THIS_ACTION);
        }
        if (!SecurityUtil.userHavePermissionToCorporation(corporationId)) {
            throw new ClientException(DONT_HAVE_PERMISSION_TO_ACTION_BUSINESS);
        }
    }

    private void checkType(BaseBusinessDto dto) {
        if(dto.getServiceType() == null){
            throw new ClientException(SERVICE_TYPE_IS_EMPTY);
        }
    }
}