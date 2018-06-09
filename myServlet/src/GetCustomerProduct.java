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
 * Servlet implementation class GetCustomerProduct
 */
@WebServlet("/GetCustomerProduct")
public class GetCustomerProduct extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		PrintWriter writer = response.getWriter();
		writer.println("<html>");
		writer.println("<head><title>Get Customer</title>");
		writer.println("<style>table, th, td {text-align:center; border: 1px solid black; "
				+ "border-collapse: collapse; padding: 5px;}</style></head>");
		writer.println("<body>");
		writer.println("<h1>Get all the customer who bought at least the specified number " 
				+ "of products over all their transactions:</h1>");	
		
		// Get form data and check if text is empty or not
		String NProdStr = request.getParameter("nProduct");
		
		if((NProdStr == null) || (NProdStr.length() == 0)) {
			printMsg("You need to enter a number of products!", writer, request);
			return;
		}	
		
		// Parse number of products to integer
		int NProd;
		
		try {
			NProd = Integer.parseInt(NProdStr);
		}
		catch(NumberFormatException exc) {
			exc.printStackTrace();
			printMsg("Cannot execute a query with an invalid number of product!", writer, request);
			return;
		}
		
		// Check if number of product is positive
		if(NProd<0) {
			printMsg("The number of products needs to be positive!", writer, request);
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
			String myQuery = "SELECT customer.CId, CName, CCity, CAge, CNumber, SUM(TQnt) as "
					+ "NProduct FROM customer, transaction WHERE customer.CId = transaction.CId " 
					+ "GROUP BY transaction.CId HAVING SUM(TQnt)>" + NProd + " ORDER BY customer.CId ASC"; // Select from multiple relations, GROUP BY and HAVING are in this query
			
			ResultSet result = statement.executeQuery(myQuery);
			boolean exist = false;
			writer.println("<table>");
			
			if (result.next()) {
				exist = true;
				writer.println("<tr>");
				writer.println("<th>CId</th>"); 
				writer.println("<th>CName</th>"); 
				writer.println("<th>CCity</th>"); 
				writer.println("<th>CAge</th>"); 
				writer.println("<th>CNumber</th>"); 
				writer.println("<th>NProduct</th>"); 
				writer.println("</tr>");
				writer.println("<tr>");
				writer.println("<td>" + result.getInt("CId") + "</td>"); 
				writer.println("<td>" + result.getString("CName") + "</td>"); 
				writer.println("<td>" + result.getString("CCity") + "</td>"); 
				writer.println("<td>" + result.getInt("CAge") + "</td>"); 
				writer.println("<td>" + result.getString("CNumber") + "</td>"); 
				writer.println("<td>" + result.getInt("NProduct") + "</td>"); 
				writer.println("</tr>");
			}	
			while (result.next())
			{
			writer.println("<tr>");
			writer.println("<td>" + result.getInt("CId") + "</td>"); 
			writer.println("<td>" + result.getString("CName") + "</td>"); 
			writer.println("<td>" + result.getString("CCity") + "</td>"); 
			writer.println("<td>" + result.getInt("CAge") + "</td>"); 
			writer.println("<td>" + result.getString("CNumber") + "</td>"); 
			writer.println("<td>" + result.getInt("NProduct") + "</td>"); 
			writer.println("</tr>");
			}
			writer.println("</table>");
			if (exist == false)
			{
			printMsg("No such customer found", writer, request);
			}		
			else
			{
			writer.println("<br/><a href = \"" + request.getHeader("Referer") + "\">Back</a>\n"); 
			}
		}
		catch(SQLException exc) {
			exc.printStackTrace();
			printMsg("Cannot search customer: database error!", writer, request);
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