package com.jaguar.functional;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.jaguar.BaseJaguarTest;
import com.jaguar.exception.JaguarException;
import com.jaguar.model.Refund;
import com.jaguar.model.RefundCollection;
import com.jaguar.net.ApiResource;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

public class RefundTest extends BaseJaguarTest {
  public static final String REFUND_ID = "re_123";

  private Refund getRefundFixture() throws JaguarException {
    final Refund refund = Refund.retrieve(REFUND_ID);
    resetNetworkSpy();
    return refund;
  }

  @Test
  public void testCreate() throws JaguarException {
    final Map<String, Object> params = new HashMap<>();
    params.put("charge", "ch_123");

    final Refund refund = Refund.create(params);

    assertNotNull(refund);
    verifyRequest(ApiResource.RequestMethod.POST, String.format("/v1/refunds"), params);
  }

  @Test
  public void testRetrieve() throws JaguarException {
    final Refund refund = Refund.retrieve(REFUND_ID);

    assertNotNull(refund);
    verifyRequest(ApiResource.RequestMethod.GET, String.format("/v1/refunds/%s", REFUND_ID));
  }

  @Test
  public void testUpdate() throws JaguarException {
    final Refund refund = getRefundFixture();

    Map<String, String> metadata = new HashMap<>();
    metadata.put("key", "value");
    Map<String, Object> params = new HashMap<>();
    params.put("metadata", metadata);

    final Refund updatedRefund = refund.update(params);

    assertNotNull(updatedRefund);
    verifyRequest(
        ApiResource.RequestMethod.POST, String.format("/v1/refunds/%s", refund.getId()), params);
  }

  @Test
  public void testList() throws JaguarException {
    final Map<String, Object> params = new HashMap<>();
    params.put("limit", 1);

    final RefundCollection refunds = Refund.list(params);

    assertNotNull(refunds);
    verifyRequest(ApiResource.RequestMethod.GET, String.format("/v1/refunds"), params);
  }
}
