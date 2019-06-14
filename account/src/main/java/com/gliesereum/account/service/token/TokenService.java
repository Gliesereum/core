package com.gliesereum.account.service.token;

import com.gliesereum.account.model.domain.TokenStoreDomain;

/**
 * @author yvlasiuk
 * @version 1.0
 */
public interface TokenService {

    TokenStoreDomain getByAccessToken(String accessToken);

    TokenStoreDomain getByRefreshToken(String refreshToken);

    TokenStoreDomain getAndVerify(String accessToken);

    TokenStoreDomain generate(String userId);

    TokenStoreDomain refresh(String refreshToken);

    void revoke(String accessToken);
}
