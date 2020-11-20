package com.godofwibu.narga.services;

import java.util.List;

import com.godofwibu.narga.dto.TicketData;
import com.godofwibu.narga.entities.User;
import com.godofwibu.narga.formdata.BookingFormData;
import com.godofwibu.narga.services.exception.ServiceLayerException;

public interface IBookingService {
	void book(BookingFormData formData, User user) throws ServiceLayerException;
	List<TicketData> getAllBookedTickets(User user) throws ServiceLayerException;
}
