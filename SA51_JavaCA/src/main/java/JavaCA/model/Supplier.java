package JavaCA.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Supplier 
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@NotNull @NotBlank
	@Size(min=2, max=50)
	private String supplierName;
	@OneToMany(mappedBy = "supplier")
	private List<Product> products;
	
	public Supplier()
	{
		
	}

	public Supplier(String supplierName) 
	{
		super();
		this.supplierName = supplierName;
	}

	public long getId() 
	{
		return id;
	}

	public void setId(long id) 
	{
		this.id = id;
	}

	public String getSupplierName() 
	{
		return supplierName;
	}

	public void setSupplierName(String supplierName) 
	{
		this.supplierName = supplierName;
	}

	public List<Product> getProducts() 
	{
		return products;
	}

	public void setProducts(List<Product> products) 
	{
		this.products = products;
	}

	@Override
	public String toString() {
		return "Supplier [id=" + id + ", supplierName=" + supplierName + ", products=" + products + "]";
	}
	
}
