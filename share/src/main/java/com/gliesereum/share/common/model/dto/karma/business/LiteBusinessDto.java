package com.gliesereum.share.common.model.dto.karma.business;

import com.gliesereum.share.common.model.dto.DefaultDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author yvlasiuk
 * @version 1.0
 */

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class LiteBusinessDto extends DefaultDto {

    private UUID corporationId;

    private String name;

    private String description;

    private String logoUrl;

    private String address;

    private String phone;

    private String addPhone;

    private Double latitude;

    private Double longitude;

    private Integer timeZone;

    private UUID businessCategoryId;

    private LocalDateTime createDate;

    private LocalDateTime updateDate;
}
