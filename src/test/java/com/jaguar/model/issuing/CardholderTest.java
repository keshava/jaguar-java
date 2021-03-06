package com.jaguar.model.issuing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.jaguar.BaseJaguarTest;
import com.jaguar.net.ApiResource;
import org.junit.jupiter.api.Test;

public class CardholderTest extends BaseJaguarTest {
  @Test
  public void testDeserialize() throws Exception {
    final String data = getFixture("/v1/issuing/cardholders/ich_123");
    final Cardholder cardholder = ApiResource.GSON.fromJson(data, Cardholder.class);

    assertNotNull(cardholder);
    assertNotNull(cardholder.getId());
    assertEquals("issuing.cardholder", cardholder.getObject());
  }
}
