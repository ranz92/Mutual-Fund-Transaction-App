package databeans;

import java.util.Date;

public class PositionOfUser4Check {
	int id;
	String name;
	String symbol;
	String amount;
	Date date;

	public int getId() 			{	return id;		}
	public String getName() 	{	return name;	}
	public String getSymbol() 	{	return symbol;	}
	public String getAmount() 	{	return amount;	}
	public Date getDate() 		{	return date;	}

	public void setId(int id) 				{	this.id = id;			}
	public void setName(String name) 		{	this.name = name;		}
	public void setSymbol(String symbol) 	{	this.symbol = symbol;	}
	public void setAmount(String amount) 	{	this.amount = amount;	}
	public void setDate(Date date) 			{	this.date = date;		}
}
