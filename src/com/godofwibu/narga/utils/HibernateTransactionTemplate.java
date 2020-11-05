package com.godofwibu.narga.utils;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.godofwibu.narga.repositories.DataAccessLayerException;
import com.godofwibu.narga.services.ServiceLayerException;

public class HibernateTransactionTemplate implements ITransactionTemplate{

	private SessionFactory factory;
	private ExceptionConverter exceptionConverter;

	public HibernateTransactionTemplate(SessionFactory factory) {
		super();
		this.factory = factory;
		exceptionConverter = new ExceptionConverter();
	}

	@Override
	public <T> T execute(IOperation<T> operation) throws DataAccessLayerException, ServiceLayerException {
		Transaction transaction = null;
		T result = null;
		try {
			transaction = getSession().getTransaction();
			if (!transaction.isActive()) {
				transaction.begin();
				result = operation.execute();
				transaction.commit();
			} else {
				result = operation.execute();
			}
			return result;
		} catch (ServiceLayerException e) {
			rollback(transaction);
			throw e;
		} catch (Exception e) {
			rollback(transaction);
			throw exceptionConverter.convert(e);
		}
	}

	@Override
	public void execute(IOperationWithOutResult operation) throws DataAccessLayerException, ServiceLayerException {
		Transaction transaction = null;
		try {
			transaction = getSession().getTransaction();
			if (!transaction.isActive()) {
				transaction.begin();
				operation.execute();
				transaction.commit();
			} else {
				operation.execute();
			}
		} catch (ServiceLayerException e) {
			rollback(transaction);
			throw e;
		} catch (Exception e) {
			rollback(transaction);
			throw exceptionConverter.convert(e);
		}
	}
	
	private void rollback(Transaction transaction) {
		if (transaction != null && transaction.isActive())
			transaction.rollback();
	}
	private Session getSession() {
		return factory.getCurrentSession();
	}
}

class ExceptionConverter {
	public DataAccessLayerException convert(Exception e) {
		return new DataAccessLayerException(e);
	}
}
