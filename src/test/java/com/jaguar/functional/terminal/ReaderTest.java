package com.jaguar.functional.terminal;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.jaguar.BaseJaguarTest;
import com.jaguar.exception.JaguarException;
import com.jaguar.model.terminal.Reader;
import com.jaguar.model.terminal.ReaderCollection;
import com.jaguar.net.ApiResource;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

public class ReaderTest extends BaseJaguarTest {
  public static final String READER_ID = "rdr_123";

  @Test
  public void testCreate() throws JaguarException {
    final Map<String, Object> params = new HashMap<>();
    params.put("registration_code", "rabbit-analyzed-pig");
    params.put("label", "device");

    final Reader reader = Reader.create(params);

    assertNotNull(reader);
    verifyRequest(ApiResource.RequestMethod.POST, String.format("/v1/terminal/readers"), params);
  }

  @Test
  public void testRetrieve() throws JaguarException {
    final Reader reader = Reader.retrieve(READER_ID);

    assertNotNull(reader);
    verifyRequest(
        ApiResource.RequestMethod.GET, String.format("/v1/terminal/readers/%s", READER_ID));
  }

  @Test
  public void testUpdate() throws JaguarException {
    final Reader reader = Reader.retrieve(READER_ID);

    final Map<String, Object> params = new HashMap<>();
    params.put("label", "new_label");

    final Reader updatedReader = reader.update(params);

    assertNotNull(updatedReader);
    verifyRequest(
        ApiResource.RequestMethod.POST,
        String.format("/v1/terminal/readers/%s", reader.getId()),
        params);
  }

  @Test
  public void testList() throws JaguarException {
    final Map<String, Object> params = new HashMap<>();
    params.put("limit", 1);

    ReaderCollection resources = Reader.list(params);

    assertNotNull(resources);
    verifyRequest(ApiResource.RequestMethod.GET, String.format("/v1/terminal/readers"), params);
  }

  @Test
  public void testDelete() throws JaguarException {
    final Reader reader = Reader.retrieve(READER_ID);

    final Reader deletedReader = reader.delete();

    assertNotNull(deletedReader);
    assertTrue(deletedReader.getDeleted());
    verifyRequest(
        ApiResource.RequestMethod.DELETE, String.format("/v1/terminal/readers/%s", reader.getId()));
  }
}
