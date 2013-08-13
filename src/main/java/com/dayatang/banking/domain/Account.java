package com.dayatang.banking.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.dayatang.domain.AbstractEntity;
import com.dayatang.domain.QuerySettings;

@Entity
@Table(name = "ACCOUNTS")
public class Account extends AbstractEntity {

	private static final long serialVersionUID = -4876922985402450066L;

	@Column(nullable = false)
	private String name;
	
	private double balance;

	public Account() {
	}

	public Account(String name, double balance) {
		this.name = name;
		this.balance = balance;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getBalance() {
		return balance;
	}

	private void setBalance(double amount) {
		this.balance = amount;
	}

	public void deposit(double amount) {
		if (amount <= 0) {
			throw new DepositZeroOrNegativeException();
		}
		balance += amount;
		save();
	}

	public void withdraw(double amount) {
		if (amount <= 0) {
			throw new WithdrawZeroOrNegativeException();
		}
		if (amount > balance) {
			throw new BalanceInsufficientException();
		}
		balance -= amount;
		save();
	}

	public void transferFundTo(Account toAccount, double amount) {
		if (amount <= 0) {
			throw new TransferZeroOrNegativeException();
		}
		if (amount > balance) {
			throw new BalanceInsufficientException();
		}
		this.setBalance(balance - amount);
		this.save();
		toAccount.setBalance(toAccount.getBalance() + amount);
		toAccount.save();
	}
	
	public static Account get(long id) {
		return getRepository().get(Account.class, id);
	}
	
	public static Account getByName(String name) {
		QuerySettings<Account> querySettings = QuerySettings.create(Account.class)
				.eq("name", name);
		List<Account> accounts = getRepository().find(querySettings);
		return accounts.isEmpty() ? null : accounts.get(0);
	}
	
	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof Account))
			return false;
		Account castOther = (Account) other;
		return new EqualsBuilder().append(name, castOther.name).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37).append(name).toHashCode();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("name", name)
				.append("balance", balance).toString();
	}

}
