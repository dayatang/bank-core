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
import com.dayatang.banking.domain.BalanceInsufficientException;
import com.dayatang.banking.domain.DepositZeroOrNegativeException;
import com.dayatang.banking.domain.TransferZeroOrNegativeException;
import com.dayatang.banking.domain.WithdrawZeroOrNegativeException;
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
	public void testCreateAccountWithName() {
		Account account = instance.createAccount("abc");
		assertThat(account.getName(), is("abc"));
		assertEquals(0.0, account.getBalance(), 0.0001);
		verify(repository).save(account);
	}

	@Test
	public void testCreateAccountWithNameAndBalance() {
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

	@Test(expected = DepositZeroOrNegativeException.class)
	public void depositZero() {
		instance.deposit(account, 0.0);
	}

	@Test(expected = DepositZeroOrNegativeException.class)
	public void depositNegative() {
		instance.deposit(account, -5.0);
	}

	@Test
	public void testWithdraw() {
		instance.withdraw(account, 20);
		assertEquals(80.0, account.getBalance(), 0.0001);
		verify(repository).save(account);
	}

	@Test(expected = BalanceInsufficientException.class)
	public void balanceInsufficient() {
		instance.withdraw(account, 2000000.0);
	}

	@Test(expected = WithdrawZeroOrNegativeException.class)
	public void withdrawZero() {
		instance.withdraw(account, 0.0);
	}

	@Test(expected = WithdrawZeroOrNegativeException.class)
	public void withdrawNegative() {
		instance.withdraw(account, -50.0);
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

	@Test(expected = BalanceInsufficientException.class)
	public void insufficientBalance() {
		Account from = new Account("from", 1000);
		Account to = new Account("to", 100);
		instance.transferFund(from, to, 2000);
		verify(repository, never()).save(from);
		verify(repository, never()).save(to);
		assertEquals(1000.0, from.getBalance(), 0.0001);
		assertEquals(100.0, to.getBalance(), 0.0001);
	}
	
	@Test(expected = TransferZeroOrNegativeException.class)
	public void transferZero() {
		Account from = new Account("from", 1000);
		Account to = new Account("to", 100);
		instance.transferFund(from, to, 0.0);
		verify(repository, never()).save(from);
		verify(repository, never()).save(to);
		assertEquals(1000.0, from.getBalance(), 0.0001);
		assertEquals(100.0, to.getBalance(), 0.0001);
		System.out.println("===========================");
	}
	
	@Test(expected = TransferZeroOrNegativeException.class)
	public void transferNegative() {
		Account from = new Account("from", 1000);
		Account to = new Account("to", 100);
		instance.transferFund(from, to, -200.0);
		verify(repository, never()).save(from);
		verify(repository, never()).save(to);
		assertEquals(1000.0, from.getBalance(), 0.0001);
		assertEquals(100.0, to.getBalance(), 0.0001);
	}

}
