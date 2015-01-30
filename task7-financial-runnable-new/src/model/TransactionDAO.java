package model;

import java.util.Arrays;
import java.util.Date;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.GenericDAO;
import org.genericdao.MatchArg;
import org.genericdao.RollbackException;
import org.genericdao.Transaction;

import databeans.CustomerBean;
import databeans.PositionBean;
import databeans.TransactionBean;

public class TransactionDAO extends GenericDAO<TransactionBean> {

	public TransactionDAO(String tableName, ConnectionPool pool)
			throws DAOException {
		super(TransactionBean.class, tableName, pool);
	}

	public TransactionBean[] getTransactions(int customerId)
			throws RollbackException {
		TransactionBean[] transactions = match(MatchArg.equals("customer_id",
				customerId));
		return transactions;

	}
	public TransactionBean[] getPendingTransactions()
			throws RollbackException {
		TransactionBean[] transactions = match(MatchArg.equals("execute_date", null));
		Arrays.sort(transactions);
		return transactions;

	}
	public TransactionBean[] getAllTransactions()
			throws RollbackException {
		TransactionBean[] transactions = match(MatchArg.notEquals("customer_id", -1));
		return transactions;

	}	
	public double getPendingSellEachFund(int customerId, int fundId)
			throws RollbackException {
		double pendingShare = 0L;
		TransactionBean[] transactions = match(
				MatchArg.equals("customer_id", customerId),
				MatchArg.equals("fund_id", fundId),
				MatchArg.equals("execute_date", null),
				MatchArg.equals("transaction_type", 1));
		for (TransactionBean tran : transactions) {
			pendingShare +=(tran.getShares()/1000.000);
		}
		return pendingShare;
	}
	public TransactionBean[] getPendingSell(int customerId) throws RollbackException {
		TransactionBean[] transactions = match(MatchArg.equals("customer_id", customerId),MatchArg.equals("execute_date",null),MatchArg.or(MatchArg.equals("transaction_type", 1)));
		return transactions;
	}

	public TransactionBean[] getPendingBuy(int customerId)
			throws RollbackException {
		TransactionBean[] transactions = match(
				MatchArg.equals("customer_id", customerId),
				MatchArg.equals("execute_date", null),
				MatchArg.or(MatchArg.equals("transaction_type", 0),
						MatchArg.equals("transaction_type", 2)));
		return transactions;

	}
	public double getPendingBuyAmount(int customerId)
			throws RollbackException {
		double pendingAmount = 0;
		TransactionBean[] transactions = match(
				MatchArg.equals("customer_id", customerId),
				MatchArg.equals("execute_date", null),
				MatchArg.or(MatchArg.equals("transaction_type", 0),
						MatchArg.equals("transaction_type", 2)));
		for (TransactionBean tran : transactions) {
			pendingAmount +=(tran.getAmount());
		}
		return pendingAmount;

	}
	
	public TransactionBean[] getPendingReqChk(int customerId)
			throws RollbackException {
		TransactionBean[] transactions = match(
				MatchArg.equals("customer_id", customerId),
				MatchArg.equals("execute_date", null),
				MatchArg.equals("transaction_type", 2));
		return transactions;
	}
	
	public TransactionBean[] getPendingDepChk(int customerId)
			throws RollbackException {
		TransactionBean[] transactions = match(
				MatchArg.equals("customer_id", customerId),
				MatchArg.equals("execute_date", null),
				MatchArg.equals("transaction_type", 3));
		return transactions;
	}
	
	public TransactionBean[] getAllPendingDepChk()
			throws RollbackException {
		TransactionBean[] transactions = match(
				MatchArg.equals("execute_date", null),
				MatchArg.equals("transaction_type", 3));
		return transactions;
	}

