package databeans;


public class CustomFundBean extends FundBean{
	private int    id;
	private String name;
	private String symbol;
	private long lastPrice;
	public CustomFundBean (FundBean fund){
		id = fund.getId();
		name = fund.getName();
		symbol = fund.getSymbol();
		lastPrice = 0;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	public long getLastPrice() {
		return lastPrice;
	}
	public void setLastPrice(long lastPrice) {
		this.lastPrice = lastPrice;
	}
	
	
}
