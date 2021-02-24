package Util;

import java.nio.charset.Charset;
import java.time.LocalDateTime;

import com.aspose.email.MailAddress;
import com.aspose.email.MailMessage;
import com.aspose.email.SmtpClient;

import Modelos.Producto;

public class EmailUtil {

	/**
	 * Envía un e-mail unsando la API aspose.email 
	 * <br><br>
	 * https://blog.aspose.com/2020/05/20/create-and-send-outlook-email-messages-asynchronously-using-java/
	 * 
	 * @param producto
	 * @param hoy
	 */
	public static void enviarEmail(Producto producto, LocalDateTime hoy, String email) {

		MailMessage message = new MailMessage();

		String body = "El producto <b>" + producto.getNombreProducto() + "</b> se ha agotado!!! Se realizó una compra a a las <b>"
				+ hoy.getHour() + ":" + hoy.getMinute() + "</b> que agotó el stock.\n\n El precio actual de compra al proveedor es de "
				+ producto.getPrecioProveedor();

		String subject = "Producto: " + producto.getNombreProducto();

		// Set subject of the message, body and sender information
		message.setSubject(subject);
		message.setHtmlBody(body);
		message.setFrom(new MailAddress("dkfjhansugyfasgyhjsghruhahgjkfds@outlook.es", "Álvaro Carvajal Guerra", false)); //tuve que crear esa direccion tan fea porque las bonitas estaban todas ocupadas :(

		// Specify encoding
		message.setBodyEncoding(Charset.forName("US-ASCII"));

		// Add To recipients and CC recipients
		message.getTo().addItem(new MailAddress(email, "Encargado", false));

		// Create an instance of SmtpClient Class
		SmtpClient client = new SmtpClient();

		// Specify your mailing host server, Username, Password, Port
		client.setHost("smtp-mail.outlook.com");
		client.setUsername("dkfjhansugyfasgyhjsghruhahgjkfds@outlook.es");
		client.setPassword("DAM2021!javaMail");
		client.setPort(587);

		try {
			// Client.Send will send this message
			client.send(message);
		}

		catch (Exception ex) {
			ex.printStackTrace();
		}

	}

}
