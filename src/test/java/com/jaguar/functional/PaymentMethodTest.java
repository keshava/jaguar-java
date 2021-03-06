package com.jaguar.functional;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.jaguar.BaseJaguarTest;
import com.jaguar.exception.JaguarException;
import com.jaguar.model.PaymentMethod;
import com.jaguar.model.PaymentMethodCollection;
import com.jaguar.net.ApiResource;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

public class PaymentMethodTest extends BaseJaguarTest {
  public static final String PAYMENT_METHOD_ID = "pm_123";

  private PaymentMethod getPaymentMethodFixture() throws JaguarException {
    final PaymentMethod payment_method = PaymentMethod.retrieve(PAYMENT_METHOD_ID);
    resetNetworkSpy();
    return payment_method;
  }

  @Test
  public void testCreate() throws JaguarException {
    final Map<String, Object> params = new HashMap<>();
    params.put("type", "card");

    final PaymentMethod payment_method = PaymentMethod.create(params);

    assertNotNull(payment_method);
    verifyRequest(ApiResource.RequestMethod.POST, String.format("/v1/payment_methods"), params);
  }

  @Test
  public void testRetrieve() throws JaguarException {
    final PaymentMethod payment_method = PaymentMethod.retrieve(PAYMENT_METHOD_ID);

    assertNotNull(payment_method);
    verifyRequest(
        ApiResource.RequestMethod.GET, String.format("/v1/payment_methods/%s", PAYMENT_METHOD_ID));
  }

  @Test
  public void testUpdate() throws JaguarException {
    final PaymentMethod payment_method = getPaymentMethodFixture();

    final Map<String, String> metadata = new HashMap<>();
    metadata.put("key", "value");
    final Map<String, Object> params = new HashMap<>();
    params.put("metadata", metadata);

    final PaymentMethod updatedPaymentMethod = payment_method.update(params);

    assertNotNull(updatedPaymentMethod);
    verifyRequest(
        ApiResource.RequestMethod.POST,
        String.format("/v1/payment_methods/%s", payment_method.getId()),
        params);
  }

  @Test
  public void testList() throws JaguarException {
    final Map<String, Object> params = new HashMap<>();
    params.put("customer", "cus_123");
    params.put("limit", 1);
    params.put("type", "card");

    final PaymentMethodCollection payment_methods = PaymentMethod.list(params);

    assertNotNull(payment_methods);
    verifyRequest(ApiResource.RequestMethod.GET, String.format("/v1/payment_methods"));
  }

  @Test
  public void testAttach() throws JaguarException {
    final PaymentMethod payment_method = getPaymentMethodFixture();

    final Map<String, Object> params = new HashMap<>();
    params.put("customer", "cus_123");

    final PaymentMethod attachedPaymentMethod = payment_method.attach(params);

    assertNotNull(attachedPaymentMethod);
    verifyRequest(
        ApiResource.RequestMethod.POST,
        String.format("/v1/payment_methods/%s/attach", payment_method.getId()));
  }

  @Test
  public void testDetach() throws JaguarException {
    final PaymentMethod payment_method = getPaymentMethodFixture();

    final PaymentMethod detachedPaymentMethod = payment_method.detach();

    assertNotNull(detachedPaymentMethod);
    verifyRequest(
        ApiResource.RequestMethod.POST,
        String.format("/v1/payment_methods/%s/detach", payment_method.getId()));
  }
}
