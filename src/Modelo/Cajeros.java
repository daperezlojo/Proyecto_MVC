package Modelo;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class Cajeros {

    public Cajeros() {
    }
        
        
    public ArrayList Repon(int i,ArrayList<Stock> stocksinrep) throws FileNotFoundException{
        Stock s =new Stock();
           
            int j=Integer.parseInt(JOptionPane.showInputDialog(null,
                                "Dame as unidades a repoñer para: "+stocksinrep.get(i).getProducto()));
            
             stocksinrep.get(i).setCantidad(j+stocksinrep.get(i).getCantidad());
            return stocksinrep;
    }

}
