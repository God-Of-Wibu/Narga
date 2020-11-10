package com.godofwibu.narga.services;

import com.godofwibu.narga.dto.BookingFormData;
import com.godofwibu.narga.entities.Ticket;
import com.godofwibu.narga.entities.User;
import com.godofwibu.narga.repositories.DataAccessLayerException;
import com.godofwibu.narga.repositories.ITicketRepository;
import com.godofwibu.narga.utils.ITransactionTemplate;

public class BookingService implements IBookingService{

	private ITransactionTemplate transactionTemplate;
	private ITicketRepository ticketRepository;
	
	
	@Override
	public void book(BookingFormData formData, User user) throws ServiceLayerException {
		try {
			transactionTemplate.execute(() -> {
				for (Integer ticketId : formData.getTicketIds()) {
					Ticket ticket = ticketRepository.findById(ticketId);
					ticket.setOwner(user);
				}
			});
		} catch (DataAccessLayerException ex) {
			throw new ServiceLayerException("failed to execute operation", ex);
		}
	}

}
