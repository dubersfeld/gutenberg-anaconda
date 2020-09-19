package com.dub.spring.services;

import com.dub.spring.domain.Payment;

public interface PaymentService {

	public boolean authorizePayment(Payment payment); 
}
