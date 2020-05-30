
package com.cdxt.inter.model;

import lombok.Data;

import java.io.Serializable;
@Data
public class BirthAge implements Serializable{

	private static final long serialVersionUID = 3366773778728575451L;

	private int age;
	
	private String dw;//1:年     2：月          3：日
	
	public BirthAge(int age,String dw){
		this.age = age;
		this.dw = dw;
	}
}

