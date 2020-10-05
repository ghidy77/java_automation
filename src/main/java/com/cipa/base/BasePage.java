package com.cipa.base;

import com.cipa.driver.SelDriver;

public class BasePage {

	protected SelDriver driver;
	protected static final int DEFAULT_WAIT = 30;

	public BasePage(SelDriver driver) {
		this.driver = driver;
	}

}
