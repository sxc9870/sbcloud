package com.sbcloud.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name = "stock")
public class StockPojo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private int tid;
	@Column(name = "code")
	private String code;
	
	@Column(name = "is_comp")
	private Byte isComp;

	public int getTid() {
		return tid;
	}

	public void setTid(int tid) {
		this.tid = tid;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Byte getIsComp() {
		return isComp;
	}

	public void setIsComp(Byte isComp) {
		this.isComp = isComp;
	}

}
