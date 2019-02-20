package com.gliesereum.share.common.model.dto.karma.business;

import com.gliesereum.share.common.model.dto.DefaultDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author vitalij
 * @version 1.0
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class WorkerDto extends DefaultDto {

    private UUID workerId;

    private String position;

    private UUID workingSpaceId;

    private List<WorkTimeDto> workTimes = new ArrayList<>();

}