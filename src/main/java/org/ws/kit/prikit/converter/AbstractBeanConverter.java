package org.ws.kit.prikit.converter;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;


public abstract class AbstractBeanConverter<T,V> implements BeanConverter<T,V>,InitializingBean {
	
	@Autowired
	private BeanConverterFactoryA converters;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		if(converters != null) {
			converters.register(this);
		}
	}
}
