package com.gliesereum.share.common.model.dto.auth;

import com.gliesereum.share.common.model.dto.account.UserDto;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author yvlasiuk
 * @version 1.0
 * @since 11/10/2018
 */

@Data
@NoArgsConstructor
public class AuthDto {

    private UserDto user;

    private TokenInfoDto tokenInfo;
}
