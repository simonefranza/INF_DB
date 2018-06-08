

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
 * Servlet implementation class GetPhoneNumber
 */
@WebServlet("/GetPhoneNumber")
public class GetPhoneNumber extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		PrintWriter writer = response.getWriter();
		
		writer.println("<html>");
		writer.println("<head><title>Get Numbers</title>");
		writer.println("<style>table, th, td {text-align:center; border: 1px solid black; border-collapse: collapse; padding: 5px;}</style></head>");
		writer.println("<body>");
		writer.println("<h1>Get the telephone numbers of those customer who bought the specified beer:</h1>");
		
		
		// Get form data and check if text is empty or not
		String BName = request.getParameter("beerName");

		
		if((BName == null) || (BName.length() == 0)) {
			printMsg("You need to enter a beer name!", writer, request);
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
			ResultSet testCity = statement.executeQuery("SELECT * FROM beer WHERE BName ='" + BName + "'");
			
			if(!testCity.next()) {
				printMsg("Cannot execute with the query: the specified beer name is not available in the database!", writer, request);
				return;
			}
			
			String myQuery = "SELECT DISTINCT customer.CId, CName, CNumber FROM customer, transaction, beer WHERE " 
					+ "customer.CId = transaction.CId AND beer.BId = transaction.BId AND BName = '" + BName 
					+ "' ORDER BY customer.CId ASC"; 
			
			ResultSet result = statement.executeQuery(myQuery);
			
			boolean exist = false;
			writer.println("<table>");
			
			if (result.next()) {
				exist = true;
				writer.println("<tr>");
				writer.println("<th>CId</th>");
				writer.println("<th>CName</th>");
				writer.println("<th>CNumber</th>");
				writer.println("</tr>");
				writer.println("<tr>");
				writer.println("<td>" + result.getInt("CId") + "</td>"); 
				writer.println("<td>" + result.getString("CName") + "</td>"); 
				writer.println("<td>" + result.getString("CNumber") + "</td>"); 
				writer.println("</tr>");
			}
			
			while (result.next())
			{
				writer.println("<tr>");
				writer.println("<td>" + result.getInt("CId") + "</td>"); 
				writer.println("<td>" + result.getString("CName") + "</td>"); 
				writer.println("<td>" + result.getString("CNumber") + "</td>"); 
				writer.println("</tr>");
			}
			writer.println("</table>");
			if (exist == false)
			{
			printMsg("The specified beer wasn't bought by any customer yet!", writer, request);
			}		
			else
			{
			writer.println("<br/><a href = \"" + request.getHeader("Referer") + "\">Back</a>\n"); 
			}
		}
		catch(SQLException exc) {
			exc.printStackTrace();
			printMsg("Cannot search beer: database error!", writer, request);
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
