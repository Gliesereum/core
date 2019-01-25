package com.gliesereum.share.common.model.dto.karma.common;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.gliesereum.share.common.databind.json.LocalTimeJsonDeserializer;
import com.gliesereum.share.common.databind.json.LocalTimeJsonSerializer;
import com.gliesereum.share.common.model.dto.DefaultDto;
import com.gliesereum.share.common.model.dto.karma.enumerated.ServiceType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.UUID;

/**
 * @author vitalij
 * @version 1.0
 * @since 12/7/18
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class WorkTimeDto extends DefaultDto {

    @NotNull
    @JsonDeserialize(using = LocalTimeJsonDeserializer.class)
    @JsonSerialize(using = LocalTimeJsonSerializer.class)
    private LocalTime from;

    @NotNull
    @JsonDeserialize(using = LocalTimeJsonDeserializer.class)
    @JsonSerialize(using = LocalTimeJsonSerializer.class)
    private LocalTime to;

    private UUID businessId;

    private Boolean isWork;

    private ServiceType serviceType;

    private DayOfWeek dayOfWeek;
}
