package com.jaguar.functional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.jaguar.BaseJaguarTest;
import com.jaguar.exception.JaguarException;
import com.jaguar.model.Account;
import com.jaguar.model.AccountCollection;
import com.jaguar.model.ExternalAccount;
import com.jaguar.model.PersonCollection;
import com.jaguar.net.ApiResource;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

public class AccountTest extends BaseJaguarTest {
  public static final String ACCOUNT_ID = "acct_123";

  private Account getAccountFixture() throws JaguarException {
    final Account account = Account.retrieve(ACCOUNT_ID, null);
    resetNetworkSpy();
    return account;
  }

  @Test
  public void testCreate() throws JaguarException {
    final Map<String, Object> params = new HashMap<>();
    params.put("type", "custom");

    final Account account = Account.create(params);

    assertNotNull(account);
    verifyRequest(ApiResource.RequestMethod.POST, String.format("/v1/accounts"), params);
  }

  @Test
  public void testList() throws JaguarException {
    final Map<String, Object> params = new HashMap<>();
    params.put("limit", 1);

    final AccountCollection accounts = Account.list(params);

    assertNotNull(accounts);
    verifyRequest(ApiResource.RequestMethod.GET, String.format("/v1/accounts"), params);
  }

  @Test
  public void testRetrieve() throws JaguarException {
    final Account account = Account.retrieve();

    assertNotNull(account);
    verifyRequest(ApiResource.RequestMethod.GET, String.format("/v1/account"));
  }

  @Test
  public void testRetrieveWithId() throws JaguarException {
    final Account account = Account.retrieve(ACCOUNT_ID, null);

    assertNotNull(account);
    verifyRequest(ApiResource.RequestMethod.GET, String.format("/v1/accounts/%s", ACCOUNT_ID));
  }

  @Test
  public void testUpdate() throws JaguarException {
    final Account account = getAccountFixture();

    final Map<String, Object> params = new HashMap<>();
    params.put("business_type", "individual");

    final Account updatedAccount = account.update(params);

    assertNotNull(updatedAccount);
    verifyRequest(
        ApiResource.RequestMethod.POST, String.format("/v1/accounts/%s", account.getId()), params);
  }

  @Test
  public void testDelete() throws JaguarException {
    final Account account = getAccountFixture();

    final Account deletedAccount = account.delete();

    assertNotNull(deletedAccount);
    assertTrue(deletedAccount.getDeleted());
    verifyRequest(
        ApiResource.RequestMethod.DELETE, String.format("/v1/accounts/%s", account.getId()));
  }

  @Test
  public void testReject() throws JaguarException {
    final Account account = getAccountFixture();

    final Map<String, Object> params = new HashMap<>();
    params.put("reason", "fraud");

    Account rejectedAccount = account.reject(params);

    assertNotNull(rejectedAccount);
    verifyRequest(
        ApiResource.RequestMethod.POST,
        String.format("/v1/accounts/%s/reject", account.getId()),
        params);
  }

  @Test
  public void testAccountCreateExternalAccount() throws JaguarException {
    final Account resource = Account.retrieve(ACCOUNT_ID, null);

    final Map<String, Object> params = new HashMap<>();
    params.put("external_account", "tok_123");

    final ExternalAccount ea = resource.getExternalAccounts().create(params);

    assertNotNull(ea);
    verifyRequest(
        ApiResource.RequestMethod.POST,
        String.format("/v1/accounts/%s/external_accounts", resource.getId()),
        params);
  }

  @Test
  public void testPersons() throws JaguarException {
    final Account resource = Account.retrieve(ACCOUNT_ID, null);

    final Map<String, Object> params = new HashMap<>();
    params.put("limit", 1);

    final PersonCollection persons = resource.persons(params);

    assertNotNull(persons);
    verifyRequest(
        ApiResource.RequestMethod.GET,
        String.format("/v1/accounts/%s/persons", resource.getId()),
        params);
  }
}
