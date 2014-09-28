package com.applechip.core;

import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

public class PropertyAnnotationAndPlaceholderConfigurer extends PropertyPlaceholderConfigurer {
	private static Logger log = Logger.getLogger(PropertyAnnotationAndPlaceholderConfigurer.class);

	@Override
	protected void processProperties(ConfigurableListableBeanFactory beanFactory, Properties properties)
			throws BeansException {
		super.processProperties(beanFactory, properties);
		// for (String name : beanFactory.getBeanDefinitionNames()) {
		// MutablePropertyValues mpv = beanFactory.getBeanDefinition(name).getPropertyValues();
		// Class clazz = beanFactory.getType(name);
		// if (log.isDebugEnabled())
		// log.debug("Configuring properties for bean=" + name + "[" + clazz + "]");
		// if (clazz != null) {
		// for (PropertyDescriptor property : BeanUtils.getPropertyDescriptors(clazz)) {
		// Method setter = property.getWriteMethod();
		// Method getter = property.getReadMethod();
		// Property annotation = null;
		// if (setter != null && setter.isAnnotationPresent(Property.class)) {
		// annotation = setter.getAnnotation(Property.class);
		// } else if (setter != null && getter != null && getter.isAnnotationPresent(Property.class)) {
		// annotation = getter.getAnnotation(Property.class);
		// }
		// if (annotation != null) {
		// String value = resolvePlaceholder(annotation.key(), properties,
		// SYSTEM_PROPERTIES_MODE_FALLBACK);
		// if (StringUtil.isEmpty(value)) {
		// value = annotation.defaultValue();
		// }
		// if (StringUtil.isEmpty(value)) {
		// throw new BeanConfigurationException("No such property=[" + annotation.key() +
		// "] found in properties.");
		// }
		// if (log.isDebugEnabled())
		// log.debug("setting property=[" + clazz.getName() + "." + property.getName() + "] value=[" +
		// annotation.key() + "=" + value + "]");
		// mpv.addPropertyValue(property.getName(), value);
		// }
		// }
		// for (Field field : clazz.getDeclaredFields()) {
		// if (log.isDebugEnabled())
		// log.debug("examining field=[" + clazz.getName() + "." + field.getName() + "]");
		// if (field.isAnnotationPresent(Property.class)) {
		// Property annotation = field.getAnnotation(Property.class);
		// PropertyDescriptor property = BeanUtils.getPropertyDescriptor(clazz, field.getName());
		// if (property.getWriteMethod() == null) {
		// throw new BeanConfigurationException("setter for property=[" + clazz.getName() + "." +
		// field.getName() + "] not available.");
		// }
		// Object value = resolvePlaceholder(annotation.key(), properties,
		// SYSTEM_PROPERTIES_MODE_FALLBACK);
		// if (value == null) {
		// value = annotation.defaultValue();
		// }
		// if (value == null) {
		// throw new BeanConfigurationException("No such property=[" + annotation.key() +
		// "] found in properties.");
		// }
		// if (log.isDebugEnabled())
		// log.debug("setting property=[" + clazz.getName() + "." + field.getName() + "] value=[" +
		// annotation.key() + "=" + value + "]");
		// mpv.addPropertyValue(property.getName(), value);
		// }
		// }
		// }
		// }
	}
}
