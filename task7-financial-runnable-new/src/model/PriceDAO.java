package model;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.GenericDAO;
import org.genericdao.MatchArg;
import org.genericdao.RollbackException;
import org.genericdao.Transaction;

import databeans.PriceBean;

public class PriceDAO extends GenericDAO<PriceBean> {
	public PriceDAO(String tableName, ConnectionPool cp) throws DAOException {
		super(PriceBean.class, tableName, cp);
	}

	public void createPrice(PriceBean price) throws RollbackException {
		PriceBean[] prices = match(MatchArg.and(
				MatchArg.equals("fund_id", price.getFund_id()),
				MatchArg.equals("price_date", price.getPrice_date())));
		if (prices.length > 0) {
			throw new RollbackException("Price for transition day "+
					price.getPrice_date()+" already exists");
		}
		if (price.getPrice_date() == null) {
			throw new RollbackException("No date specified");
		}
		try {
			Transaction.begin();
			create(price);
			Transaction.commit();
		} finally {
			if (Transaction.isActive())
				Transaction.rollback();
		}
	}

	public PriceBean[] getPrice(int fund_id) throws RollbackException {

		PriceBean[] prices = match(MatchArg.equals("fund_id", fund_id));
		Arrays.sort(prices);
		return prices;
	}
	public double getLatestPrice(int fund_id) throws RollbackException {

		PriceBean[] prices = match(MatchArg.equals("fund_id", fund_id));
		Arrays.sort(prices);
		if (prices.length>0){
		return (double)prices[prices.length - 1].getPrice()/100.00;
		}
		else return 0;
	}
	public Date getLastTransactionDay() throws RollbackException {

		PriceBean[] prices = match();
		Arrays.sort(prices);
		if (prices.length>0){
		return prices[prices.length - 1].getPrice_date();
		}
		else {
			return null;
		}
	}


}
