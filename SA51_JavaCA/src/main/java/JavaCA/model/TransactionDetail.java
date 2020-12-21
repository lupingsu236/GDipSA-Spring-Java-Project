package JavaCA.model;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Min;

@Entity
public class TransactionDetail 
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@Min(1)
	private int quantityChange;
	private Date date;
	private TransactionType transactionType;
	@ManyToOne
	private Transaction transaction;
	@ManyToOne
	private Product product;
	
	public TransactionDetail()
	{
		long millis = System.currentTimeMillis();  
		this.setDate(new Date(millis));
	}

	public TransactionDetail(int quantityChange, TransactionType transactionType) 
	{
		super();
		long millis = System.currentTimeMillis();  
		this.setDate(new Date(millis));
		this.quantityChange = quantityChange;
		this.transactionType = transactionType;
	}

	
	public TransactionDetail(@Min(1) int quantityChange, Date date, TransactionType transactionType) {
		super();
		this.quantityChange = quantityChange;
		this.date = date;
		this.transactionType = transactionType;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getQuantityChange() {
		return quantityChange;
	}

	public void setQuantityChange(int quantityChange) {
		this.quantityChange = quantityChange;
	}

	public TransactionType getTransactionType() {
		return transactionType;
	}
	
	public String getTransactionTypeString() {
		return transactionType.toString();
	}

	public void setTransactionType(TransactionType transactionType) {
		this.transactionType = transactionType;
	}

	public Transaction getTransaction() {
		return transaction;
	}

	public void setTransaction(Transaction transaction) {
		this.transaction = transaction;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	@Override
	public String toString() {
		return "TransactionDetail [id=" + id + ", quantityChange=" + quantityChange + ", transactionType="
				+ transactionType + ", transaction=" + transaction + ", product=" + product + "]";
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
}
