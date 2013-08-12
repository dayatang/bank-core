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
		from.save();
		to = new Account("account4", 10);
		to.save();
	}

	@After
	public void tearDown() throws Exception {
		Account.setRepository(null);
	}

	@Test
	public void testSuccess() {
		from.transferFundTo(to, 50L);
		assertEquals("转出账户余额应当为50!", 50, from.getBalance(), 0.0001);
		verify(repository, atLeastOnce()).save(from);
		assertEquals("转入账户余额应当为60!", 60, to.getBalance(), 0.0001);
		verify(repository, atLeastOnce()).save(to);
	}

}
