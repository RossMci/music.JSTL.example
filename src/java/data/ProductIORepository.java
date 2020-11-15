package data;


import business.Product;
import java.io.*;
import java.util.*;

public class ProductIORepository implements ProductRepository
{
private final String filepath;
	public ProductIORepository(String filepath)
	{
		this.filepath = filepath;
	}

// gets a product
	public Product getProduct(String code)
	{
		try
		{
			File file = new File(filepath);
			BufferedReader in = new BufferedReader(new FileReader(file));

			String line = in.readLine();
			while(line != null)
			{
				StringTokenizer t = new StringTokenizer(line, "|");
				String productCode = t.nextToken();
				if(code.equalsIgnoreCase(productCode))
				{
					String description = t.nextToken();
					double price = Double.parseDouble(t.nextToken());
					Product product = new Product();
					product.setCode(code);
					product.setDescription(description);
					product.setPrice(price);
					in.close();
					return product;
				}
				line = in.readLine();
			}
			in.close();
			return null;
		}
		catch(IOException e)
		{
			System.err.println(e);
			return null;
		}
	}
	// gets all the products
	public ArrayList<Product> getProducts()
	{
		ArrayList<Product> products = new ArrayList<Product>();
		File file = new File(filepath);
		try
		{
			BufferedReader in = new BufferedReader(new FileReader(file));

			String line = in.readLine();
			while(line != null)
			{
				StringTokenizer t = new StringTokenizer(line, "|");
				String code = t.nextToken();
				String description = t.nextToken();
				String priceAsString = t.nextToken();
				double price = Double.parseDouble(priceAsString);
				Product product = new Product();
				product.setCode(code);
				product.setDescription(description);
				product.setPrice(price);
				products.add(product);
				line = in.readLine();
			}
			in.close();
			return products;
		}
		catch(IOException e)
		{
			System.err.println(e);
			return null;
		}
	}
	
}
