package databeans;

import java.util.Date;

import org.genericdao.PrimaryKey;

@PrimaryKey("fund_id,price_date")

public class PriceBean implements Comparable<PriceBean>{
	int fund_id;
	Date price_date;
	long price;
	public PriceBean(int fund_id, Date price_date, long price) {
		super();
		this.fund_id = fund_id;
		this.price_date = price_date;
		this.price = price;
	}
	public PriceBean() {
		// TODO Auto-generated constructor stub
	}
	public int getFund_id() {
		return fund_id;
	}
	public void setFund_id(int fund_id) {
		this.fund_id = fund_id;
	}
	public Date getPrice_date() {
		return price_date;
	}
	public void setPrice_date(Date price_date) {
		this.price_date = price_date;
	}
	public long getPrice() {
		return price;
	}
	public void setPrice(long price) {
		this.price = price;
	}
	
	public int compareTo(PriceBean o) {
		// TODO Auto-generated method stub
		return price_date.compareTo(o.price_date);
	}
	
}
