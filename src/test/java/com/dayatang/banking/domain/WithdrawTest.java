package com.dayatang.banking.domain;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.dayatang.banking.domain.Account;
import com.dayatang.banking.domain.BalanceInsufficientException;
import com.dayatang.domain.EntityRepository;

public class WithdrawTest {

	private Account instance;
	
	@Mock
	private EntityRepository repository;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		Account.setRepository(repository);
		instance = new Account("account2", 100);
	}

	@After
	public void tearDown() throws Exception {
		Account.setRepository(null);
	}

	@Test
	public void testSuccess() {
		instance.withdraw(20.0);
		assertEquals("余额应当为80!", 80, instance.getBalance(), 0.0001);
		verify(repository).save(instance);
	}

	@Test(expected = BalanceInsufficientException.class)
	public void balanceInsufficient() {
		instance.withdraw(200.0);
	}

	@Test(expected = WithdrawZeroOrNegativeException.class)
	public void withdrawZero() {
		instance.withdraw(0.0);
	}

	@Test(expected = WithdrawZeroOrNegativeException.class)
	public void withdrawNegative() {
		instance.withdraw(-50.0);
	}
}
