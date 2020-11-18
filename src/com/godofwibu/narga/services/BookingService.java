package com.godofwibu.narga.services;

import com.godofwibu.narga.entities.Ticket;
import com.godofwibu.narga.entities.User;
import com.godofwibu.narga.formdata.BookingFormData;
import com.godofwibu.narga.repositories.DataAccessLayerException;
import com.godofwibu.narga.repositories.ITicketRepository;
import com.godofwibu.narga.services.exception.ServiceLayerException;
import com.godofwibu.narga.utils.ITransactionTemplate;

public class BookingService implements IBookingService{

	private ITransactionTemplate transactionTemplate;
	private ITicketRepository ticketRepository;
	
	public BookingService(ITransactionTemplate transactionTemplate, ITicketRepository ticketRepository) {
		super();
		this.transactionTemplate = transactionTemplate;
		this.ticketRepository = ticketRepository;
	}

	@Override
	public void book(BookingFormData formData, User user) throws ServiceLayerException {
		try {
			transactionTemplate.execute(() -> {
				for (Integer ticketId : formData.getTicketIds()) {
					Ticket ticket = ticketRepository.findById(ticketId);
					if (ticket.getOwner() != null)
						throw new ServiceLayerException("ticket");
					ticket.setOwner(user);
				}
			});
		} catch (DataAccessLayerException ex) {
			throw new ServiceLayerException("failed to execute operation", ex);
		}
	}
}
