package com.dayatang.banking.application.impl;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.dayatang.banking.IoCUtils;
import com.dayatang.banking.application.BankApplication;
import com.dayatang.banking.domain.Account;
import com.dayatang.domain.InstanceFactory;

public class BankApplicationImplIntegrationTest {

	private BankApplication instance;
	private Account from;
	private Account to;

	@BeforeClass
	public static void classSetup() throws Exception {
		IoCUtils.init();
	}

	@AfterClass
	public static void classTearDown() throws Exception {
	}
	
	@Before
	public void beforeTest() {
		instance = InstanceFactory.getInstance(BankApplication.class);
		from = instance.createAccount("abc", 1000);
		to = instance.createAccount("xyz", 100);
	}
	
	@After
	public void afterTest() {
		instance.removeAccount(from);
		instance.removeAccount(to);
	}
	
	@Test
	public void testGetAccount() {
		assertThat(instance.getAccount(from.getId()), is(from));
		assertThat(instance.getAccount(to.getId()), is(to));
	}

	@Test
	public void testGetAccountByName() {
		assertThat(instance.getAccountByName("abc"), is(from));
		assertThat(instance.getAccountByName("xyz"), is(to));
	}

	@Test
	public void testCreateAccountString() {
		Account account = instance.createAccount("newAccount");
		account = instance.getAccount(account.getId());
		assertNotNull(account);
		assertEquals("newAccount", account.getName());
		assertEquals(0.0, account.getBalance(), 0.0001);
		instance.removeAccount(account);
	}

	@Test
	public void testCreateAccountStringDouble() {
		Account account = instance.createAccount("newAccount", 250.5);
		account = instance.getAccount(account.getId());
		assertNotNull(account);
		assertEquals("newAccount", account.getName());
		assertEquals(250.5, account.getBalance(), 0.0001);
		instance.removeAccount(account);
	}

	@Test
	public void testRemoveAccount() {
		Account account = instance.createAccount("newAccount", 250.5);
		assertNotNull(instance.getAccount(account.getId()));
		instance.removeAccount(account);
		assertNull(instance.getAccount(account.getId()));
	}

	@Test
	public void testDeposit() {
		instance.deposit(from, 20);
		assertEquals(1020.0, from.getBalance(), 0.0001);
	}

	@Test
	public void testWithdraw() {
		instance.withdraw(from, 20);
		assertEquals(980, from.getBalance(), 0.0001);
	}

	@Test
	public void testTransferFund() {
		instance.transferFund(from, to, 200);
		assertEquals(800, from.getBalance(), 0.0001);
		assertEquals(300, to.getBalance(), 0.0001);
	}

}
