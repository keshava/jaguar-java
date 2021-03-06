package com.jaguar.functional;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.jaguar.BaseJaguarTest;
import com.jaguar.exception.JaguarException;
import com.jaguar.model.InvoiceItem;
import com.jaguar.model.InvoiceItemCollection;
import com.jaguar.net.ApiResource;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

public class InvoiceItemTest extends BaseJaguarTest {
  public static final String INVOICE_ITEM_ID = "ii_123";

  private InvoiceItem getItemFixture() throws JaguarException {
    final InvoiceItem item = InvoiceItem.retrieve(INVOICE_ITEM_ID);
    resetNetworkSpy();
    return item;
  }

  @Test
  public void testCreate() throws JaguarException {
    final Map<String, Object> params = new HashMap<>();
    params.put("amount", 100);
    params.put("currency", "usd");
    params.put("customer", "cus_123");

    final InvoiceItem item = InvoiceItem.create(params);

    assertNotNull(item);
    verifyRequest(ApiResource.RequestMethod.POST, String.format("/v1/invoiceitems"), params);
  }

  @Test
  public void testRetrieve() throws JaguarException {
    final InvoiceItem item = InvoiceItem.retrieve(INVOICE_ITEM_ID);

    assertNotNull(item);
    verifyRequest(
        ApiResource.RequestMethod.GET, String.format("/v1/invoiceitems/%s", INVOICE_ITEM_ID));
  }

  @Test
  public void testUpdate() throws JaguarException {
    final InvoiceItem resource = getItemFixture();

    final Map<String, String> metadata = new HashMap<>();
    metadata.put("key", "value");
    final Map<String, Object> params = new HashMap<>();
    params.put("metadata", metadata);

    final InvoiceItem updatedItem = resource.update(params);

    assertNotNull(updatedItem);
    verifyRequest(
        ApiResource.RequestMethod.POST,
        String.format("/v1/invoiceitems/%s", resource.getId()),
        params);
  }

  @Test
  public void testList() throws JaguarException {
    final Map<String, Object> params = new HashMap<>();
    params.put("limit", 1);

    InvoiceItemCollection items = InvoiceItem.list(params);

    assertNotNull(items);
    verifyRequest(ApiResource.RequestMethod.GET, String.format("/v1/invoiceitems"), params);
  }
}
