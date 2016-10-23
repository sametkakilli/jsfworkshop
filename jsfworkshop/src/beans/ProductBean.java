package beans;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;

import connection.DatabaseConnection;

@ManagedBean
@SessionScoped
public class ProductBean {
	private int productId;
	private int categoryId;
	private String productName;
	private int productPrice;
	public List<ProductBean> productList = new ArrayList<ProductBean>();
	private ProductBean ProductBean;

	public ProductBean(int productId, int categoryId, String productName, int productPrice) {
		super();
		this.productId = productId;
		this.categoryId = categoryId;
		this.productName = productName;
		this.productPrice = productPrice;
	}

	public ProductBean() {
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		System.out.println("setlendi-->" + categoryId);
		this.categoryId = categoryId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public int getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(int productPrice) {
		this.productPrice = productPrice;
	}

	public void setsendedCategoryId(ActionEvent event) {
		Object Parameter = event.getComponent().getAttributes().get("categoryId");
		int catId = Integer.parseInt((Parameter.toString()));
		setCategoryId(catId);
	}

	public List<ProductBean> getAllProduct() {
		Connection con = null;
		Statement stmt = null;
		DatabaseConnection db = new DatabaseConnection();
		con = db.setConnection();
		ProductBean usrBean = null;
		List<ProductBean> allCustomer = new ArrayList<ProductBean>();
		try {
			String mysql;
			stmt = con.createStatement();
			mysql = "SELECT * FROM jsfworkshop.product where categoryId='" + getCategoryId() + "';";
			ResultSet rs = stmt.executeQuery(mysql);
			while (rs.next()) {
				usrBean = new ProductBean(rs.getInt("productId"), rs.getInt("categoryId"), rs.getString("productName"), rs.getInt("price"));
				allCustomer.add(usrBean);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			db.closeConnection();
		}
		return allCustomer;
	}

	public String yonlendir() {
		return "index?faces-redirect=true";
	}

	public String cartList() {
		productList.add(this.ProductBean);
		return "products.xhtml";

	}
}
