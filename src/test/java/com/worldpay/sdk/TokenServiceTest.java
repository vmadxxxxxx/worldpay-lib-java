/*
 * Copyright 2013 Worldpay
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 */

package com.worldpay.sdk;

import com.worldpay.api.client.error.exception.WorldpayException;
import com.worldpay.gateway.clearwater.client.core.dto.response.TokenResponse;
import com.worldpay.sdk.util.PropertyUtils;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

/**
 * This is the test class for TokenService.
 */
public class TokenServiceTest {

    /**
     * Service under test
     */
    private TokenService tokenService;

    @Before
    public void setup() {
        tokenService = new WorldpayRestClient(PropertyUtils.serviceKey()).getTokenService();
    }


    /**
     * This test is for validating the Token API to retrieve the existing token.
     */
    @Test
    public void shouldGetValidToken() {
        TokenResponse responseToken = tokenService.get(PropertyUtils.getProperty("tokenId"));
        assertThat(responseToken.getToken(), is(notNullValue()));
        assertThat(responseToken.getPaymentMethod(), is(notNullValue()));
        assertThat("Contains the same token", PropertyUtils.getProperty("tokenId").equals(responseToken.getToken()));
    }

    /**
     * This test is for validating the Token API to retrieve the non existing token.
     * The Token API throws WorldpayException with the custom code TKN_NOT_FOUND
     */
    @Test
    public void getTokenWithEmptyString() {
        try {
            tokenService.get("");
        } catch (WorldpayException e) {
            assertThat("Invalid token", e.getMessage(), is("token id should be empty"));
        }
    }

    /**
     * This test is for validating the Token API to retrieve the non existing token.
     * The Token API throws WorldpayException with the custom code TKN_NOT_FOUND
     */
    @Test
    public void shouldNotGetInValidToken() {
        try {
            tokenService.get("invalid-token");
        } catch (WorldpayException e) {
            assertThat("Invalid token", e.getApiError().getCustomCode(), is("TKN_NOT_FOUND"));
        }
    }
}
