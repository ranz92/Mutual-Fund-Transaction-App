package databeans;

import org.genericdao.PrimaryKey;

@PrimaryKey("customer_id,fund_id")
public class PositionBean {
	
	private int customer_id;
	private int fund_id;
	private long shares;

	public PositionBean(int customer_id, int fund_id, long shares) {
		super();
		this.customer_id = customer_id;
		this.fund_id = fund_id;
		this.shares = shares;
	}
	
	public PositionBean() {
		// TODO Auto-generated constructor stub
	}

	public int getCustomer_id() {
		return customer_id;
	}
	public void setCustomer_id(int customer_id) {
		this.customer_id = customer_id;
	}
	public int getFund_id() {
		return fund_id;
	}
	public void setFund_id(int fund_id) {
		this.fund_id = fund_id;
	}
	public long getShares() {
		return shares;
	}
	public void setShares(long shares) {
		this.shares = shares;
	}
}

// Do we need to sort the position table?