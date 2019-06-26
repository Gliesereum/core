package com.gliesereum.share.common.model.dto.karma.service;

import com.gliesereum.share.common.model.dto.DefaultDto;
import com.gliesereum.share.common.model.dto.base.description.DescriptionReadableDto;
import com.gliesereum.share.common.model.dto.karma.service.descriptions.ServiceClassDescriptionDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

/**
 * @author vitalij
 * @version 1.0
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ServiceClassDto extends DefaultDto {

    @NotEmpty
    private String name;

    private String description;

    private Integer orderIndex;

    private DescriptionReadableDto<ServiceClassDescriptionDto> descriptions = new DescriptionReadableDto<>();

}
