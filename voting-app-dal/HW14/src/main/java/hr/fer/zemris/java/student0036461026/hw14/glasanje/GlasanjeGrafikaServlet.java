package hr.fer.zemris.java.student0036461026.hw14.glasanje;

import hr.fer.zemris.java.student0036461026.hw14.dao.DAOProvider;
import hr.fer.zemris.java.student0036461026.hw14.model.AnketaOpcija;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.util.Rotation;

/**
 * Servlet čija je zadaća stvaranje tortnog prikaza rezultata glasova opcije ankete.
 * @author Tomislav
 *
 */
@WebServlet(urlPatterns={"/glasanje-grafika"})
public class GlasanjeGrafikaServlet extends HttpServlet {

	/**
	 * Serial version.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Metoda stvara sliku tortnog prikaza rezultata opcije ankete.
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		HttpSession session = req.getSession();
		
		@SuppressWarnings("unchecked")
		List<AnketaOpcija> list = (List<AnketaOpcija>) session.getAttribute("opcije");
		if (list == null) {
			Long pollID = null;
			try{
				pollID = Long.valueOf(req.getParameter("pollID"));
			}catch (Exception ex) {
				return;
			}
			list = DAOProvider.getDao().dohvatiSveOpcijeAnkete(pollID, "votesCount");
			session.setAttribute("opcije", list);
		}
		
		PieDataset dataset = createDataset(list);
		JFreeChart chart = createChart(dataset, "");
		BufferedImage bufferedImage = chart.createBufferedImage(400,400);
		
		resp.setContentType("image/png");
		OutputStream out = resp.getOutputStream();
		ImageIO.write(bufferedImage, "png", out);
		out.flush();
		out.close();
	}	

	/**
	 * Metoda stvara skup podataka za tortni prikaz.
	 * @param list Lista koja sadrži podatke o opcijama ankete.
	 * @return Skup podataka za tortni prikaz.
	 */
	private  PieDataset createDataset(List<AnketaOpcija> list) {
		DefaultPieDataset result = new DefaultPieDataset();
		for (AnketaOpcija opcija : list) {
			result.setValue(opcija.getName(), opcija.getBrGlasova());
		}
		return result;   
	}

	/**
	 * Metoda stvara tortni graf.
	 * @param dataset skup podataka za stvaranje grafa.
	 * @param title Naslov grafa.
	 * @return Tortni graf.
	 */
	private JFreeChart createChart(PieDataset dataset, String title) {

		JFreeChart chart = ChartFactory.createPieChart3D(title,
				dataset,                
				true,                   
				true,
				false);

		PiePlot3D plot = (PiePlot3D) chart.getPlot();
		plot.setStartAngle(290);
		plot.setDirection(Rotation.CLOCKWISE);
		plot.setForegroundAlpha(0.5f);
		return chart;

	}
}
