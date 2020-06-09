package com.cdxt.app.annotation;

import java.lang.annotation.*;

/**
 * @ClassName: Path
 * @Description: XML路径注解
 * @Author tangxiaohui
 * @Copyright: Copyright (c) 2020
 * @Company: 成都信通网易医疗科技发展有限公司
 * @DateTime 2020/5/28 17:35
 */
@Target({ElementType.TYPE, ElementType.FIELD}) 
@Retention(RetentionPolicy.RUNTIME)    
@Documented    
@Inherited
public @interface Path {
	/**
	 * @author: tangxiaohui
	 * @description: 节点XPATH路径
	 * @date: 2020/5/28 17:37
	 */
	public String path() default ""; 
	
	/**
	 * @Description: 是否是空节点  空节点时其他属性不需要，只需要设置nullFlavor属性
	 * @Author tangxiaohui
	 * @DateTime 2020/5/28 17:39
	 */
	public String nullFlavor() default "";
	
	/**
	 * @Description: 如果不进行设置时默认取text值，设置时取节点的属性值
	 * @Author tangxiaohui
	 * @DateTime 2020/5/28 17:39
	 */
	public String attribute() default "";

	/**
	 * @Description: 日期格式化格式
	 * @Author tangxiaohui
	 * @DateTime 2020/5/28 17:40
	 */
	public String dateformat() default "";

	/**
	 * @Description: 节点attribute为空的时候是否保留该节点或者属性   默认为1：保留   0:删除节点  2：删除属性    只针对返回时起作用
	 * @Author tangxiaohui
	 * @DateTime 2020/5/28 17:40
	 */
	public int attrEmptyVisiable() default 1;
	
	/**
	 * @Description: 节点Text值为空的时候是否保留该节点   默认为true：保留   只针对返回时起作用
	 * @Author tangxiaohui
	 * @DateTime 2020/5/28 17:41
	 */
	public boolean textEmptyVisiable() default true;
}
