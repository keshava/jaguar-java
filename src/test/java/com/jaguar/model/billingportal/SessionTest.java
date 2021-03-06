package com.jaguar.model.billingportal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.jaguar.BaseJaguarTest;
import com.jaguar.net.ApiResource;
import org.junit.jupiter.api.Test;

public class SessionTest extends BaseJaguarTest {
  @Test
  public void testDeserialize() throws Exception {
    final String data = getResourceAsString("/api_fixtures/billingportal/session.json");
    final Session session = ApiResource.GSON.fromJson(data, Session.class);

    assertNotNull(session);
    assertNotNull(session.getId());
    assertEquals("billing_portal.session", session.getObject());
  }
}
