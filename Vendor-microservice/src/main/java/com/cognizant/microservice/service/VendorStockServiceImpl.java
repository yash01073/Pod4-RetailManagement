package com.cognizant.microservice.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cognizant.microservice.exceptions.ProductIdNotFoundException;
import com.cognizant.microservice.exceptions.QuantityLimitExceededException;
import com.cognizant.microservice.model.Vendor;
import com.cognizant.microservice.model.VendorStock;
import com.cognizant.microservice.repo.VendorStockRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional
public class VendorStockServiceImpl implements VendorStockService {
	@Autowired
	private VendorStockRepository vendorStockRepository;
	@Autowired
	private VendorService vendorService;
	
	/**
	 * save vendor stock details
	 */
	@Override
	public VendorStock save(VendorStock vendorStock) {
		log.info("save inside VendorStockServiceImpl started...");
		return vendorStockRepository.save(vendorStock);
	}
	
	//for getting max quantity
	@Override
	public long getMaxQuantity(long productId) {
		log.info("getMaxQuantity inside VendorStockServiceImpl started...");
		return vendorStockRepository.getMaxproductQuantity(productId);
	}
	
	/**
	 * get available vendor with maximum rating for a product
	 */
	@Override
	public Vendor getVendor(long productId, int quantity) {
		log.info("getVendor inside VendorStockServiceImpl started...");
		//product not found exception
		if(vendorStockRepository.isProductAvailable(productId).isEmpty()) {
			throw new ProductIdNotFoundException("Product with the id ["+ productId +"] is not present in the vendors stock");	
		}
		// quantity exception
		long maxQuantity = getMaxQuantity(productId);
		if (quantity > maxQuantity) {
			throw new QuantityLimitExceededException(
					"The Quantity you want is more than the available quantity, Please Enter quantity of the product below limit of ["
					+ maxQuantity + "] units");
		}// assigning vendor with best rating
		List<Long> vendorIdList = vendorStockRepository.getVendorIds(productId, quantity);
		Vendor finalVendor = null;
		VendorStock vendorStock = null;
		double temp = Double.MIN_VALUE;
		for(long id:vendorIdList) {
			Vendor vendor = vendorService.findByVendorId(id);
			if(temp < vendor.getRating()) {
				temp = vendor.getRating();
				finalVendor = vendor;
			}
		}//after vendor is assigned update the stock repository
		if(finalVendor != null) {
			vendorStock = vendorStockRepository.getVendorStock(productId, finalVendor.getVendorId());
			vendorStock.setStockInHand(vendorStock.getStockInHand() - quantity);
			vendorStockRepository.save(vendorStock);
		}else {
			return new Vendor();
		}
		return finalVendor;
	}
	

}
