package JavaCA.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import JavaCA.model.ActiveType;
import JavaCA.model.Product;
import JavaCA.model.RoleType;
import JavaCA.model.User;
import net.bytebuddy.utility.RandomString;

@Service
@Transactional
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
	public void sendReorderEmailReminderForThisProduct(Product product)
	{
		if (product.getQuantity() > product.getReorderLevel())
			return;
		else
		{
			String subject = "*Parts4you* Reorder required for " + product.getName() + " (Product ID " + product.getId() + ")";
			int orderAmountForProduct = product.getReorderLevel() - product.getQuantity() + product.getMinOrderQty();
			String text = String.format("Dear Admin,\n\nPlease reorder %s units of %s (Product ID %s) from Supplier %s (Supplier ID %s)."
										+ "\n\nThis is a system generated email, please do not reply.", 
										orderAmountForProduct, product.getName(), product.getId(), product.getSupplier().getSupplierName(),
										product.getSupplier().getId());
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
	
	@Override
	public void sendEmailToResetPassword(String s)
	{
		if (uservice.findByUsername(s) == null && uservice.findByEmail(s) == null)
			return;
		else
		{
			if (uservice.findByUsername(s) != null)
			{
				User u = uservice.findByUsername(s);
				String newPassword = RandomString.make(8);
				u.setPassword(newPassword);
				uservice.updateUser(u);
				
				String subject = String.format("*Parts4you* Password reset requested for %s (User ID %s)", u.getFullName(), u.getId());
				String text = String.format("Dear %s,\n\n Your password has been reset to %s.\n\nThis is a system generated email, "
						+ "please do not reply.", u.getFullName(), u.getPassword());
				
				SimpleMailMessage message = new SimpleMailMessage(); 
				message.setFrom("system.parts4you@gmail.com");
				message.setTo(u.getEmail()); 
				message.setSubject(subject);
				message.setText(text);
				emailSender.send(message);
			}
			else if (uservice.findByEmail(s) != null)
			{
				User u = uservice.findByEmail(s);
				String newPassword = RandomString.make(8);
				u.setPassword(newPassword);
				uservice.updateUser(u);
				
				String subject = String.format("*Parts4you* Password reset requested for %s (User ID %s)", u.getFullName(), u.getId());
				String text = String.format("Dear %s,\n\n Your password has been reset to %s.\n\nThis is a system generated email, "
						+ "please do not reply.", u.getFullName(), u.getPassword());
				
				SimpleMailMessage message = new SimpleMailMessage(); 
				message.setFrom("system.parts4you@gmail.com");
				message.setTo(u.getEmail()); 
				message.setSubject(subject);
				message.setText(text);
				emailSender.send(message);
			}
		}
	}
}
