package com.godofwibu.narga.services;

import java.sql.Date;
import java.sql.Time;
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
import com.godofwibu.narga.services.exception.ServiceLayerException;
import com.godofwibu.narga.utils.DateTime;
import com.godofwibu.narga.utils.ITransactionTemplate;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import lombok.AllArgsConstructor;
import lombok.Data;

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

	private final static boolean[] IS_VIP_ROW = { false, // A
			false, // B
			false, // C
			true, // D
			true, // E
			false, // F
			true, // G
			false, // I
			false, // J
	};

	@Override
	public void newIssues(NewIssueFormData formData) throws ServiceLayerException {
		try {
			transactionTemplate.execute(() -> {
				Film film = (filmRepository.findById(formData.getFilmId()));
				Issue issue = null;
				for (DateTime dateTime : formData.getDateTimes()) {
					issue = new Issue();
					issue.setFilm(film);
					issue.setDate(dateTime.getDate());
					issue.setTime(dateTime.getTime());
					issueRepository.save(issue);
					for (char row = 'A'; row < 'J'; ++row) {
						for (int col = 1; col <= 6; ++col) {
							Ticket ticket = new Ticket();
							ticket.setPosition("" + row + col);
							ticket.setIssue(issue);
							ticket.setCost(IS_VIP_ROW[row - 'A'] ? formData.getVipCost() : formData.getBasicCost());
							ticketRepository.save(ticket);
						}
					}

				}
			});
		} catch (DataAccessLayerException e) {
			throw new ServiceLayerException("tạo xuất vé thất bại", e);
		}
	}

	@Override
	public String getAllIssuesByGivenFilmIdAndDateAsJsonArray(int filmId, Date date) throws ServiceLayerException {
		return transactionTemplate.execute(() -> {
			List<IssueJsonData> issueJsonDataArray = new ArrayList<>();
			issueRepository.findByFilmIdAndDate(filmId, date)
					.forEach(issue -> issueJsonDataArray.add(new IssueJsonData(issue.getId(), issue.getTime())));
			return gson.toJson(issueJsonDataArray);
		});
	}

	@Override
	public String getAllTicketsOfGivenIssueAsJsonArray(Integer issueId) {
		return transactionTemplate.execute(() -> {
			List<TicketJsonData> ticketJsonDataArray = new ArrayList<>();
			ticketRepository.findByIssueIdOrderByPositionDesc(issueId)
					.forEach(ticket -> ticketJsonDataArray.add(new TicketJsonData(ticket.getId(), ticket.getPosition(),
							ticket.getOwner() == null, ticket.getCost())));
			return gson.toJson(ticketJsonDataArray);
		});
	}

}
