package com.gliesereum.account.service.token;

import com.gliesereum.account.model.domain.TokenStoreDomain;

import java.util.UUID;

/**
 * @author yvlasiuk
 * @version 1.0
 * @since 11/10/2018
 */
public interface TokenService {

    TokenStoreDomain getByAccessToken(String accessToken);

    TokenStoreDomain getAndVerify(String accessToken);

    TokenStoreDomain generate(String useId);

    TokenStoreDomain refresh(String accessToken, String refreshToken);

    void revoke(String accessToken);
}