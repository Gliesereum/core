package com.gliesereum.karma.model.entity.business;

import com.gliesereum.share.common.model.entity.description.BaseDescriptionEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author yvlasiuk
 * @version 1.0
 */

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "business_category_description")
public class BusinessCategoryDescriptionEntity extends BaseDescriptionEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;
}
