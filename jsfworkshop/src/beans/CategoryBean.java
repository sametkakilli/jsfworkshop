package beans;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import connection.DatabaseConnection;

@ManagedBean
@RequestScoped
public class CategoryBean {
	private int categoryId;
	private String categoryName;

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public CategoryBean(int categoryId, String categoryName) {
		super();
		this.categoryId = categoryId;
		this.categoryName = categoryName;
	}

	public CategoryBean() {
		// TODO Auto-generated constructor stub
	}

	public List<CategoryBean> getAllCategory() {
		Connection con = null;
		Statement stmt = null;
		DatabaseConnection db = new DatabaseConnection();
		con = db.setConnection();
		CategoryBean usrBean = null;
		List<CategoryBean> allCustomer = new ArrayList<CategoryBean>();
		try {
			String mysql;
			stmt = con.createStatement();
			mysql = "SELECT * FROM jsfworkshop.category;";
			ResultSet rs = stmt.executeQuery(mysql);
			while (rs.next()) {
				usrBean = new CategoryBean(rs.getInt("categoryId"), rs.getString("categoryname"));
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

}
