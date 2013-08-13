package com.dayatang.banking.domain;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.dayatang.banking.domain.Account;
import com.dayatang.domain.EntityRepository;

public class TransferFundTest {

	private Account from;
	private Account to;
	
	@Mock
	private EntityRepository repository;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		Account.setRepository(repository);
		from = new Account("account3", 100);
		to = new Account("account4", 10);
	}

	@After
	public void tearDown() throws Exception {
		Account.setRepository(null);
	}

	@Test
	public void testSuccess() {
		from.transferFundTo(to, 50.0);
		assertEquals("转出账户余额应当为50!", 50, from.getBalance(), 0.0001);
		verify(repository).save(from);
		assertEquals("转入账户余额应当为60!", 60, to.getBalance(), 0.0001);
		verify(repository).save(to);
	}

	@Test(expected = BalanceInsufficientException.class)
	public void insufficientBalance() {
		from.transferFundTo(to, 10000.0);
	}
	
	@Test(expected = TransferZeroOrNegativeException.class)
	public void transferZero() {
		from.transferFundTo(to, 0.0);
	}
	
	@Test(expected = TransferZeroOrNegativeException.class)
	public void transferNegative() {
		from.transferFundTo(to, -50.0);
	}
}
