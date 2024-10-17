package com.example.taxi;
import java.sql.*;

public class BdConnexion {

		private String driver="com.mysql.jdbc.Driver";
		private String url="jdbc:mysql://localhost:3306/test";
		private String user="root";
		private String pwd="";
		private Connection con;
		private static Statement st;
		public BdConnexion()  {
			try {
				Class.forName(driver);
			}catch(ClassNotFoundException ex){
				System.out.println("Probleme driver");
			}
			try {
				con=DriverManager.getConnection(url,user,pwd);
	            st=con.createStatement();
			}catch(SQLException ex) {
				System.out.println("Probleme conn");
			}
		}
	    public Connection getConnection() {
		return con;
	    }

		public  int executerAction(String s)  {
			int nbr=0;
			try {
				nbr=st.executeUpdate(s);

			}catch(SQLException e) {
				System.out.println("Probleme req");

			}
			return nbr;
		}

		public ResultSet executerSelect(String s) {
			ResultSet rs=null;
			try {
				rs=st.executeQuery(s);
			}catch(SQLException e) {
				System.out.println("probleme req select");
			}
			return rs;
		}



}
