package model;

import java.util.Arrays;

import databeans.CustomerBean;

import org.genericdao.*;

public class CustomerDAO extends GenericDAO<CustomerBean> {

	public CustomerDAO(String tableName, ConnectionPool pool) throws DAOException {
		super(CustomerBean.class, tableName, pool);
	}
	public CustomerBean read(String username) throws RollbackException {
		CustomerBean[] customer = match(MatchArg.equals("username", username));
		if (customer.length > 0)
			return customer[0];
		else
			return null;

	}
	public CustomerBean[] getCustomer() throws RollbackException {
		CustomerBean[] customers = match();
		Arrays.sort(customers); 
		return customers;
	}
	public CustomerBean getCustomer(int id) throws RollbackException {
		CustomerBean[] customers = match(MatchArg.equals("customerId", id));
		return customers[0];
	}
	public CustomerBean getCustomer(String userName) throws RollbackException {
		CustomerBean[] customers = match(MatchArg.equals("username", userName));
		return customers[0];
	}
	
	public void create(CustomerBean newCustomer) throws RollbackException {
		if (read(newCustomer.getUsername())!=null){
			return;
		}
		try {
			Transaction.begin();
			createAutoIncrement(newCustomer);
			Transaction.commit();
		} finally {
			if (Transaction.isActive()) Transaction.rollback();
		}
	}

	public void setPassword(String username, String password) throws RollbackException {
        try {
        	Transaction.begin();
			CustomerBean customer = read(username);
			
			if (customer == null) {
				throw new RollbackException("User "+username+" no longer exists");
			}
			
			customer.setPassword(password);
			
			update(customer);
			Transaction.commit();
		} finally {
			if (Transaction.isActive()) Transaction.rollback();
		}
	}
	public void updateCash(int customerId, long amount) throws RollbackException {
        try {
        	Transaction.begin();
			CustomerBean customer = read(customerId);
			
			if (customer == null) {
				throw new RollbackException("User with Id "+customerId+" no longer exists");
			}
			
			customer.setCash(customer.getCash()+amount);
			
			update(customer);
			Transaction.commit();
		} finally {
			if (Transaction.isActive()) Transaction.rollback();
		}
	}
}
