package com.dayatang.banking.application.impl;

import com.dayatang.banking.application.BankApplication;
import com.dayatang.banking.domain.Account;

public class BankApplicationImpl implements BankApplication {

	public Account getAccount(long id) {
		return Account.get(id);
	}

	public Account getAccountByName(String name) {
		return Account.getByName(name);
	}

	public Account createAccount(String name) {
		Account account = new Account(name, 0);
		account.save();
		return account;
	}

	public Account createAccount(String name, double balance) {
		Account account = new Account(name, balance);
		account.save();
		return account;
	}

	public void removeAccount(Account account) {
		account.remove();
	}

	public void deposit(Account account, double amount) {
		account.deposit(amount);
	}

	public void withdraw(Account account, double amount) {
		account.withdraw(amount);
	}

	public void transferFund(Account fromAccount, Account toAccount,
			double amount) {
		fromAccount.transferFundTo(toAccount, amount);
	}
}
