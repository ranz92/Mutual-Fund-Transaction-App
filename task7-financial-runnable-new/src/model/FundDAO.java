package model;

import java.util.Arrays;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.GenericDAO;
import org.genericdao.MatchArg;
import org.genericdao.RollbackException;
import org.genericdao.Transaction;

import databeans.FundBean;

public class FundDAO extends GenericDAO<FundBean> {
	public FundDAO(String tableName, ConnectionPool cp) throws DAOException {
		super(FundBean.class, tableName, cp);
	}
//Jiayi changed to autoincrement
	public void create(FundBean Fund) throws RollbackException {
		FundBean[] funds = match(MatchArg.equals("symbol", Fund.getSymbol()));
		if (funds.length > 0) {
				throw new RollbackException("Ticker name \""+
			Fund.getSymbol()+"\" already exists");
		 		}
		try {
			Transaction.begin();

			this.createAutoIncrement(Fund);

			Transaction.commit();
		} finally {
			if (Transaction.isActive())
				Transaction.rollback();
		}
	}

//	public FundBean[] getFund(int id) throws RollbackException {
//
//		FundBean[] funds = match(MatchArg.equals("id", id));
//		return funds;
//	}
//Jiayi added getFundList
	public FundBean[] getFundList() throws RollbackException {
		FundBean[] fundList = match();
		Arrays.sort(fundList);  // We want them sorted by last and first names (as per User.compareTo());
		return fundList;
	}
}
