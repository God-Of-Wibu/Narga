package com.godofwibu.narga.services;

import java.util.ArrayList;
import java.util.List;

import com.godofwibu.narga.dto.TicketData;
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
		this.transactionTemplate = transactionTemplate;
		this.ticketRepository = ticketRepository;
	}

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

	@Override
	public List<TicketData> getAllBookedTickets(User user) throws ServiceLayerException {
		try {
			return transactionTemplate.execute(() -> {
				List<TicketData> ticketDatas = new ArrayList<>();
				ticketRepository.findByUserId(user.getId()).forEach(ticket -> {
					TicketData ticketData = new TicketData();
					ticketData.setPosition(ticket.getPosition());
					ticketData.setCost(ticket.getCost());
					ticketData.setFilmTitle(ticket.getIssue().getFilm().getTitle());
					ticketData.setDate(ticket.getIssue().getDate());
					ticketData.setStartingTime(ticket.getIssue().getTime());
					ticketDatas.add(ticketData);
				});
				return ticketDatas;
			});
		} catch (DataAccessLayerException e) {
			throw new ServiceLayerException("query fail", e);
		}
	}
}
