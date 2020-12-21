package JavaCA.service;

import JavaCA.model.Product;

public interface EmailService 
{
	void sendSimpleMessage();
	void sendReorderEmailReminderForThisProduct(Product product);
}
