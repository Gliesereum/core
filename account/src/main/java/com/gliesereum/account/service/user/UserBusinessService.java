package com.gliesereum.account.service.user;

import com.gliesereum.account.model.entity.UserBusinessEntity;
import com.gliesereum.share.common.model.dto.account.user.UserBusinessDto;
import com.gliesereum.share.common.service.DefaultService;

import java.util.UUID;

/**
 * @author vitalij
 * @since 12/4/18
 */
public interface UserBusinessService extends DefaultService<UserBusinessDto, UserBusinessEntity> {

    UserBusinessDto getByUserIdAndBusinessId(UUID userId, UUID businessId);
}
