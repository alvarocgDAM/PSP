import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class main {

	public static void main(String[] args) {

		/*
		 * Vamos a usar la libreria JSoup, que nos permite trabajar con el html de una
		 * url como si fuera una especie de JQuery
		 * 
		 */

		String urlBolsa = "https://www.bolsamadrid.es/esp/aspx/Mercados/Precios.aspx?indice=ESI100000000&punto=indice";

		while (true) {

			Calendar c = Calendar.getInstance();

			// fecha de hoy
			String dia = Integer.toString(c.get(Calendar.DATE));
			String mes = Integer.toString(c.get(Calendar.MONTH) + 1);
			String anyo = Integer.toString(c.get(Calendar.YEAR));
			String hora = LocalTime.now().toString().split("[.]")[0];

			try {

				BufferedWriter escritor = new BufferedWriter(new FileWriter("bolsa.txt", true));

				// todo el documento html de la página
				Document doc = Jsoup.connect(urlBolsa).get();

				// cogemos la tabla del ibex 35 y navegamos hacia el td que contiene el dato que
				// buscamos, sacandolo como string
				Element ibex = doc.getElementById("ctl00_Contenido_tblÍndice");
				String valorIbex = ibex.child(0).child(1).child(2).text();

				escritor.write(dia + "/" + mes + "/" + anyo + "," + hora + ",IBEX35," + valorIbex + "\n");

				// lo mismo para la tabla grande
				Element tabla = doc.getElementById("ctl00_Contenido_tblAcciones");

				Elements trs = tabla.child(0).children();

				for (int i = 1; i < trs.size(); i++) {

					Element tr = trs.get(i);

					String nombreEmpresa = tr.child(0).text();
					String valorUltimo = tr.child(1).text().replace(",", ".");

					escritor.write(
							dia + "/" + mes + "/" + anyo + "," + hora + "," + nombreEmpresa + "," + valorUltimo + "\n");

				}

				escritor.close();

				System.out.println("Terminado el scrapping de la hora: " + hora);

				Thread.sleep(60000); // espera 1 minuto

			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {

			}

		}

	}

}
