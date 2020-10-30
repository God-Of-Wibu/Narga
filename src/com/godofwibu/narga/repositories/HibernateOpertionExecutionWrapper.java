package com.godofwibu.narga.repositories;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class HibernateOpertionExecutionWrapper implements IDbOperationExecutionWrapper{

	private SessionFactory factory;

	public HibernateOpertionExecutionWrapper(SessionFactory factory) {
		super();
		this.factory = factory;
	}

	@Override
	public <T> T execute(IOperation<T> operation) throws DataAccessLayerException {
		Transaction transaction = null;
		T result = null;
		try {
			transaction = getSession().getTransaction();
			if (!transaction.isActive()) {
				transaction.begin();
				result = operation.doStuff();
				transaction.commit();
			} else {
				result = operation.doStuff();
			}
			
			return result;
		} catch (RuntimeException e) {
			rollback(transaction);
			
			//TODO: bắt lỗi hibernate và chuyển đổi qua data access layer exception tương ứng
			
			throw new DataAccessLayerException("Failed to execute operation", e);
		}
	}

	@Override
	public void execute(IOperationWithOutResult operation) throws DataAccessLayerException {
		Transaction transaction = null;
		try {
			transaction = getSession().getTransaction();
			if (!transaction.isActive()) {
				transaction.begin();
				operation.doStuff();
				transaction.commit();
			} else {
				operation.doStuff();
			}
		} catch (RuntimeException e) {
			rollback(transaction);
			//TODO: bắt lỗi hibernate và chuyển đổi qua data access layer exception tương ứng
			throw new DataAccessLayerException(e);
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
