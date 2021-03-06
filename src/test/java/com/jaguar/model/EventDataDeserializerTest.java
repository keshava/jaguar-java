package com.jaguar.model;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.jaguar.BaseJaguarTest;
import com.jaguar.net.ApiResource;
import org.junit.jupiter.api.Test;

public class EventDataDeserializerTest extends BaseJaguarTest {

  @Test
  public void testEventAccountApplicationDeauthorized() throws Exception {
    final String data = getResourceAsString("/api_fixtures/account_application_deauthorized.json");
    final Event event = ApiResource.GSON.fromJson(data, Event.class);
    assertNotNull(event);
    assertNotNull(event.getId());
    assertNotNull(event.getData());

    // Using deserializeUnsafe() because the fixture uses an older API version
    assertNotNull(event.getDataObjectDeserializer().deserializeUnsafe());

    final Application application =
        (Application) event.getDataObjectDeserializer().deserializeUnsafe();
    assertNotNull(application);
    assertNotNull(application.getId());
  }
}
