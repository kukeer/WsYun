package org.ws.kit.prikit.converter;

public interface BeanConverter<T, V> {
	T toBean(V v);
	V fromBean(T t);
}
