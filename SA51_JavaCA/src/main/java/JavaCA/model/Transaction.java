package JavaCA.model;

import java.sql.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Transaction 
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String carPlateNo;
	private Date date;
	@ManyToOne
	private User user;
	@OneToMany(mappedBy = "transaction")
	private List<TransactionDetail> transactionDetails;
	
	public Transaction()
	{
		long millis = System.currentTimeMillis();  
		this.setDate(new Date(millis));
	}

	public Transaction(String carPlateNo) {
		this();
		this.carPlateNo = carPlateNo;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCarPlateNo() {
		return carPlateNo;
	}

	public void setCarPlateNo(String carPlateNo) {
		this.carPlateNo = carPlateNo;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<TransactionDetail> getTransactionDetails() {
		return transactionDetails;
	}

	public void setTransactionDetails(List<TransactionDetail> transactionDetails) {
		this.transactionDetails = transactionDetails;
	}

	@Override
	public String toString() {
		return "Transaction [id=" + id + ", carPlateNo=" + carPlateNo + ", user=" + user + ", transactionDetails="
				+ transactionDetails + "]";
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
}
