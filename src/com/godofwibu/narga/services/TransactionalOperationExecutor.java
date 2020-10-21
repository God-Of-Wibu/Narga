package com.godofwibu.narga.services;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class TransactionalOperationExecutor {

	private SessionFactory factory;

	public TransactionalOperationExecutor(SessionFactory factory) {
		super();
		this.factory = factory;
	}

	public <T> T execute(ITransactionalOperation<T> operation) throws ServiceLayerException {
		Transaction transaction = null;
		T result = null;
		try {
			transaction = getSession().beginTransaction();
			result = operation.doOperation();
			transaction.commit();
			return result;
		} catch (Exception e) {
			if (transaction != null && transaction.isActive())
				transaction.rollback();
			throw new ServiceLayerException(e.getMessage(), e);
		}
	}

	public void execute(ITransactionalOperationWithOutResult operation) throws ServiceLayerException {
		Transaction transaction = null;
		try {
			transaction = getSession().beginTransaction();
			operation.doOperation();
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null && transaction.isActive())
				transaction.rollback();
			throw new ServiceLayerException(e.getMessage(), e);
		}
	}

	private Session getSession() {
		return factory.getCurrentSession();
	}
}
