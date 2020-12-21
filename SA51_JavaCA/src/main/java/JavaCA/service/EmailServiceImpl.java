package JavaCA.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import JavaCA.model.ActiveType;
import JavaCA.model.Product;
import JavaCA.model.RoleType;

@Service
public class EmailServiceImpl implements EmailService
{
	@Autowired
    private JavaMailSender emailSender;	
	private UserInterface uservice;
	
	@Autowired
	public void setServices(UserImplementation uservice) 
	{
		this.uservice = uservice;
	}
	
	@Override
	public void sendSimpleMessage() 
	{
		SimpleMailMessage message = new SimpleMailMessage(); 
		message.setFrom("system.parts4you@gmail.com");
		message.setTo(("liauhyj@gmail.com")); 
		message.setSubject("test1");
		message.setText("test1");
		emailSender.send(message);
   }
	
	public void sendReorderEmailReminderForThisProduct(Product product)
	{
		if (product.getQuantity() > product.getReorderLevel())
			return;
		else
		{
			String subject = "Reorder required for " + product.getName() + " (Product ID " + product.getId() + ")";
			int orderAmountForProduct = product.getReorderLevel() - product.getQuantity() + product.getMinOrderQty();
			String text = String.format("Please reorder %s units of %s (Product ID %s) from Supplier %s (Supplier ID %s)."
										+ "\n\nThis is a system generated email, please do not reply.", 
										orderAmountForProduct, product.getName(), product.getId(), product.getSupplier().getSupplierName(),
										product.getSupplier().getId());
//			String text = "Please reorder " + orderAmountForProduct + " units of " + product.getName() + 
//						  " (Product ID " + product.getId() + ")" + "from Supplier " + product.getSupplier() + 
//						  " (Supplier ID " + product.getSupplier().getId() + ")";
			List<String> emailOfAdmins = uservice.listAllUser().stream()
										.filter(x -> x.getRole() == RoleType.ADMIN && x.getActivetype() == ActiveType.ACTIVE)
										.map(x -> x.getEmail())
										.collect(Collectors.toList());
			for (String email: emailOfAdmins)
			{
				SimpleMailMessage message = new SimpleMailMessage(); 
				message.setFrom("system.parts4you@gmail.com");
				message.setTo(email); 
				message.setSubject(subject);
				message.setText(text);
				emailSender.send(message);
			}
		}
	}
}
