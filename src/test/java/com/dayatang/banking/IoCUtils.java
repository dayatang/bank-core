package com.dayatang.banking;

import com.dayatang.domain.InstanceFactory;
import com.dayatang.spring.factory.SpringInstanceProvider;

public class IoCUtils {
	public static final void init() {
		SpringInstanceProvider provider = new SpringInstanceProvider("/applicationContext-hibernate.xml");
		InstanceFactory.setInstanceProvider(provider);
	}
}
