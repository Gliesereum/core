package com.gliesereum.karma.service.record;

import com.gliesereum.karma.model.entity.record.BaseRecordEntity;
import com.gliesereum.share.common.model.dto.karma.enumerated.StatusPay;
import com.gliesereum.share.common.model.dto.karma.enumerated.StatusProcess;
import com.gliesereum.share.common.model.dto.karma.enumerated.StatusRecord;
import com.gliesereum.share.common.model.dto.karma.record.BaseRecordDto;
import com.gliesereum.share.common.model.dto.karma.record.RecordsSearchDto;
import com.gliesereum.share.common.model.dto.karma.record.ReportFilterDto;
import com.gliesereum.share.common.service.DefaultService;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * @author vitalij
 * @version 1.0
 * @since 12/7/18
 */
public interface BaseRecordService extends DefaultService<BaseRecordDto, BaseRecordEntity> {

    List<BaseRecordDto> getByParamsForClient(RecordsSearchDto search);

    List<BaseRecordDto> getByBusinessIdAndStatusRecord(UUID businessId, StatusRecord status, LocalDateTime from, LocalDateTime to);

    List<BaseRecordDto> getByParamsForBusiness(RecordsSearchDto search);

    BaseRecordDto updateWorkingSpace(UUID idRecord, UUID workingSpaceId);

    BaseRecordDto updateStatusProgress(UUID idRecord, StatusProcess status);

    BaseRecordDto canceledRecord(UUID idRecord);

    BaseRecordDto updateTimeRecord(UUID idRecord, Long beginTime);

    BaseRecordDto getFreeTimeForRecord(BaseRecordDto dto);

    List<BaseRecordDto> getAllByUser(UUID businessCategoryId);

    BaseRecordDto createFromBusiness(BaseRecordDto dto);

    BaseRecordDto getFullModelById(UUID id);

    BaseRecordDto updateStatusPay(UUID idRecord, StatusPay status);

    void getReport(HttpServletResponse response, ReportFilterDto filter);

    List<BaseRecordDto> convertListEntityToDto(List<BaseRecordEntity> entities);

    void setFullModelRecord(List<BaseRecordDto> list);
}
