package com.jaguar.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.jaguar.BaseJaguarTest;
import com.jaguar.net.ApiResource;
import org.junit.jupiter.api.Test;

public class TransferTest extends BaseJaguarTest {
  @Test
  public void testDeserialize() throws Exception {
    final String data = getFixture("/v1/transfers/tr_123");
    final Transfer transfer = ApiResource.GSON.fromJson(data, Transfer.class);
    assertNotNull(transfer);
    assertNotNull(transfer.getId());
    assertEquals("transfer", transfer.getObject());
    assertNull(transfer.getBalanceTransactionObject());
    assertNull(transfer.getDestinationObject());
    assertNull(transfer.getDestinationPaymentObject());
    assertNull(transfer.getSourceTransactionObject());
  }

  @Test
  public void testDeserializeWithExpansions() throws Exception {
    final String[] expansions = {
      "balance_transaction", "destination", "destination_payment", "source_transaction",
    };
    final String data = getFixture("/v1/transfers/tr_123", expansions);
    final Transfer transfer = ApiResource.GSON.fromJson(data, Transfer.class);
    assertNotNull(transfer);
    final BalanceTransaction balanceTransaction = transfer.getBalanceTransactionObject();
    assertNotNull(balanceTransaction);
    assertNotNull(balanceTransaction.getId());
    assertEquals(transfer.getBalanceTransaction(), balanceTransaction.getId());
    final Account destination = transfer.getDestinationObject();
    assertNotNull(destination);
    assertNotNull(destination.getId());
    assertEquals(transfer.getDestination(), destination.getId());
    final Charge destinationPayment = transfer.getDestinationPaymentObject();
    assertNotNull(destinationPayment);
    assertNotNull(destinationPayment.getId());
    assertEquals(transfer.getDestinationPayment(), destinationPayment.getId());
    final Charge sourceTransaction = transfer.getSourceTransactionObject();
    assertNotNull(sourceTransaction);
    assertNotNull(sourceTransaction.getId());
    assertEquals(transfer.getSourceTransaction(), sourceTransaction.getId());
  }
}
