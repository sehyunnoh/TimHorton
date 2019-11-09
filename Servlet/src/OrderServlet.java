import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/OrderServlet")
public class OrderServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	String action;
	int numbers = 0;
	String size;
	int creams = 0;
	int sugars = 0;
	String slang;
	private double totalSum;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		action = request.getParameter("action");
		numbers = Integer.parseInt(request.getParameter("numbers"));
		size = request.getParameter("size");

		if (action.equals("form1")) {
			creams = Integer.parseInt(request.getParameter("creams"));
			sugars = Integer.parseInt(request.getParameter("sugars"));
		} else {
			slang = request.getParameter("slang");
			getInfoFromslang(slang);
		}

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.append("<!DOCTYPE html>");
		out.append("<html>");
		out.append("<head>");
		out.append("<meta charset='UTF-8'>");
		out.append("<title>Your Tim Horton's Order</title>");
		out.append("<style>");
		out.append("body {");
		out.append("	background: rgb(235, 222, 205);");
		out.append("	color: rgb(99, 61, 19);");
		out.append("}");
		out.append(".container {");
		out.append("	padding: 10px 150px;");
		out.append("}");
		out.append("h1 {");
		out.append("	font-size: 4rem;");
		out.append("}");
		out.append("@media only screen and (max-width: 700px) {");
		out.append("	.container {");
		out.append("		padding: 0px;");
		out.append("	}");
		out.append("}");
		out.append(".order {");
		out.append("	border: 2px solid rgb(99, 61, 19);");
		out.append("	background: white;");
		out.append("	padding: 20px;");
		out.append("	margin-bottom: 20px;");
		out.append("}");
		out.append(".small {");
		out.append("	height: 100px;");
		out.append("}");
		out.append(".medium {");
		out.append("	height: 150px;");
		out.append("}");
		out.append(".large {");
		out.append("	height: 200px !important;");
		out.append("}");
		out.append(".exLarge {");
		out.append("	height: 250px;");
		out.append("}");
		out.append("#price, #totalSum {");
		out.append("	font-size: 1.5rem;");
		out.append("	font-weight: bold;");
		out.append("}");
		out.append("</style>");
		out.append("</head>");
		out.append("<body>");
		out.append("	<div class='container'>");
		out.append("		<h1>Thanks for your order. Here it is:</h1>");
		out.append("		<div>");
		out.append("			<h3 style='display: inline-block'>( Small: $1.20 / Medium: $1.50");
		out.append("				/ Large: $1.80 / Extra large: $2.00 )</h3>");
		out.append("			<button onClick='window.location.href = \"index.html\"'>Back");
		out.append("				to order page</button>");
		out.append("		</div>");
		out.append("		<div class='body' id='body'>");

		for (int i = 0; i < numbers; i++) {
			out.append(makeDivOrder(size, creams, sugars));
		}

		out.append("		</div>");
		out.append("		<div class='price' id='price'>");
		out.append(makePrice(size, numbers));
		out.append("		</div>");
		out.append("		<br>");
		out.append("		<div id='totalSum'>So far, we have made $" + String.format("%.2f", totalSum) + "</div>");
		out.append("	</div>");
		out.append("</body>");
		out.append("</html>");
	}

	// get creams and sugars information from slang
	public void getInfoFromslang(String slang) {
		switch (slang) {
		case "r":
			creams = 1;
			sugars = 1;
			break;
		case "dd":
			creams = 2;
			sugars = 2;
			break;
		case "tt":
			creams = 3;
			sugars = 3;
			break;
		case "b":
			creams = 0;
			sugars = 0;
			break;
		case "bos":
			creams = 0;
			sugars = 1;
			break;
		case "bts":
			creams = 0;
			sugars = 2;
			break;
		case "bths":
			creams = 0;
			sugars = 3;
			break;
		}
	}

	// make price and store total sum
	public String makePrice(String size, int numbers) {
		double price = 0;
		if (size.equals("small")) {
			price = 1.2;
		} else if (size.equals("medium")) {
			price = 1.5;
		} else if (size.equals("large")) {
			price = 1.8;
		} else if (size.equals("exLarge")) {
			price = 2.0;
		} else {
			price = 1.2;
		}

		totalSum += (price * 1.13 * numbers);
		return String.format("Cost: $%.2f x %d + tax = $%.2f", price, numbers, (price * 1.13 * numbers));
	}

	// make image row
	public String makeDivOrder(String size, int creams, int sugars) {
		String result = "<div class='order'>";
		result += String.format("<img class='%s' src='images/cup.jpg'>", size);
		result = addStuff(result, sugars, "sugar");
		result = addStuff(result, creams, "cream");
		return result + "</div>";
	}

	// add image sugar or cream
	public String addStuff(String result, int number, String stuff) {
		if (number != 0) {
			result += "<img src='images/plus.jpg'>";
			while (number > 0) {
				result += String.format("<img src='images/%s.jpg'>", stuff);
				number--;
			}
		}
		return result;
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
