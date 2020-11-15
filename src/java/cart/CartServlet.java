package cart;

import business.Cart;
import business.LineItem;
import business.Product;
import data.ProductDB;
import data.ProductRepository;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class CartServlet extends HttpServlet {

	private ProductRepository productRepository;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		ServletContext servletContext = getServletContext();
		String path = servletContext.getRealPath("/WEB-INF/products.txt");
		productRepository = new ProductDB();

		// get current action
		String action = request.getParameter("action");
		if (action == null) {
			action = "cart";  // default action
		}

		// perform action and set URL to appropriate page
		String url = "/index.jsp";
		if (action.equals("shop")) {
			url = "/index.jsp";    // the "index" page
		} else if (action.equals("cart")) {
			String productCode = request.getParameter("productCode");
			String quantityString = request.getParameter("quantity");

			HttpSession session = request.getSession();
			Cart cart = (Cart) session.getAttribute("cart");
			if (cart == null) {
				cart = new Cart();
			}

			//if the user enters a negative or invalid quantity,
			//the quantity is automatically reset to 1.
			int quantity;
			try {
				quantity = Integer.parseInt(quantityString);
				if (quantity < 0) {
					quantity = 1;
				}
			} catch (NumberFormatException nfe) {
				quantity = 1;
			}

			Product product = productRepository.getProduct(productCode);
			System.out.println(product);
			LineItem lineItem = new LineItem();
			lineItem.setProduct(product);
			lineItem.setQuantity(quantity);
			if (quantity > 0) {
				cart.addItem(lineItem);
			} else if (quantity == 0) {
				cart.removeItem(lineItem);
			}

			session.setAttribute("cart", cart);
			url = "/cart.jsp";
		} else if (action.equals("checkout")) {
			url = "/checkout.jsp";
		}

		servletContext.getRequestDispatcher(url)
				.forward(request, response);
	}
}
