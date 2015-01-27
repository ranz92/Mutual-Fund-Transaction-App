package databeans;


public class RawPriceBean{
	int fund_id;
	String price;
	public RawPriceBean(int fund_id, String price) {
		super();
		this.fund_id = fund_id;
		this.price = price;
	}
	public int getFund_id() {
		return fund_id;
	}
	public void setFund_id(int fund_id) {
		this.fund_id = fund_id;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	
}
