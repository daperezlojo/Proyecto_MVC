package Controlador;

import Modelo.Ofertas;
import Modelo.Stock;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class Connect {

    public Connection conecta() {
        Connection c = null;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:prueba.db");
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return c;
    }

    public void busca(Statement stmt) throws SQLException {
        String busc = (String) JOptionPane.showInputDialog(null, "dame o nome a buscar");
        String tabsc = (String) JOptionPane.showInputDialog(null, "dame a taboa");
        String consulta = "SELECT * FROM " + tabsc + " WHERE nome='" + busc + "';";
        ResultSet rs = stmt.executeQuery(consulta);
        while (rs.next()) {
            System.out.println(rs.getString("nome"));
            System.out.println(rs.getString("idade"));
        }
        rs.close();
    }

    public String buscagrf(Statement stmt, String tabact, String busc) throws SQLException {
        String resultado = null;
        String consulta = "SELECT * FROM " + tabact + " WHERE nome='" + busc + "';";
        ResultSet rs = stmt.executeQuery(consulta);
        while (rs.next()) {
            resultado = rs.getString("nome") + " " + rs.getString("idade");
        }
        rs.close();
        return resultado;
    }

    public void comando(Statement stmt) throws SQLException {
        String cmd = (String) JOptionPane.showInputDialog(null, "dame o comando a insertar");
        stmt.execute(cmd);
    }

    public void inserta(Statement stmt) throws SQLException {
        String val = (String) JOptionPane.showInputDialog(null, "dame o valor a insertar ('nome',idade)");
        String tab = (String) JOptionPane.showInputDialog(null, "dame taboa");
        stmt.execute("INSERT INTO " + tab + " VALUES (" + val + ");");
    }

    public void insertagrf(Statement stmt, String tab, String val1, String val2, String val3) throws SQLException {
        if (tab.equals("stock")) {
            stmt.execute("insert into " + tab + " values('" + val1 + "'," + val2 + "," + val3 + ");");
        } else if (tab.equals("Ofertas")) {
            stmt.execute("insert into " + tab + " values('" + val1 + "'," + val2 + ");");
        } else {
        }
    }

    public void creaTabla(Statement stmt) throws SQLException {
        String tabn = (String) JOptionPane.showInputDialog(null, "dame o nome da taboa");
        String tabvals = (String) JOptionPane.showInputDialog(null, "dame os valores da taboa (pKey,campos...)");
        stmt.execute("CREATE TABLE " + tabn + "(" + tabvals + ");");
    }

    public void borra(Statement stmt) throws SQLException {
        String borr = (String) JOptionPane.showInputDialog(null, "dame a taboa");
        String vborr = (String) JOptionPane.showInputDialog(null, "dame o nome a borrar");
        String borra = "DELETE FROM " + borr + " WHERE nome='" + vborr + "';";
        stmt.execute(borra);
    }

    public void borragrf(Statement stmt, String tabact, String vborr) throws SQLException {
        
        if(tabact.equals("stock")){
            String borra = "DELETE FROM " + tabact + " WHERE Producto='" + vborr + "';";
            stmt.execute(borra);
            JOptionPane.showMessageDialog(null, "Borrado o valor: " + vborr);
        }
        else{
            String borra = "DELETE FROM " + tabact + " WHERE codigo='" + vborr + "';";
            stmt.execute(borra);
            JOptionPane.showMessageDialog(null, "Borrado o valor: " + vborr);
        }
        
    }

    public void actualiza(Statement stmt) throws SQLException {
        String upval = (String) JOptionPane.showInputDialog(null, "dame a idade a actualizar");
        String nome = (String) JOptionPane.showInputDialog(null, "dame o nome a actualizar");
        String uptab = (String) JOptionPane.showInputDialog(null, "dame taboa");
        stmt.execute("UPDATE " + uptab + " set idade=" + upval + " WHERE nome='" + nome + "';");
    }

    public void actualizagrf(Statement stmt, String tabact, String val1, String val2, String val3) throws SQLException {
        if (tabact.equals("stock")) {

            if (val2.isEmpty() && val3.isEmpty()) {
                stmt.execute("UPDATE " + tabact + " set Producto='" + val1 + "' WHERE Producto=" + val1 + "';");
            } else if (val1.isEmpty()) {
                Float precio = Float.parseFloat(val2);
                int cant = Integer.parseInt(val3);
                stmt.execute("UPDATE " + tabact + " set Precio=" + precio + ",Cantidad=" + cant + " WHERE Producto='" + val1 + "';");
            } else if (val2.isEmpty()) {
                int cant = Integer.parseInt(val3);
                stmt.execute("UPDATE " + tabact + " set Cantidad=" + cant + " WHERE Producto='" + val1 + "';");
            } else if (val3.isEmpty()) {
                Float precio = Float.parseFloat(val2);
                stmt.execute("UPDATE " + tabact + " set Producto='" + val1 + "',Precio=" + precio + " WHERE Producto='" + val1 + "';");
            } else {
                Float precio = Float.parseFloat(val2);
                int cant = Integer.parseInt(val3);
                stmt.execute("UPDATE " + tabact + " set Producto='" + val1 + "',Precio=" + precio + ",Cantidad=" + cant + " WHERE Producto='" + val1 + "';");
            }
        } else if (tabact.equals("Ofertas")) {
            if (val2.isEmpty()) {
                stmt.execute("UPDATE " + tabact + " set codigo=" + val1 + " WHERE codigo='" + val1 + "';");
            } else {
                stmt.execute("UPDATE " + tabact + " set descuento=" + val2 + " WHERE codigo='" + val1 + "';");
            }
        } else {
            System.out.println("No se ha podido actualizar");
        }

    }

    public ArrayList getTabla(String tablaac) throws SQLException {
        Connection con = conecta();
        Statement stmt;
        stmt = con.createStatement();
        ArrayList<Object> tabla = new ArrayList();

        ResultSet rs = stmt.executeQuery("SELECT * FROM " + tablaac + ";");

        if (tablaac.equals("stock")) {
            while (rs.next()) {
                String producto = rs.getString("producto");
                Float precio = rs.getFloat("precio");
                int cantidad = rs.getInt("cantidad");
                tabla.add(new Stock(producto, precio, cantidad));
            }
        } else if (tablaac.equals("Ofertas")) {
            while (rs.next()) {
                String codigo = rs.getString("codigo");
                Float descuento = rs.getFloat("descuento");
                tabla.add(new Ofertas(codigo, descuento));
            }
        } else {

        }
        rs.close();
        stmt.close();
        con.close();
        return tabla;
    }
}
