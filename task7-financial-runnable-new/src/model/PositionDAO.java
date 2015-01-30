package model;

import java.util.Arrays;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.DuplicateKeyException;
import org.genericdao.GenericDAO;
import org.genericdao.MatchArg;
import org.genericdao.RollbackException;
import org.genericdao.Transaction;

import databeans.PositionBean;
import databeans.CustomerBean;
import databeans.FundBean;

public class PositionDAO extends GenericDAO<PositionBean>{
	public PositionDAO(String tableName, ConnectionPool pool) throws DAOException {
		super(PositionBean.class, tableName, pool);
	}

	public PositionBean[] getPositions() throws RollbackException {
		PositionBean[] positions = match(MatchArg.notEquals("shares",0L));
		return positions;
	}
	
	public void updatePosition(PositionBean newPosition) throws RollbackException {
		try {
			Transaction.begin();
			int curCus = newPosition.getCustomer_id();
			int curFund = newPosition.getFund_id();
						
			PositionBean[] DupPositions = match(MatchArg.equals("customer_id",curCus), MatchArg.equals("fund_id",curFund));
	        if(DupPositions!=null && DupPositions.length>0) {
	        	for(PositionBean pos:DupPositions){
	        		pos.setShares(pos.getShares() + newPosition.getShares());
					update(pos);
	        	}
	        }	
	        else {
			create(newPosition);
	        }
			Transaction.commit();
		}		
		finally {
			if (Transaction.isActive()) Transaction.rollback();
		}
	}
	
	public PositionBean[] getPositions(int cusId) throws RollbackException {
		PositionBean[] positions = match(MatchArg.notEquals("shares", 0L), MatchArg.equals("customer_id", cusId));
		return positions;
	}
	
	public long getPositionsValue(int cusId) throws RollbackException {
		long value=0L;
		PositionBean[] positions = match(MatchArg.notEquals("shares", 0L), MatchArg.equals("customer_id", cusId));
		for (PositionBean position : positions){
			value += position.getShares()*100;
		}
		return value;
	}

	public void delete(int customerId, int fundId) throws RollbackException {
		try {
			Transaction.begin();
    		PositionBean position = read(customerId, fundId);

    		if (position == null) {
				throw new RollbackException("Entry not found.");
    		}

			delete(customerId, fundId);
			Transaction.commit();
		} finally {
			if (Transaction.isActive()) Transaction.rollback();
		}
	}

	public long getShareValue(int customerId, int fund_id) throws RollbackException {
		// TODO Auto-generated method stub
		if (read(customerId, fund_id)==null){
			return 0;
		}
		else return read(customerId, fund_id).getShares();
	}

}