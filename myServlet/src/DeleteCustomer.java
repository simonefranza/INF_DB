

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
 * Servlet implementation class DeleteCustomer
 */
@WebServlet("/DeleteCustomer")
public class DeleteCustomer extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		PrintWriter writer = response.getWriter();
		
		writer.println("<html>");
		writer.println("<head><title>Delete Customer</title></head>");
		writer.println("<body>");
		writer.println("<h1>Delete Customer and all related data!</h1>");
		
		
		// Get form data and check if text is empty or not
		String CIdStr = request.getParameter("customerId");

		
		if((CIdStr == null) || (CIdStr.length() == 0)) {
			printMsg("Cannot delete a user with no user Id!", writer, request);
			return;
		}
		
		
		// Parse IDs and rating to integer
		int CId;
		
		try {
			CId = Integer.parseInt(CIdStr);
		}
		catch(NumberFormatException exc) {
			exc.printStackTrace();
			printMsg("Cannot delete a user with an invalid user Id!", writer, request);
			return;
		}
		
		// Check if ID is positive
		if(CId<0) {
			printMsg("Cannot delete a user with a negative user Id!", writer, request);
			return;
		}

		
		// Connect to the database
		try {
			Class.forName("com.mysql.jdbc.Driver");
		}
		catch(ClassNotFoundException exc) {
			exc.printStackTrace();
			printMsg("Cannot delete the user: no JDBC driver found!", writer, request);
			return;
		}
		
		try {
			Connection connection_;
			connection_ = DriverManager.getConnection("jdbc:mysql://localhost/01530693_beer", "student", "student");
			Statement statement = connection_.createStatement(); // Referential integrity gets checked here!!
			
			ResultSet result = statement.executeQuery("SELECT * FROM customer WHERE CId =" + CId);
			
			if(!result.next()) {
				printMsg("Cannot delete a customer which does not exist!", writer, request);
				return;
			}
			
			
			String deleteSqlStmt = "DELETE FROM customer WHERE CId = " + CId;
			statement.executeUpdate(deleteSqlStmt);
			
			printMsg("Customer and all related data successfully deleted!", writer, request);
		}
		catch(SQLException exc) {
			exc.printStackTrace();
			printMsg("Cannot delete customer: database error!", writer, request);
		}
				
		
		writer.println("</body>");
		writer.println("</html>");
		writer.close();
	}
	private void printMsg(String msg, PrintWriter writer, HttpServletRequest request) {
		writer.write("<h3>" + msg + "</h3>\n");
		writer.write("<a href = \"" + request.getHeader("Referer") + "\">Back</a>\n"); writer.write("</body>");
		writer.write("</html>");
	}

}
