/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package usm.simulacion.twitter.io;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jfree.data.category.DefaultCategoryDataset;
import usm.simulacion.twitter.simulator.NetworkManager;
import usm.simulacion.twitter.simulator.User;

/**
 *
 * @author camilovera
 */
public class OutputGlobal {
    
    public static class Reporte{
        public long tweet;
        public long reTweet;
        public double tweetUsuario;
        public double tweetUsuarioTiempo;
    }
    
    private static Workbook reporte = new XSSFWorkbook();
    private static DefaultCategoryDataset dataset22 = new DefaultCategoryDataset();
    private static DefaultCategoryDataset dataset11 = new DefaultCategoryDataset();
    private static Map<Integer,List<Double>> promedios = new HashMap<Integer, List<Double>>();
    private static int currentSimulation;
    private static List<Reporte> reportes = new ArrayList<Reporte>();
    private static NormalDistribution d;
    
    public synchronized static void addValue(Integer val1, Double val2){
        
        if(promedios.containsKey(val1)){
            promedios.get(val1).add(val2);
        }else{
            List<Double> f = new ArrayList<Double>();
            f.add(val2);
            promedios.put(val1, f);
        }
        
    }
    
    public synchronized static void addValue3(long tweetCounter, long reTweetCounter, double tweetUsuario, double tweetUsuarioTiempo){
        Reporte r = new Reporte();
        r.tweet = tweetCounter;
        r.reTweet = reTweetCounter;
        r.tweetUsuario = tweetUsuario;
        r.tweetUsuarioTiempo = tweetUsuarioTiempo;
        reportes.add(r);
    }

    public synchronized static int getCurrentSimulation(){

       return OutputGlobal.currentSimulation;

    }
    public synchronized static void plusCurrentSimulation(){

        OutputGlobal.currentSimulation++;

    }
    
