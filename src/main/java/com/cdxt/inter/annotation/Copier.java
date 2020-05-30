package com.cdxt.inter.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * @ClassName: Copier
 * @Description:
 * @Author tangxiaohui
 * @Copyright: Copyright (c) 2020
 * @Company: 成都信通网易医疗科技发展有限公司
 * @DateTime 2020/5/30 0030 10:02
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)    
@Documented    
@Inherited
public @interface Copier {
	
	/**
	 * 属性类型
	 */
	public Class<?> parameterType();
	
	/**
	 * 泛型类型
	 */
	public Class<?> genericType() default Object.class; 
}
