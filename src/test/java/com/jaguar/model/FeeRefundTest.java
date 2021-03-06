package com.jaguar.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.jaguar.BaseJaguarTest;
import com.jaguar.net.ApiResource;
import org.junit.jupiter.api.Test;

public class FeeRefundTest extends BaseJaguarTest {
  @Test
  public void testDeserialize() throws Exception {
    final String data = getFixture("/v1/application_fees/fee_123/refunds/fr_123");
    final FeeRefund refund = ApiResource.GSON.fromJson(data, FeeRefund.class);
    assertNotNull(refund);
    assertNotNull(refund.getId());
    assertEquals("fee_refund", refund.getObject());
    assertNull(refund.getBalanceTransactionObject());
    assertNull(refund.getFeeObject());
  }

  @Test
  public void testDeserializeWithExpansions() throws Exception {
    final String[] expansions = {
      "balance_transaction", "fee",
    };
    final String data = getFixture("/v1/application_fees/fee_123/refunds/fr_123", expansions);
    final FeeRefund refund = ApiResource.GSON.fromJson(data, FeeRefund.class);
    assertNotNull(refund);
    final BalanceTransaction balanceTransaction = refund.getBalanceTransactionObject();
    assertNotNull(balanceTransaction);
    assertNotNull(balanceTransaction.getId());
    assertEquals(refund.getBalanceTransaction(), balanceTransaction.getId());
    final ApplicationFee fee = refund.getFeeObject();
    assertNotNull(fee);
    assertNotNull(fee.getId());
    assertEquals(refund.getFee(), fee.getId());
  }
}
