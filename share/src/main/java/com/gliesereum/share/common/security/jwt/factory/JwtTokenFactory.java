package com.gliesereum.share.common.security.jwt.factory;

import com.gliesereum.share.common.security.model.UserAuthentication;

/**
 * @author yvlasiuk
 * @version 1.0
 * @since 19/10/2018
 */
public interface JwtTokenFactory {

    String getJwtToken(UserAuthentication userAuthentication);

    UserAuthentication parseJwtToken(String jwtToken);
}
