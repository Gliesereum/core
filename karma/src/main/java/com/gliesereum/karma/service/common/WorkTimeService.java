package com.gliesereum.karma.service.common;

import com.gliesereum.karma.model.entity.common.WorkTimeEntity;
import com.gliesereum.share.common.model.dto.karma.common.WorkTimeDto;
import com.gliesereum.share.common.model.dto.karma.enumerated.ServiceType;
import com.gliesereum.share.common.service.DefaultService;

import java.util.List;
import java.util.UUID;

/**
 * @author vitalij
 * @version 1.0
 * @since 12/7/18
 */
public interface WorkTimeService extends DefaultService<WorkTimeDto, WorkTimeEntity> {

    List<WorkTimeDto> getByBusinessServiceId(UUID businessServiceId);

    void delete(UUID id, ServiceType serviceType);
}