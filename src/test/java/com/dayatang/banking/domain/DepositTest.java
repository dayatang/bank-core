package com.dayatang.banking.domain;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.dayatang.banking.domain.Account;
import com.dayatang.banking.domain.DepositException;
import com.dayatang.domain.EntityRepository;

public class DepositTest {

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
		instance.deposit(50L);
		assertEquals("余额应当为150!", 150, instance.getBalance(), 0.0001);
		verify(repository).save(instance);
	}

	@Test(expected = DepositException.class)
	public void depositNegative() {
		instance.deposit(-5);
	}

	@Test(expected = DepositException.class)
	public void depositZero() {
		instance.deposit(0);
	}
}
