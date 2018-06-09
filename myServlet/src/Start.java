import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
/**
 * Servlet implementation class Start
 */
@WebServlet("/Start")
public class Start extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public Start() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		response.setContentType("text/html");
		PrintWriter writer = response.getWriter();
		writer.println("<html>");
		writer.println("<head><title>Overview Servlet</title></head>");
		writer.println("<body>");
		writer.println("<form method='get' action='InsertRating'>");
		writer.println("<h2>Insert a new rating:</h2>");
		writer.println("CustomerId:<input type='text' name='customerId'/>");
		writer.println("BeerId:<input type='text' name='beerId'/>");
		writer.println("Rating:<input type='text' name='rRating'/>");
		writer.println("<input type='submit' value='Insert Rating'/>");
		writer.println("</form>");
		writer.println("<form method='get' action='DeleteCustomer'>");
		writer.println("<h2>Delete a customer and all the related data:</h2>");
		writer.println("CustomerId:<input type='text' name='customerId'/>");
		writer.println("<input type='submit' value='Delete this customer'/>");
		writer.println("</form>");
		writer.println("<form method='get' action='GetCustomerProduct'>");
		writer.println("<h2>Get all the customer who bought at least the specified "
				+ "number of products over all their transactions:</h2>");
		writer.println("Amount of products:<input type='text' name='nProduct'/>");
		writer.println("<input type='submit' value='Get customers'/>");
		writer.println("</form>");
		writer.println("<form method='get' action='GetRatingCity'>");
		writer.println("<h2>Get the average rating given by the costumer living in the "
				+ "specified city:</h2>");
		writer.println("City:<input type='text' name='city'/>");
		writer.println("<input type='submit' value='Get average ratings'/>");
		writer.println("</form>");
		writer.println("<form method='get' action='GetPhoneNumber'>");
		writer.println("<h2>Get the telephone numbers of those customer who bought "
				+ "the specified beer:</h2>");
		writer.println("BeerName:<input type='text' name='beerName'/>");
		writer.println("<input type='submit' value='Get telephone numbers'/>");
		writer.println("</form>");
		writer.println("</body>");
		writer.println("</html>");
		writer.close();
	}
}
