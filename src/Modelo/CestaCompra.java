package Modelo;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import Controlador.Connect;

/**
 *
 * @author Damian
 */
public class CestaCompra {
    private float precio,saldo,cambio;
    private int numpro;
    public  ArrayList<Stock> cesta;

    public ArrayList<Stock> getCesta() {
        return cesta;
    }

    public CestaCompra() {
    }

    public CestaCompra(float precio, float saldo, float cambio, int numpro) {
        this.precio = precio;
        this.saldo = saldo;
        this.cambio = cambio;
        this.numpro = numpro;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public void setCesta(ArrayList<Stock> cesta) {
        this.cesta = cesta;
    }
    

    public float getSaldo() {
        return saldo;
    }

    public void setSaldo(float saldo) {
        this.saldo = saldo;
    }

    public float getCambio() {
        return cambio;
    }

    public void setCambio(float cambio) {
        this.cambio = cambio;
    }

    public int getNumpro() {
        return numpro;
    }

    public void setNumpro(int numpro) {
        this.numpro = numpro;
    }

    @Override
    public String toString() {
        return "CestaCompra{" + "precio=" + precio + ", saldo=" + saldo + ", cambio=" + cambio + ", numpro=" + numpro + '}';
    }
    
    public void pagar(Float pago, ArrayList<Stock> lista,ArrayList<Stock> cesta) throws FileNotFoundException, SQLException{
        Float cambio,total=0f;
            for(int i=0;i<lista.size();i++){
                System.out.println(lista.size());
                        total=total+lista.get(i).getPrecio();
                    }
            //para aplicar ofertas
                    Ofertas of1= new Ofertas();
                    
                      int res= JOptionPane.showConfirmDialog(null,"Tienes un codigo de oferta a aplicar?");
                            
                            if (res==0){
                                String cof= JOptionPane.showInputDialog(null,"Introduce o codigo da oferta a "
                                       + "aplicar");
                                total=of1.calcularPrecioDescuento(total,cof);
                            }
            // pago final          
            cambio=pago-total;
                if(cambio<0){
                    while(pago<total){
                    Float cextra=Float.parseFloat(JOptionPane.showInputDialog(null,
                            "Introduce cambio adicional"));
                    pago=cextra+pago;
                    }
                       
                }else{
                    JOptionPane.showMessageDialog(null,"Su cambio es: "+cambio);
                    Connect cn=new Connect();Statement stmt;
                    Connection cc=cn.conecta(); stmt=cc.createStatement();
                    for(int i=0;i<lista.size();i++){
                        cn.actualizagrf(stmt, "stock",cesta.get(i).getProducto(),"",Integer.toString(cesta.get(i).getCantidad()));
                       
                    }
                    
                }
    }
}
