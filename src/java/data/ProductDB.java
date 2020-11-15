
package data;



import business.Product;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ross Mcinerney
 */
public class ProductDB implements ProductRepository
{
        @Override
	    public Product getProduct(String code)
	     {
   	   Connection connection = ConnectionPool.getInstance().getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

       
        try 
        {
        	String query = "SELECT * FROM product WHERE ProductCode = ?";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, code);

			resultSet = preparedStatement.executeQuery();
			Product product = null;
			if(resultSet.next())
			{
				product = new Product();
				product.setCode(resultSet.getString("productCode"));
				product.setDescription(resultSet.getString("ProductDescription"));
				product.setPrice(resultSet.getDouble("ProductPrice"));
			}
			return product;

        } 
        catch(SQLException ex)
		{
			Logger.getLogger(ProductDB.class.getName()).log(Level.SEVERE, null, ex);
		}
		finally
		{
			DBUtil.closeResultSet(resultSet);
			DBUtil.closePreparedStatement(preparedStatement);
			ConnectionPool.getInstance().freeConnection(connection);
		}
		return null;
    }
		  @Override
	public List<Product> getProducts()
	{
		Connection connection = ConnectionPool.getInstance().getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try
		{
			String query = "SELECT * FROM product";
			preparedStatement = connection.prepareStatement(query);

			resultSet = preparedStatement.executeQuery();
			ArrayList<Product> products = new ArrayList<>();
			while(resultSet.next())
			{
				Product product = new Product();
				product.setCode(resultSet.getString("productCode"));
				product.setDescription(resultSet.getString("ProductDescription"));
				product.setPrice(resultSet.getDouble("ProductPrice"));
				products.add(product);
			}
			return products;
          
}
   	catch(SQLException ex)
		{
			Logger.getLogger(ProductDB.class.getName()).log(Level.SEVERE, null, ex);
		}
		finally
		{
			DBUtil.closeResultSet(resultSet);
			DBUtil.closePreparedStatement(preparedStatement);
			ConnectionPool.getInstance().freeConnection(connection);
		}
		return null;
	}
}
