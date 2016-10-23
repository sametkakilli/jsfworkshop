package beans;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import connection.DatabaseConnection;

@ManagedBean
@SessionScoped
public class CartBean {

	private int orderedProductId;
	@ManagedProperty(value = "#{customerBean.customerId}")
	private int orderedCustomerId;
	private int orderedCategoryId;
	private String orderedProductName;
	private int orderedProductPrice;

	public CartBean(int orderedProductId, int orderedCustomerId, int orderedCategoryId, String orderedProductName, int orderedProductPrice) {
		super();
		this.orderedProductId = orderedProductId;
		this.orderedCustomerId = orderedCustomerId;
		this.orderedCategoryId = orderedCategoryId;
		this.orderedProductName = orderedProductName;
		this.orderedProductPrice = orderedProductPrice;
	}

	public CartBean() {

	}

	public int getOrderedProductId() {
		return orderedProductId;
	}

	public void setOrderedProductId(int orderedProductId) {
		this.orderedProductId = orderedProductId;
	}

	public int getOrderedCategoryId() {
		return orderedCategoryId;
	}

	public void setOrderedCategoryId(int orderedCategoryId) {
		this.orderedCategoryId = orderedCategoryId;
	}

	public int getOrderedCustomerId() {
		return orderedCustomerId;
	}

	public void setOrderedCustomerId(int id) {
		this.orderedCustomerId = id;
	}

	List<CartBean> cart = new ArrayList<CartBean>();

	public CartBean(String orderedProductName, int orderedProductPrice) {
		super();
		this.orderedProductName = orderedProductName;
		this.orderedProductPrice = orderedProductPrice;
	}

	public String getOrderedProductName() {
		return orderedProductName;
	}

	public void setOrderedProductName(String orderedProductName) {
		this.orderedProductName = orderedProductName;
	}

	public int getOrderedProductPrice() {
		return orderedProductPrice;
	}

	public void setOrderedProductPrice(int orderedProductPrice) {
		this.orderedProductPrice = orderedProductPrice;
	}

	public void setSelectedProduct(ActionEvent ae) {
		Object Parameter1 = ae.getComponent().getAttributes().get("productId");
		Object Parameter2 = ae.getComponent().getAttributes().get("customerId");
		Object Parameter3 = ae.getComponent().getAttributes().get("categoryId");
		Object Parameter4 = ae.getComponent().getAttributes().get("productName");
		Object Parameter5 = ae.getComponent().getAttributes().get("productPrice");
		int productId = Integer.parseInt(Parameter1.toString());
		int customerId = Integer.parseInt(Parameter2.toString());
		int categoryId = Integer.parseInt(Parameter3.toString());
		String productName = Parameter4.toString();
		int productPrice = Integer.parseInt(Parameter5.toString());
		setOrderedProductId(productId);
		setOrderedCustomerId(customerId);
		setOrderedCategoryId(categoryId);
		setOrderedProductName(productName);
		setOrderedProductPrice(productPrice);
		add2Cart(new CartBean(productId, customerId, categoryId, productName, productPrice));
	}

	public void set2OrderProductDB(ActionEvent event) {
		DatabaseConnection dbc = new DatabaseConnection();
		Statement stmt = null;
		try {
			stmt = dbc.setConnection().createStatement();
			String mysql;
			List<CartBean> cart = getCartList();
			for (CartBean cb : cart) {
				mysql = "insert into jsfworkshop.orderproduct(productId,customerId,categoryId,productname,price) values ('" + cb.getOrderedProductId() + "','" + cb.getOrderedCustomerId() + "','" + cb.getOrderedCategoryId() + "','" + cb.getOrderedProductName() + "','" + cb.getOrderedProductPrice() + "');";
				System.out.println(mysql);
				stmt.executeUpdate(mysql);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbc.closeConnection();
		}
	}

	public List<CartBean> getSaledCartList() {
		Connection con = null;
		Statement stmt = null;
		DatabaseConnection db = new DatabaseConnection();
		con = db.setConnection();
		CartBean cartBean = null;
		List<CartBean> saledProduct = new ArrayList<CartBean>();
		try {
			String mysql;
			stmt = con.createStatement();
			mysql = "SELECT * FROM jsfworkshop.orderproduct where customerId='" + getOrderedCustomerId() + "';";
			ResultSet rs = stmt.executeQuery(mysql);
			System.out.println(mysql);
			while (rs.next()) {
				cartBean = new CartBean(rs.getInt("productId"), rs.getInt("customerId"), rs.getInt("categoryId"), rs.getString("productName"), rs.getInt("price"));
				System.out.println(cartBean.getOrderedCategoryId() + "-" + cartBean.getOrderedProductName() + "-");
				saledProduct.add(cartBean);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			db.closeConnection();
		}
		return saledProduct;
	}

	public List<CartBean> getCartList() {
		return cart;
	}

	public void setCartList(List<CartBean> urun) {
		this.cart = urun;
	}

	public void add2Cart(CartBean cartbean) {
		FacesContext context = FacesContext.getCurrentInstance();
		System.out.println("it is in the add2cart");
		cart.add(cartbean);
		for (CartBean cb : cart) {
			System.out.println(cb.getOrderedProductName());
		}
	}

	public int getSumOfCartPrice() {
		int sum = 0;
		for (CartBean cb : cart) {
			sum += cb.orderedProductPrice;
		}
		return sum;
	}
}
