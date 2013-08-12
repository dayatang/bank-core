package com.dayatang.banking.application.impl;

import static org.hamcrest.CoreMatchers.*;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.dayatang.banking.domain.Account;
import com.dayatang.domain.EntityRepository;

public class BankApplicationImplTest {
	
	private BankApplicationImpl instance = new BankApplicationImpl();
	
	@Mock
	private EntityRepository repository;
	
	private Account account = new Account("abc", 100);
	
	@Before
	public void beforeTest() {
		MockitoAnnotations.initMocks(this);
		Account.setRepository(repository);
	}

	@After
	public void afterTest() {
		Account.setRepository(null);
	}
	
	@Test
	public void testGetAccount() {
		when(repository.get(Account.class, 3L)).thenReturn(account);
		assertThat(instance.getAccount(3), is(account));
	}

	@Test
	public void testCreateAccountString() {
		Account account = instance.createAccount("abc");
		assertThat(account.getName(), is("abc"));
		assertEquals(0.0, account.getBalance(), 0.0001);
		verify(repository).save(account);
	}

	@Test
	public void testCreateAccountStringDouble() {
		Account account = instance.createAccount("abc", 200);
		assertThat(account.getName(), is("abc"));
		assertEquals(200.0, account.getBalance(), 0.0001);
		verify(repository).save(account);
	}

	@Test
	public void testRemoveAccount() {
		instance.removeAccount(account);
		verify(repository).remove(account);
	}
	
	@Test
	public void testDeposit() {
		instance.deposit(account, 50);
		assertEquals(150.0, account.getBalance(), 0.0001);
		verify(repository).save(account);
	}

	@Test
	public void testWithdraw() {
		instance.withdraw(account, 20);
		assertEquals(80.0, account.getBalance(), 0.0001);
		verify(repository).save(account);
	}

	@Test
	public void testTransferFund() {
		Account from = new Account("from", 1000);
		Account to = new Account("to", 100);
		instance.transferFund(from, to, 200);
		assertEquals(800.0, from.getBalance(), 0.0001);
		verify(repository).save(from);
		assertEquals(300.0, to.getBalance(), 0.0001);
		verify(repository).save(to);
	}

}
