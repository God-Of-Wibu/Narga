package com.godofwibu.narga.services;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.godofwibu.narga.dto.*;
import com.godofwibu.narga.entities.Film;
import com.godofwibu.narga.entities.Issue;
import com.godofwibu.narga.entities.Ticket;
import com.godofwibu.narga.formdata.NewIssueFormData;
import com.godofwibu.narga.repositories.DataAccessLayerException;
import com.godofwibu.narga.repositories.IFilmRepository;
import com.godofwibu.narga.repositories.IIssueRepository;
import com.godofwibu.narga.repositories.ITicketRepository;
import com.godofwibu.narga.utils.DateTime;
import com.godofwibu.narga.utils.ITransactionTemplate;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


public class IssueService implements IIssueService {
	
	private ITransactionTemplate transactionTemplate;
	private IIssueRepository issueRepository;
	private IFilmRepository filmRepository;
	private ITicketRepository ticketRepository;
	private Gson gson;
	
	public IssueService(ITransactionTemplate transactionTemplate, IIssueRepository issueRepository,
			IFilmRepository filmRepository, ITicketRepository ticketRepository) {
		super();
		this.transactionTemplate = transactionTemplate;
		this.issueRepository = issueRepository;
		this.filmRepository = filmRepository;
		this.ticketRepository = ticketRepository;
		this.gson = new GsonBuilder().create();
	}

	private final static boolean[] IS_VIP_ROW = {
		false, // A
		false, // B
		false, // C
		true,  // D
		true,  // E
		false, // F
		true,  // G
		false, // I
		false, // J
	};

	@Override
	public void newIssues(NewIssueFormData formData) throws ServiceLayerException {
		try {
			transactionTemplate.execute(() -> {
				Film film = (filmRepository.findById(formData.getFilmId()));
				Issue issue = null;
				for (DateTime dateTime: formData.getDateTimes()) {
					issue = new Issue();
					issue.setFilm(film);
					issue.setDate(dateTime.getDate());
					issue.setTime(dateTime.getTime());
					issueRepository.insert(issue);
					List<Ticket> tickets = new ArrayList<>();
					for (char row = 'A'; row < 'J'; ++row) {
						for (int col = 1; col <=6; ++col) {
							Ticket ticket = new Ticket();
							ticket.setPosition("" + row + col);
							ticket.setIssue(issue);
							ticket.setCost(IS_VIP_ROW[row - 'A'] ? formData.getVipCost() : formData.getBasicCost());
							ticketRepository.insert(ticket);
						}
					}
					
				}
			});
		} catch (DataAccessLayerException e) {
			throw new ServiceLayerException("tạo xuất vé thất bại", e);
		}
	}

	@Override
	public List<Issue> getIssuesInThisWeek() throws ServiceLayerException {
		return null;
	}
	
	

	@Override
	public String getAllIssuesByGivenFilmIdAndDateAsJson(int filmId, Date date) throws ServiceLayerException {
		return transactionTemplate.execute(() -> {
			List<Issue_DTO_API_Get> issues = new ArrayList<>();
			issueRepository.findByFilmIdAndDate(filmId, date).forEach(
					(eFilm) -> issues.add(new Issue_DTO_API_Get(eFilm.getId(), eFilm.getTime())));
			return gson.toJson(issues);
		});
	}

	@Override
	public String getAllTicketOfGivenIssueAsJson(Integer issueId) {
		try {
			return transactionTemplate.execute(() -> {
				List<Ticket_DTO_API_GetByIssue> tickets = new ArrayList<>();
				ticketRepository.findByIssueIdOrderByPositionDesc(issueId).forEach((eTicket) -> {
					tickets.add(new Ticket_DTO_API_GetByIssue(eTicket.getId(), eTicket.getPosition(), eTicket.getOwner() == null, eTicket.getCost()));
				});
				return gson.toJson(tickets);
			});
		}catch (DataAccessLayerException e) {
			throw new ServiceLayerException("execute operation fail", e);
		}
	}

}
