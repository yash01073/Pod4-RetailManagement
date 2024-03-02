package com.cognizant.microservice.service;

import com.cognizant.microservice.model.Vendor;
import com.cognizant.microservice.model.VendorStock;

public interface VendorStockService {
	public VendorStock save(VendorStock vendorStock);

	public Vendor getVendor(long productId, int quanity);

	public long getMaxQuantity(long productId);
}