	public boolean checkEnoughCash(int customerId, long cash, long amount)
			throws RollbackException {

		TransactionBean[] transactions = match(MatchArg.and(
				MatchArg.equals("customer_id", customerId),
				MatchArg.equals("execute_date", null),
				MatchArg.or(MatchArg.equals("transaction_type", 0),
						MatchArg.equals("transaction_type", 2))));
		cash -= amount;
		if (cash < 0){
			return false;
		}
		for (TransactionBean tran : transactions) {
			cash -= tran.getAmount();
			if (cash < 0)
				return false;
		}
		return true;
	}
	public long getMaxBuy(int customerId, long cash)
			throws RollbackException {
		long max = Long.MAX_VALUE;
		max -= cash;
		TransactionBean[] transactions = match(MatchArg.and(
				MatchArg.equals("customer_id", customerId),
				MatchArg.equals("execute_date", null),
				MatchArg.equals("transaction_type", 2)));
		for (TransactionBean tran : transactions) {
			max -= tran.getAmount();
			if (max < 0){
				return 0;
			}
		}
		max /= 9999;
		TransactionBean[] transactionsBuy = match(MatchArg.and(
				MatchArg.equals("customer_id", customerId),
				MatchArg.equals("execute_date", null),
				MatchArg.equals("transaction_type", 0)));
		for (TransactionBean tran : transactionsBuy) {
			max -= tran.getAmount();
			if (max < 0){
				return 0;
			}
		}
		return max;
	}

	public boolean checkEnoughShare(int customerId, int fundId, long share, long amount)
			throws RollbackException {

		TransactionBean[] transactions = match(MatchArg.and(
				MatchArg.equals("customer_id", customerId),
				MatchArg.equals("fund_id", fundId),
				MatchArg.equals("execute_date", null),
				MatchArg.or(MatchArg.equals("transaction_type", 1))));
		share -= amount;
		if (share < 0)
			return false;
		for (TransactionBean tran : transactions) {
			share -= tran.getShares();
			if (share < 0)
				return false;
		}

		return true;
	}
	

	public void createBuyTransaction(TransactionBean transaction) {
		// TODO Auto-generated method stub
		try {
			Transaction.begin();
			transaction.setTransaction_type(0);
			createAutoIncrement(transaction);
			Transaction.commit();

		} catch (RollbackException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (Transaction.isActive())
				Transaction.rollback();
		}

	}

	public void createSellTransaction(TransactionBean transaction) {
		// TODO Auto-generated method stub
		try {
			Transaction.begin();
			transaction.setTransaction_type(1);
			createAutoIncrement(transaction);
			Transaction.commit();

		} catch (RollbackException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (Transaction.isActive())
				Transaction.rollback();
		}
	}
	
	public void createReqChkTransaction(TransactionBean transaction) {
		// TODO Auto-generated method stub
		try {
			Transaction.begin();
			transaction.setTransaction_type(2);
			createAutoIncrement(transaction);
			Transaction.commit();

		} catch (RollbackException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (Transaction.isActive())
				Transaction.rollback();
		}

	}
	
	public void createDepChkTransaction(TransactionBean transaction) {
		// TODO Auto-generated method stub
		try {
			Transaction.begin();
			transaction.setTransaction_type(3);
			createAutoIncrement(transaction);
			Transaction.commit();

		} catch (RollbackException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (Transaction.isActive())
				Transaction.rollback();
		}

	}

	public void executeBuy(int transaction_id, Date d, long price) throws RollbackException {
		try {
        	Transaction.begin();
			TransactionBean tran = read(transaction_id);	
			if (tran == null) {
				throw new RollbackException("Transaction "+transaction_id+" no longer exists");
			}	
			tran.setExecute_date(d);
			double share = new Double(tran.getAmount())/new Double(price);
			tran.setShares((long)(share*1000));
			update(tran);
			Transaction.commit();
		} finally {
			if (Transaction.isActive()) Transaction.rollback();
		}
	}

	public void executeCheck(int transaction_id, Date d) throws RollbackException {
		try {
        	Transaction.begin();
			TransactionBean tran = read(transaction_id);	
			if (tran == null) {
				throw new RollbackException("Transaction "+transaction_id+" no longer exists");
			}	
			tran.setExecute_date(d);
			update(tran);
			Transaction.commit();
		} finally {
			if (Transaction.isActive()) Transaction.rollback();
		}		
	}

	public void executeSell(int transaction_id, Date d, long price) throws RollbackException {
		try {
        	Transaction.begin();
			TransactionBean tran = read(transaction_id);	
			if (tran == null) {
				throw new RollbackException("Transaction "+transaction_id+" no longer exists");
			}	
			tran.setExecute_date(d);
			long amount =new Double(new Double(tran.getShares())*new Double(price)/1000).longValue();
			System.out.println(amount);
			System.out.print(new Double(tran.getShares())*new Double(price)/1000);
			tran.setAmount(amount);
			update(tran);
			Transaction.commit();
		} finally {
			if (Transaction.isActive()) Transaction.rollback();
		}
	}
}
