package com.gliesereum.share.common.model.dto.lendinggallery.offer;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.gliesereum.share.common.databind.json.LocalDateTimeJsonDeserializer;
import com.gliesereum.share.common.databind.json.LocalDateTimeJsonSerializer;
import com.gliesereum.share.common.model.dto.DefaultDto;
import com.gliesereum.share.common.model.dto.lendinggallery.artbond.ArtBondDto;
import com.gliesereum.share.common.model.dto.lendinggallery.enumerated.OperationType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author vitalij
 * @version 1.0
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class OperationsStoryDto extends DefaultDto {

    private UUID customerId;

    private UUID artBondId;

    private ArtBondDto artBond;

    private Integer sum;

    private String name;

    private String description;

    @JsonDeserialize(using = LocalDateTimeJsonDeserializer.class)
    @JsonSerialize(using = LocalDateTimeJsonSerializer.class)
    private LocalDateTime create;

    private OperationType operationType;

    public OperationsStoryDto(UUID customerId, UUID artBondId, ArtBondDto artBond,
                              Integer sum, String name, String description,
                              LocalDateTime create, OperationType operationType) {
        this.customerId = customerId;
        this.artBondId = artBondId;
        this.artBond = artBond;
        this.sum = sum;
        this.name = name;
        this.description = description;
        this.create = create;
        this.operationType = operationType;
    }
}