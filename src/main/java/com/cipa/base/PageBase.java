package com.cipa.base;

import com.cipa.driver.SelDriver;

public class PageBase {

	protected SelDriver driver;
	protected static final int DEFAULT_WAIT = 30;

	public PageBase(SelDriver driver) {
		this.driver = driver;
	}

}
