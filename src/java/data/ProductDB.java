/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;


import business.Cart;
import business.Product;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 *
 * @author Ross Mcinerney
 */
public class ProductDB
{
	    public static Product getProduct(String code,String description, String Price) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM product"
                + "WHERE ProductCode = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1,code);
			ps.setString(2,description);
			ps.setString(3,Price);
            rs = ps.executeQuery();
           Product product = null;
            if (rs.next()) {
                product = new Product();
                product.setCode(rs.getString("productCode"));
                product.setDescription(rs.getString("description"));
                product.setPrice(rs.getInt("Price"));
            }
            return product;
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }
		   public static ArrayList<Product> getProducts(String code,String description, String Price) throws SQLException {
        ArrayList<Product> products = new ArrayList<Product>();
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement p = null;
        ResultSet rs = null;

        String query = "SELECT * FROM product"
                + "WHERE ProductCode = ?";
        try {
            p = connection.prepareStatement(query);
            p.setString(1,code);
			p.setString(2,description);
			p.setString(3,Price);
            rs = p.executeQuery();
           Product product = null;
            if (rs.next()) {
                product = new Product();
                product.setCode(rs.getString("productCode"));
                product.setDescription(rs.getString("description"));
                product.setPrice(rs.getInt("Price"));
                products.add((Product) p);
			}
          
            return products;
           } catch (SQLException e) {
            System.out.println(e);
            return null;
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(p);
            pool.freeConnection(connection);
        }
    }

}
