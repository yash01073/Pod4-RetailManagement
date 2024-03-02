package com.cognizant.microservice.service;

import com.cognizant.microservice.model.Vendor;

public interface VendorService {
	public Vendor findByVendorId(long vendorId);
}
