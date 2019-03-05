package com.gliesereum.account.model.repository.jpa.kyc;

import com.gliesereum.account.model.entity.kyc.KycRequestEntity;
import com.gliesereum.share.common.model.dto.account.enumerated.KycRequestType;
import com.gliesereum.share.common.model.dto.account.enumerated.KycStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * @author yvlasiuk
 * @version 1.0
 */
@Repository
public interface KycRequestRepository extends JpaRepository<KycRequestEntity, UUID> {

    boolean existsByKycRequestTypeAndObjectIdAndKycStatusNot(KycRequestType requestType, UUID objectId, KycStatus kycStatus);

    List<KycRequestEntity> findAllByObjectIdIn(List<UUID> objectIds);
}