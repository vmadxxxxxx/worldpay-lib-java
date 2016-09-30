package com.worldpay.sdk.integration;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.As;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;
import com.tngtech.jgiven.annotation.ProvidedScenarioState;
import com.worldpay.api.client.common.enums.OrderStatus;
import com.worldpay.gateway.clearwater.client.core.dto.CurrencyCode;
import com.worldpay.gateway.clearwater.client.core.dto.common.DeliveryAddress;
import com.worldpay.gateway.clearwater.client.core.dto.response.CardResponse;
import com.worldpay.gateway.clearwater.client.core.dto.response.OrderResponse;
import com.worldpay.gateway.clearwater.client.core.exception.WorldpayException;
import com.worldpay.gateway.clearwater.client.ui.dto.order.Transaction;
import com.worldpay.sdk.util.PropertyUtils;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class AssertOfferResponseStage extends Stage<AssertOfferResponseStage> {

    @ExpectedScenarioState
    private OrderResponse orderResponse;

    @ProvidedScenarioState
    private String orderCode;

    @ExpectedScenarioState
    private WorldpayException worldpayException;

    @ExpectedScenarioState
    private Transaction transaction;

    public AssertOfferResponseStage theErrorMessageIs(String errorMessage) {
        assertThat("Invalid token", worldpayException.getMessage(), is(errorMessage));
        return self();
    }

    public AssertOfferResponseStage theApiErrorCustomCodeIs(String customCode) {
        assertThat("Invalid token", worldpayException.getApiError().getCustomCode(), is(customCode));
        return self();
    }

    public AssertOfferResponseStage theOrderCodeIsNotNull() {
        assertThat("Response code", orderResponse.getOrderCode(), is(notNullValue()));
        return self();
    }

    public AssertOfferResponseStage theAmountIs(int amount) {
        assertThat("Amount", orderResponse.getAmount(), is(amount));
        return self();
    }

    public AssertOfferResponseStage theCustomerIdentifiersIsNotNull() {
        assertThat("Customer identifier", orderResponse.getKeyValueResponse().getCustomerIdentifiers(), is(notNullValue()));
        return self();
    }

    @As("And the card type is '$'")
    public AssertOfferResponseStage theCardTypeIs(String cardType) {
        assertThat("Card Type", ((CardResponse) orderResponse.getPaymentResponse()).getCardType(),
                   equalTo(cardType));
        return self();
    }

    public AssertOfferResponseStage theDeliveryAddressIs(DeliveryAddress deliveryAddress) {
        assertThat("Delivery address",orderResponse.getDeliveryAddress(), equalTo(deliveryAddress));
        return self();
    }

    public AssertOfferResponseStage theShopperEmailAddressIs(String emailAddress) {
        assertThat("shopper email", orderResponse.getShopperEmailAddress(), equalTo(emailAddress));
        return self();
    }

    public AssertOfferResponseStage theResponseIsNotNull() {
        assertThat("Response", orderResponse, is(notNullValue()));
        return self();
    }

    public AssertOfferResponseStage theCurrencyCodeIs(CurrencyCode currencyCode) {
        assertThat("Settlement currency", orderResponse.getSettlementCurrency(), equalTo(currencyCode));
        return self();
    }

    public AssertOfferResponseStage theOrderCodeIsTheSame() {
        assertThat("Order code", orderResponse.getOrderCode(), equalTo(orderCode));
        return self();
    }

    @As("the payment status is 'SUCCESS'")
    public AssertOfferResponseStage thePaymentStatusIsSuccess() {
        assertThat("Order Status", orderResponse.getPaymentStatus(), equalTo(OrderStatus.SUCCESS.toString()));
        return self();
    }

    @As("the payment status is 'AUTHORIZED'")
    public AssertOfferResponseStage thePaymentStatusIsAuthorized() {
        assertThat("Order status", orderResponse.getPaymentStatus(), equalTo(OrderStatus.AUTHORIZED.toString()));
        return self();
    }

    public AssertOfferResponseStage aWorldpayExceptionIsThrown() {
        assertThat(worldpayException, is(notNullValue()));
        return self();
    }

    public AssertOfferResponseStage theRedirectUrlIsNotNull() {
        assertThat("Redirect URL", orderResponse.getRedirectURL(), is(notNullValue()));
        return self();
    }

    public AssertOfferResponseStage theRedirectUrlContainsTheOrderCode() {
        assertThat("Payment status", orderResponse.getRedirectURL(), containsString(orderResponse.getOrderCode()));
        return self();
    }

    public AssertOfferResponseStage theTransactionIsNotNull() {
        assertThat("Response", transaction, is(notNullValue()));
        return self();
    }

    public AssertOfferResponseStage theOrderResponseIsNotNull() {
        assertThat("Order Response", transaction.getOrderResponse(), notNullValue());
        return self();
    }

    public AssertOfferResponseStage theAuthorizedAmountInTheOrderResponseIs(int amount) {
        assertThat("Authorized amount", transaction.getOrderResponse().getAuthorizedAmount(), is(amount));
        return self();
    }

    @As("the payment status in the order response is 'SUCCESS'")
    public AssertOfferResponseStage thePaymentStatusInTheOrderResponseIsSuccess() {
        assertThat("Order Status", transaction.getOrderResponse().getPaymentStatus(), equalTo(OrderStatus.SUCCESS.toString()));
        return self();
    }

    @As("the payment status in the order response is 'CANCELLED'")
    public AssertOfferResponseStage thePaymentStatusInTheOrderResponseIsCancelled() {
        assertThat("Order Status", transaction.getOrderResponse().getPaymentStatus(), equalTo(OrderStatus.CANCELLED.toString()));
        return self();
    }

    public AssertOfferResponseStage theAmountInTheOrderResponseIs(int amount) {
        assertThat("Order Status", transaction.getOrderResponse().getAmount(), equalTo(amount));
        return self();
    }
}
