package interlentisimo;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;


public class ControlBase {
	JFrame frame;
	private Connection conexion = null;
	private String pass = "ziRtszHrvfZ8rEAaY_PVNQ2LpmUeQF50";
	private String user = "qhqysnst";
	private String comprobarlogin;
	
	public void conectarme() {
		try {
		Class.forName("org.postgresql.Driver");
		conexion = DriverManager.getConnection("jdbc:postgresql://queenie.db.elephantsql.com/qhqysnst",user,pass);
	} catch (SQLException | ClassNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}
	
	public  boolean buscarusuario(String id, String contrase�a) throws SQLException {
		PreparedStatement p = null;
		ResultSet result = null;
		conectarme();
		try {
			comprobarlogin = "SELECT identificaci�n_u,contrase�a_u from usuarios where identificaci�n_u = '"+id+"' and contrase�a_u = '"+contrase�a+"'";
			p = conexion.prepareStatement(comprobarlogin);
			result = p.executeQuery();
			while(result.next()) {
				id = result.getString("identificaci�n_u");
				contrase�a = result.getString("contrase�a_u");	
				return true;
			  }		
			
		}finally {
	        if (result != null) try { result.close(); } catch (SQLException logOrIgnore) {}
	        if (conexion != null) try { conexion.close(); } catch (SQLException logOrIgnore) {}
	    }
		return false;
	}
	
	public void crud(String sql) {
		PreparedStatement p = null;
		conectarme();
		try {
			p = conexion.prepareStatement(sql);
			p.executeUpdate();
		}catch(Exception ex) {
			JOptionPane.showMessageDialog(null, ex);
		}
	}
}
