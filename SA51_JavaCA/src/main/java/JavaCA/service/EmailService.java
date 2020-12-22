package JavaCA.service;

import JavaCA.model.Product;

public interface EmailService 
{
	void sendReorderEmailReminderForThisProduct(Product product);
	void sendEmailToResetPassword(String s);
}
