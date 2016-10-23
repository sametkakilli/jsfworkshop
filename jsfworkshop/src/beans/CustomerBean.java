package beans;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import connection.DatabaseConnection;

@SessionScoped
@ManagedBean
public class CustomerBean {
	private int customerId;
	private String name;
	private String surname;
	private String adress;
	private String telephone;
	private String email;
	private String password;

	public CustomerBean(int customerId, String name, String surname, String adress, String telephone, String email, String password) {
		super();
		this.customerId = customerId;
		this.name = name;
		this.surname = surname;
		this.adress = adress;
		this.telephone = telephone;
		this.email = email;
		this.password = password;
	}

	public CustomerBean() {
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getAdress() {
		return adress;
	}

	public void setAdress(String adress) {
		this.adress = adress;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String checkCustomer() {
		Connection con = null;
		Statement stmt = null;
		DatabaseConnection db = new DatabaseConnection();
		con = db.setConnection();
		CustomerBean customer = null;
		String page = null;
		try {
			String mysql;
			stmt = con.createStatement();
			mysql = "SELECT * FROM jsfworkshop.customer where name='" + getName() + "' and password='" + getPassword() + "';";
			ResultSet rs = stmt.executeQuery(mysql);
			while (rs.next()) {
				customer = new CustomerBean(rs.getInt("customerId"), rs.getString("name"), rs.getString("surname"), rs.getString("adress"), rs.getString("telephone"), rs.getString("email"), rs.getString("password"));
				System.out.println(customer.getCustomerId() + "------customerId");
				setCustomerId(customer.customerId);
				setSurname(customer.surname);
			}
			if (customer != null && customer.getCustomerId() != 0 && customer.getName() != null && customer.getSurname() != null && customer.getAdress() != null && customer.getEmail() != null && customer.getPassword() != null && customer.getTelephone() != null) {
				System.out.println("not null");
				setCustomerId(customer.customerId);
				page = "index.xhtml?faces-redirect=true";
			} else {
				System.out.println("null");
				FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
				page = "login.xhtml?faces-redirect=true";
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			db.closeConnection();
		}
		return page;

	}

	public String setNewUser() {
		DatabaseConnection dbc = new DatabaseConnection();
		Statement stmt = null;
		String page = null;
		try {
			stmt = dbc.setConnection().createStatement();
			if (name != null && surname != null && adress != null && email != null && password != null && telephone != null) {
				String mysql;
				mysql = "insert into jsfworkshop.customer(name,surname,adress,telephone,email,password) values ('" + name + "','" + surname + "','" + adress + "','" + telephone + "','" + email + "','" + password + "');";
				System.out.println(mysql);
				stmt.executeUpdate(mysql);
				FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
				page = "login.xhtml?faces-redirect=true";
			} else {
				FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
				page = "newuser.xhtml?faces-redirect=true";
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbc.closeConnection();
		}
		return page;
	}

	public String logout() {
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		return "index.xhtml?faces-redirect=true";
	}

}