    public synchronized static void generateReport(){
        try {
            int i=0;
            int j=0;
            Sheet saltos = reporte.createSheet("Saltos");
            Sheet general = reporte.createSheet("general");
            GraphOut graficoFinal = new GraphOut();

            Row fila = saltos.createRow(0);
            for(Integer key:promedios.keySet()){
                fila.createCell(key).setCellValue("salto "+key);
            }
            
            fila = saltos.createRow(reportes.size()+3);
            fila.createCell(0).setCellValue("media");
            fila = saltos.createRow(reportes.size()+4);
            fila.createCell(0).setCellValue("varianza");
            fila = saltos.createRow(reportes.size()+5);
            fila.createCell(0).setCellValue("x1");
            fila.createCell(1).setCellValue(reportes.size()-1);
            fila = saltos.createRow(reportes.size()+6);
            fila.createCell(0).setCellValue("x2");
            fila.createCell(1).setCellValue(1-(1-(95/100))/2);
            
            for(Integer key:promedios.keySet()){
                i=0;
                for(Double val:promedios.get(key)){
                    if(key == 1){
                        fila = saltos.createRow(i+1);
                    }else{
                        fila = saltos.getRow(i+1);
                    }
                    fila.createCell(key).setCellValue(val);
                    i++;
                }
                double count = 0;
                for(int z=0;z<promedios.get(key).size();z++){
                    count = count+promedios.get(key).get(z);
                }
                double media = count/reportes.size();
                fila = saltos.getRow(reportes.size()+3);
                fila.createCell(key).setCellValue(media);
                
                for(int z=0;z<promedios.get(key).size();z++){
                    count += Math.pow(promedios.get(key).get(z)-media,2);
                }
                fila = saltos.getRow(reportes.size()+4);
                fila.createCell(key).setCellValue(Math.pow((count/(reportes.size()-1))/reportes.size(),-2));
                
                dataset22.addValue(count/reportes.size(),"cont", key);
            }
            
            /*
            List<Float> keys = new ArrayList<Float>(promedios.keySet());
            Collections.sort(keys);
            for(Float key:keys){ // saltos vs tweets
                j++;
                Row fila2 = saltos.createRow(j+1);
                int x = 0;
                for(Float f:promedios.get(key)){
                    Cell celda001 = fila2.createCell(x+1);
                    celda001.setCellValue(f);
                    x++;
                }
                double count = 0;
                for(int z=0;z<promedios.get(key).size();z++){
                    count = count+promedios.get(key).get(z);
                }
                
                dataset22.addValue(count/promedios.get(key).size(),"cont", key);
                


            }
            * 
            */
            graficoFinal.barras(dataset22, "Promedios", "Saltos", "Promedios");
            
            
            i=0;
            fila = general.createRow(0);
            fila.createCell(1).setCellValue("tweet");    
            fila.createCell(2).setCellValue("reTweet");    
            fila.createCell(3).setCellValue("tweet por Usuario");    
            fila.createCell(4).setCellValue("tweet por usuario por unidad de tiempo");
            double tweetSum = 0;
            double retweetSum = 0;
            double tweetUsuarioSum = 0;
            double tweetUsuarioTiempoSum = 0;
            double tweetM = 0;
            double retweetM = 0;
            double tweetUsuarioM = 0;
            double tweetUsuarioTiempoM = 0;
            for(Reporte r:reportes){
                fila = general.createRow(i+1);
                fila.createCell(1).setCellValue(r.tweet);    
                fila.createCell(2).setCellValue(r.reTweet);    
                fila.createCell(3).setCellValue(r.tweetUsuario);    
                fila.createCell(4).setCellValue(r.tweetUsuarioTiempo); 
                tweetSum+=r.tweet;
                retweetSum+=r.reTweet;
                tweetUsuarioSum+=r.tweetUsuario;
                tweetUsuarioTiempoSum+=r.tweetUsuarioTiempo;
                i++;
            }
            
            tweetM = tweetSum/reportes.size();
            retweetM = retweetSum/reportes.size();
            tweetUsuarioM = tweetUsuarioSum/reportes.size();
            tweetUsuarioTiempoM = tweetUsuarioTiempoSum/reportes.size();
            fila = general.createRow(reportes.size()+4);
            fila.createCell(0).setCellValue("media");
            fila.createCell(1).setCellValue(tweetM);    
            fila.createCell(2).setCellValue(retweetM);    
            fila.createCell(3).setCellValue(tweetUsuarioM);    
            fila.createCell(4).setCellValue(tweetUsuarioTiempoM);
            tweetSum = 0;
            retweetSum = 0;
            tweetUsuarioSum = 0;
            tweetUsuarioTiempoSum = 0;
            for(Reporte r:reportes){
                tweetSum+=Math.pow(r.tweet-tweetM,2);
                retweetSum+=Math.pow(r.reTweet-retweetM,2);
                tweetUsuarioSum+=Math.pow(r.tweetUsuario-tweetUsuarioM,2);
                tweetUsuarioTiempoSum+=Math.pow(r.tweetUsuarioTiempo-tweetUsuarioTiempoM,2);
            }
            double tweetV = tweetSum/(reportes.size()-1);
            double retweetV = retweetSum/(reportes.size()-1);;
            double tweetUsuarioV = tweetUsuarioSum/(reportes.size()-1);;
            double tweetUsuarioTiempoV = tweetUsuarioTiempoSum/(reportes.size()-1);
            fila = general.createRow(reportes.size()+5);
            fila.createCell(0).setCellValue("varianza");
            fila.createCell(1).setCellValue(Math.pow(tweetV/reportes.size(),-2));    
            fila.createCell(2).setCellValue(Math.pow(retweetV/reportes.size(),-2));    
            fila.createCell(3).setCellValue(Math.pow(tweetUsuarioV/reportes.size(),-2));    
            fila.createCell(4).setCellValue(Math.pow(tweetUsuarioTiempoV/reportes.size(),-2));
            
            fila = general.createRow(reportes.size()+6);
            fila.createCell(0).setCellValue("x1");
            fila.createCell(1).setCellValue(reportes.size()-1);
            
            fila = general.createRow(reportes.size()+7);
            fila.createCell(0).setCellValue("x2");
            fila.createCell(1).setCellValue(1-(1-(95/100))/2);
            
            
            
            
            FileOutputStream archivo;
        
            archivo = new FileOutputStream("reporte.xlsx");
            reporte.write(archivo);
            archivo.close(); 

        }
        catch (FileNotFoundException ex) {
            Logger.getLogger(Output.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (IOException ex) {
            Logger.getLogger(Output.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.print("todas las simulaciones terminaron");
    }
    
    
    public synchronized static void generateNetwork(NetworkManager nm){
        
        Sheet network = reporte.createSheet("Red");
        Row fila;
        Cell cell;
        int follows = 0;
        int i = 0;
        fila = network.createRow(0);
        for(User u:nm.getUsers()){
            cell = fila.createCell(i+1);
            cell.setCellValue(u.getName());
            i++;
        }
        i = 0;
        int j = 0;
        for(User u:nm.getUsers()){
            fila = network.createRow(i+1);
            cell = fila.createCell(0);
            cell.setCellValue(u.getName());
            for(User u2:nm.getUsers()){
                cell = fila.createCell(j+1);
                if(u.getId() == u2.getId()){
                    cell.setCellValue("*");
                }else if(nm.getFollowings(u).contains(u2)){
                    cell.setCellValue("X");
                    follows++;
                }else{
                    cell.setCellValue("-");
                }
                j++;
            }
            cell = fila.createCell(j+1);
            cell.setCellValue(follows);
            follows = 0;
            j=0;
            i++;
        }
        
        
    }
}
