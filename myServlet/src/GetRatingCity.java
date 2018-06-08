

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class GetRatingCity
 */
@WebServlet("/GetRatingCity")
public class GetRatingCity extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		PrintWriter writer = response.getWriter();
		
		writer.println("<html>");
		writer.println("<head><title>Get Ratings</title>");
		writer.println("<style>table, th, td {text-align:center; border: 1px solid black; border-collapse: collapse; padding: 5px;}</style></head>");
		writer.println("<body>");
		writer.println("<h1>Get the average rating given by the costumer living in the specified city:</h1>");
		
		
		// Get form data and check if text is empty or not
		String CCity = request.getParameter("city");

		
		if((CCity == null) || (CCity.length() == 0)) {
			printMsg("You need to enter a city!", writer, request);
			return;
		}
	
		// Connect to the database
		try {
			Class.forName("com.mysql.jdbc.Driver");
		}
		catch(ClassNotFoundException exc) {
			exc.printStackTrace();
			printMsg("Cannot execute the query: no JDBC driver found!", writer, request);
			return;
		}
		
		try {
			Connection connection_;
			connection_ = DriverManager.getConnection("jdbc:mysql://localhost/01530693_beer", "student", "student");
			
			Statement statement = connection_.createStatement(); 
			
			// Check if city is in the database
			ResultSet testCity = statement.executeQuery("SELECT * FROM customer WHERE CCity ='" + CCity + "'");
			
			if(!testCity.next()) {
				printMsg("Cannot execute with the query: the specified city is not available in the database!", writer, request);
				return;
			}
			
			String myQuery = "SELECT customer.CId, CName, CCity, ROUND(AVG(RRating),2) as AverageRating FROM customer, rating WHERE customer.CId = rating.CId AND CCity = '" + CCity + "' GROUP BY rating.CId ORDER BY customer.CId ASC";
			
			ResultSet result = statement.executeQuery(myQuery);
			
			boolean exist = false;
			writer.println("<table>");
			
			if (result.next()) {
				exist = true;
				writer.println("<tr>");
				writer.println("<th>CId</th>"); 
				writer.println("<th>CName</th>"); 
				writer.println("<th>CCity</th>"); 
				writer.println("<th>Average Rating</th>"); 
				writer.println("</tr>");
				writer.println("<tr>");
				writer.println("<td>" + result.getInt("CId") + "</td>"); 
				writer.println("<td>" + result.getString("CName") + "</td>"); 
				writer.println("<td>" + result.getString("CCity") + "</td>"); 
				writer.println("<td>" + result.getFloat("AverageRating") + "</td>"); 
				writer.println("</tr>");
			}
			
			while (result.next())
			{
				writer.println("<tr>");
				writer.println("<td>" + result.getInt("CId") + "</td>"); 
				writer.println("<td>" + result.getString("CName") + "</td>"); 
				writer.println("<td>" + result.getString("CCity") + "</td>"); 
				writer.println("<td>" + result.getInt("AverageRating") + "</td>"); 
				writer.println("</tr>");
			}
			writer.println("</table>");
			if (exist == false)
			{
			printMsg("The customers living in the specified city didn't give any rating!", writer, request);
			}		
			else
			{
			writer.println("<br/><a href = \"" + request.getHeader("Referer") + "\">Back</a>\n"); 
			}
		}
		catch(SQLException exc) {
			exc.printStackTrace();
			printMsg("Cannot search rating: database error!", writer, request);
		}
				
		
		writer.println("</body>");
		writer.println("</html>");
		writer.close();
	}
	private void printMsg(String msg, PrintWriter writer, HttpServletRequest request) {
		writer.write("<h3>" + msg + "</h3>\n");
		writer.write("<a href = \"" + request.getHeader("Referer") + "\">Back</a>\n"); 
		writer.write("</body>");
		writer.write("</html>");
	}

}
