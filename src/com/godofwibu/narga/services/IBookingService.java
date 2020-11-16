package com.godofwibu.narga.services;

import com.godofwibu.narga.entities.User;
import com.godofwibu.narga.formdata.BookingFormData;

public interface IBookingService {
	void book(BookingFormData formData, User user) throws ServiceLayerException;
}
