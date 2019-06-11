package com.gliesereum.account.facade.user;

import com.gliesereum.share.common.model.dto.account.user.DetailedUserDto;

import java.util.List;
import java.util.UUID;

/**
 * @author yvlasiuk
 * @version 1.0
 */
public interface UserFacade {

    List<DetailedUserDto> getDetailedByIds(List<UUID> ids);
}
