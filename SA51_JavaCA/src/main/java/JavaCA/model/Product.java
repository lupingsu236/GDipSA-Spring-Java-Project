package JavaCA.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Product 
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@ManyToOne
	private Brand brand;
	@NotNull
	@Size(min=2, max=50)
	private String name, type, category, subcategory;
	@NotNull
	@Size(min=2, max=200)
	private String description;
	@Min(0)
	@Digits(integer = 8, fraction = 2)
	private double originalPrice;
	@Min(0)
	@Digits(integer = 8, fraction = 0)
	private int reorderLevel, minOrderQty, quantity;
	@ManyToOne
	private Supplier supplier;
	@OneToMany(mappedBy = "product")
	private List<TransactionDetail> transactionDetails;
	
	private static double wholesaleMultiplier = 1.2;
	private static double partnerMultiplier = 1.3;
	private static double retailMultiplier = 1.4;
	
	public Product()
	{
		
	}

	public Product(String name, String description, String type, String category, String subcategory,
			double originalPrice, int reorderLevel, int minOrderQty, int quantity)
	{
		super();
		this.name = name;
		this.description = description;
		this.type = type;
		this.category = category;
		this.subcategory = subcategory;
		this.originalPrice = originalPrice;
		this.reorderLevel = reorderLevel;
		this.minOrderQty = minOrderQty;
		this.quantity = quantity;
	}

	public long getId() 
	{
		return id;
	}

	public void setId(long id) 
	{
		this.id = id;
	}

	public Brand getBrand() 
	{
		return brand;
	}

	public void setBrand(Brand brand) 
	{
		this.brand = brand;
	}

	public String getName() 
	{
		return name;
	}

	public void setName(String name) 
	{
		this.name = name;
	}

	public String getDescription() 
	{
		return description;
	}

	public void setDescription(String description) 
	{
		this.description = description;
	}

	public String getType() 
	{
		return type;
	}

	public void setType(String type) 
	{
		this.type = type;
	}

	public String getCategory() 
	{
		return category;
	}

	public void setCategory(String category) 
	{
		this.category = category;
	}

	public String getSubcategory() 
	{
		return subcategory;
	}

	public void setSubcategory(String subcategory) 
	{
		this.subcategory = subcategory;
	}

	public double getOriginalPrice() 
	{
		return originalPrice;
	}

	public void setOriginalPrice(double originalPrice) 
	{
		this.originalPrice = originalPrice;
	}

	public int getReorderLevel() 
	{
		return reorderLevel;
	}

	public void setReorderLevel(int reorderLevel) 
	{
		this.reorderLevel = reorderLevel;
	}

	public int getMinOrderQty() 
	{
		return minOrderQty;
	}

	public void setMinOrderQty(int minOrderQty) 
	{
		this.minOrderQty = minOrderQty;
	}

	public int getQuantity() 
	{
		return quantity;
	}

	public void setQuantity(int quantity) 
	{
		this.quantity = quantity;
	}

	public Supplier getSupplier() 
	{
		return supplier;
	}

	public void setSupplier(Supplier supplier) 
	{
		this.supplier = supplier;
	}
	
	public List<TransactionDetail> getTransactionDetails() {
		return transactionDetails;
	}

	public void setTransactionDetails(List<TransactionDetail> transactionDetails) {
		this.transactionDetails = transactionDetails;
	}

	public double getWholesalePrice() 
	{
		return wholesaleMultiplier * originalPrice;
	}
	
	public double getPartnerPrice()
	{
		return partnerMultiplier * originalPrice;
	}
	
	public double getRetailPrice()
	{
		return retailMultiplier * originalPrice;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", brand=" + brand + ", name=" + name + ", description=" + description + ", type="
				+ type + ", category=" + category + ", subcategory=" + subcategory + ", originalPrice=" + originalPrice
				+ ", reorderLevel=" + reorderLevel + ", minOrderQty=" + minOrderQty + ", quantity=" + quantity
				+ ", supplier=" + supplier + ", transactionDetails=" + transactionDetails + "]";
	}

}
