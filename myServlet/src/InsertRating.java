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
 * Servlet implementation class InsertRating
 */
@WebServlet("/InsertRating")
public class InsertRating extends HttpServlet {
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
		writer.println("<head><title>Insert Rating</title></head>");
		writer.println("<body>");
		writer.println("<h1>Insert Rating!</h1>");	
		
		// Get form data and check if text is empty or not
		String CIdStr = request.getParameter("customerId");
		String BIdStr = request.getParameter("beerId");
		String RRatingStr = request.getParameter("rRating");
		
		if((CIdStr == null) || (CIdStr.length() == 0)) {
			printMsg("Cannot insert a rating with no customer Id!", writer, request);
			return;
		}
		
		if((BIdStr == null) || (BIdStr.length() == 0)) {
			printMsg("Cannot insert a rating with no beer Id!", writer, request);
			return;
		}
		
		if((RRatingStr == null) || (RRatingStr.length() == 0)) {
			printMsg("Cannot insert a rating with no rating!", writer, request);
			return;
		}
	
		// Parse IDs and rating to integer
		int CId, BId, RRating;
		
		try {
			CId = Integer.parseInt(CIdStr);
		}
		catch(NumberFormatException exc) {
			exc.printStackTrace();
			printMsg("Cannot insert a rating with an invalid customer Id!", writer, request);
			return;
		}
		
		try {
			BId = Integer.parseInt(BIdStr);
		}
		catch(NumberFormatException exc) {
			exc.printStackTrace();
			printMsg("Cannot insert a rating with an invalid beer Id!", writer, request);
			return;
		}
		
		try {
			RRating = Integer.parseInt(RRatingStr);
		}
		catch(NumberFormatException exc) {
			exc.printStackTrace();
			printMsg("Cannot insert a rating with an invalid rating!", writer, request);
			return;
		}
		
		// Check if IDs are positive and if rating is between 0 and 5
		if(CId<0) {
			printMsg("Cannot insert a rating with negative customer Id!", writer, request);
			return;
		}
		
		if(BId<0) {
			printMsg("Cannot insert a rating with negative beer Id!", writer, request);
			return;
		}
		
		if((RRating < 0) || (RRating > 5)) {
			printMsg("The rating cannot be negative or greater then 5!", writer, request);
			return;
		}
		
		// Connect to the database
		try {
			Class.forName("com.mysql.jdbc.Driver");
		}
		catch(ClassNotFoundException exc) {
			exc.printStackTrace();
			printMsg("Cannot insert the rating : no JDBC driver found!", writer, request);
			return;
		}
		
		try {
			Connection connection_;
			connection_ = DriverManager.getConnection("jdbc:mysql://localhost/01530693_beer", "student", "student");
			Statement statement = connection_.createStatement(); 
			
			ResultSet resultC = statement.executeQuery("SELECT * FROM customer WHERE CId =" 
			+ CId); // Referential integrity gets checked here!!
			
			if(!resultC.next()) {
				printMsg("Cannot insert the rating: no such customer!", writer, request);
				return;
			}
			
			ResultSet resultB = statement.executeQuery("SELECT * FROM beer WHERE BId =" + BId);
			
			if(!resultB.next()) {
				printMsg("Cannot insert the rating: no such beer!", writer, request);
				return;
			}
			
			ResultSet resultA = statement.executeQuery("SELECT * FROM rating WHERE CId =" + CId 
					+ " AND BId = " + BId);
			
			//If rating already exists, it gets updated
			if(resultA.next()) {
				String insertSqlStmt = "UPDATE rating SET RRating = " + RRating + " WHERE CId = " 
			+ CId + " AND BId = " + BId; // Database gets updated here
				statement.executeUpdate(insertSqlStmt);
				
				printMsg("Rating updated successfully!", writer, request);
			}
			//If rating doesn't exist yet, it gets inserted into the database
			else {
				String insertSqlStmt = "INSERT INTO rating VALUES (" + CId + "," + BId + "," 
			+ RRating + ");"; //Values are inserted here
				statement.executeUpdate(insertSqlStmt);
			
				printMsg("Rating successfully inserted!", writer, request);
			}
		}
		catch(SQLException exc) {
			exc.printStackTrace();
			printMsg("Cannot insert rating: database error!", writer, request);
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