package com.gliesereum.karma.model.entity.common;

import com.gliesereum.karma.model.entity.car.ServiceClassCarEntity;
import com.gliesereum.share.common.model.dto.karma.enumerated.CarInteriorType;
import com.gliesereum.share.common.model.dto.karma.enumerated.CarType;
import com.gliesereum.share.common.model.entity.DefaultEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author vitalij
 * @version 1.0
 * @since 12/7/18
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "service_price")
public class ServicePriceEntity extends DefaultEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private int price;

    @Column(name = "service_id")
    private UUID serviceId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_id", insertable = false, updatable = false)
    private ServiceEntity service;

    @Column(name = "business_service_id")
    private UUID businessServiceId;

    @Column(name ="duration")
    private int duration;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "service_class_price",
            joinColumns = {@JoinColumn(name = "price_id", insertable = false, updatable = false)},
            inverseJoinColumns = {@JoinColumn(name = "service_class_car_id", insertable = false, updatable = false)})
    private Set<ServiceClassCarEntity> serviceClass = new HashSet<>();

    @Column(name = "car_body")
    @Enumerated(EnumType.STRING)
    private CarType carBody;

    @Column(name = "interior_type")
    @Enumerated(EnumType.STRING)
    private CarInteriorType interiorType;

}