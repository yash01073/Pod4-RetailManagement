package com.cognizant.microservice.model;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Vendor {
	@Id
	@Column(name = "vendor_id")
	private long vendorId;
	@Column(name = "vendorName")
	private String vendorName;
	@Column(name = "delivery_charge")
	private double deliveryCharge;
	private double rating;
}
