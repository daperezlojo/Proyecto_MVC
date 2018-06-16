package Modelo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Ofertas {
        private String codoferta;
        private float descuento;
       
        File fich =new File("Ofertas.txt");
        PrintWriter pw;
        ArrayList<Ofertas> ofertas = new ArrayList<>();
        Scanner sc;

    public Ofertas(String codoferta, float descuento) {
        this.codoferta = codoferta;
        this.descuento = descuento;
    }

    public String getCodoferta() {
        return codoferta;
    }

    public void setCodoferta(String codoferta) {
        this.codoferta = codoferta;
    }

    public float getDescuento() {
        return descuento;
    }

    public void setDescuento(float descuento) {
        this.descuento = descuento;
    }
        
        
        public Ofertas() throws FileNotFoundException {
        sc=new Scanner(fich);
        String[] arrayofer=new String[3];
            while(sc.hasNextLine()){
                String linea=sc.nextLine();
                    arrayofer=linea.split(",");         //corto la linea y añado a un array
                    Ofertas of=new Ofertas(arrayofer[0],Float.parseFloat(arrayofer[1]));
                            
                    ofertas.add(of);              //añado oferta al arraylist desde el archivo
                        
            }
    }
        
        public Float calcularPrecioDescuento(float precio, String codigo) throws FileNotFoundException{
            float preciodes=0f;
            
            for(int i =0;i<ofertas.size();i++){
            
                if(codigo.equals(ofertas.get(i).getCodoferta())){
                    preciodes=precio*ofertas.get(i).getDescuento();
                }
            }   
                preciodes=precio-preciodes;
            return preciodes;
        }

    public ArrayList<Ofertas> getOfertas() {
        return ofertas;
    }

        
}
