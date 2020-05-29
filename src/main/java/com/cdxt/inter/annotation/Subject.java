package com.cdxt.inter.annotation;

import java.lang.annotation.*;

/**
 * @ClassName: Subject
 * @Description: XML子节点注解标识
 * @Author tangxiaohui
 * @Copyright: Copyright (c) 2020
 * @Company: 成都信通网易医疗科技发展有限公司
 * @DateTime 2020/5/28 17:41
 */
@Target({ElementType.FIELD}) 
@Retention(RetentionPolicy.RUNTIME)    
@Documented    
@Inherited
public @interface Subject {

	/**
	 * @Description: 子节点root节点路径
	 * @Author tangxiaohui
	 * @DateTime 2020/5/28 17:42
	 */
	public String path() default "";
	
	/**
	 * @Description: 是否有多个节点  默认false
	 * @Author tangxiaohui
	 * @DateTime 2020/5/28 17:42
	 */
	public boolean mult() default false; 
}
