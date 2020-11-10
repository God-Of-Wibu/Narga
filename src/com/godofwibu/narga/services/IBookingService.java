package com.godofwibu.narga.services;

import com.godofwibu.narga.dto.BookingFormData;
import com.godofwibu.narga.entities.User;

public interface IBookingService {
	void book(BookingFormData formData, User user) throws ServiceLayerException;
}
