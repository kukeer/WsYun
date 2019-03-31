package org.ws.kit.prikit.converter;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
@Component
public class BeanConverterFactoryA implements InitializingBean{

	private Map<Class<?>,BeanConverter> converters;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		converters = new HashMap<>(2<<2);
	}
	
	public void register(BeanConverter converter) {
		if(converter != null) {
			converters.put(converter.getClass(), converter);
		}
	}
	
	public <T extends BeanConverter> T get(Class<T> clazz) {
		if(clazz != null) {
			return (T)converters.get(clazz);
		}
		return (T)null;
	}
	
}
