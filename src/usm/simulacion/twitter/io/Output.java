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
import org.apache.poi.hssf.usermodel.*;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import usm.simulacion.twitter.core.EventBus;
import usm.simulacion.twitter.core.SimulationEvent;
import usm.simulacion.twitter.core.SimulationEventHandler;
import usm.simulacion.twitter.simulator.*;

/**
 *
 * @author camilovera
 */
public class Output {
    
    

private EventBus eventBus;
    private NetworkManager networkManager;
    private boolean finalizo;
    private long tweetCounter;
    private long reTweetCounter;
    private long largo;
    private HSSFWorkbook libro = new HSSFWorkbook();
    private DefaultPieDataset dataset = new DefaultPieDataset();  // saltos torta
    private DefaultCategoryDataset dataset2 = new DefaultCategoryDataset(); // saltos barra
    private DefaultCategoryDataset dataset3 = new DefaultCategoryDataset(); // comparacion recividos vs enviados
    private DefaultCategoryDataset dataset4 = new DefaultCategoryDataset(); // comparacion usuario tipo 1
    private DefaultCategoryDataset dataset5 = new DefaultCategoryDataset(); // comparacion usuario tipo 2
    private DefaultCategoryDataset dataset6 = new DefaultCategoryDataset(); // comparacion usuario tipo 3
    
    



    public Output(EventBus eventBus,NetworkManager networkManager) throws FileNotFoundException, IOException{
        
        

        this.eventBus = eventBus;
        this.networkManager = networkManager;
        finalizo = false;
        tweetCounter = 0;
        reTweetCounter = 0;
        bind();


        

    }


    

    private String getTime(){
        return "Time: "+networkManager.getCurrentTime()+"________>";
    }
  
    
    private void bind() throws FileNotFoundException, IOException{

        
        eventBus.registerEventHandler(AddUserEvent.TYPE, new AddUserEventHandler(){

                    int filaCounter = 0;
                    HSSFSheet hoja = libro.createSheet("Users");
                    
                    
                    
            @Override


            public void addUser(AddUserEvent event) {
                
                    
                    
                if(!finalizo){
                    
                    

                    HSSFRichTextString texto = new HSSFRichTextString(event.getUser().getName());
                    HSSFRichTextString texto2 = new HSSFRichTextString(event.getUser().getType());

                    
                    HSSFRow fila = hoja.createRow(filaCounter+1);


                    HSSFCell celda = fila.createCell(0);
                    HSSFCell celda2 = fila.createCell(1);

                    celda.setCellValue(texto);
                    celda2.setCellValue(texto2);
                    
                    filaCounter++;
                    
                    
                }
            }
        });
        
        eventBus.registerEventHandler(NewTweetEvent.TYPE, new NewTweetEventHandler() {
            
            int filaCounter2 = 0;
            int cellCounter2 = 0;
           
            HSSFSheet hoja2 = libro.createSheet("Tweets");
            
            @Override
            public void onNewTweet(NewTweetEvent event) {
                if(!finalizo){
                    
                    tweetCounter++;

                    //System.out.println(tweetCounter+") "+getTime()+"el usuario: "+event.getUser().getName()+" a generado un nuevo tweet con id: "+event.getTweet().getId());

                    HSSFRichTextString timeTweet1 = new HSSFRichTextString(getTime());
                    HSSFRichTextString nameUser1 = new HSSFRichTextString(event.getUser().getName());

                    HSSFRow fila = hoja2.createRow(filaCounter2+1);

                    HSSFCell celda = fila.createCell(cellCounter2);
                    HSSFCell celda2 = fila.createCell(cellCounter2+1);

                    celda.setCellValue(timeTweet1);
                    celda2.setCellValue(nameUser1);

                    filaCounter2++;

         


                }
            }
        });
        
        eventBus.registerEventHandler(NewReTweetEvent.TYPE, new NewReTweetEventHandler() {

            int filaCounter3 = 0;
            int cellCounter3 = 0;
            
            HSSFSheet hoja3 = libro.createSheet("ReTweets");

            @Override
            public void onReTweet(NewReTweetEvent event) {
                if(!finalizo){

                    reTweetCounter++;

                    //System.out.println(reTweetCounter+") "+getTime()+"el usuario: "+event.getUser().getName()+" a retuitiado un tweet con id: "+event.getTweet().getId()+" cuyo autor original es: "+event.getTweet().getOwner().getName());

                    HSSFRichTextString timeTweet1 = new HSSFRichTextString(getTime());
                    HSSFRichTextString nameUser1 = new HSSFRichTextString(event.getUser().getName());
                    HSSFRichTextString idCounter1 = new HSSFRichTextString(String.valueOf(event.getTweet().getId()));
                    HSSFRichTextString originUser1 = new HSSFRichTextString(event.getTweet().getOwner().getName());


                    HSSFRow fila = hoja3.createRow(filaCounter3+1);
                    HSSFRow fila0 = hoja3.createRow(0);

                    HSSFCell cel1 = fila0.createCell(0);
                    HSSFCell cel2 = fila0.createCell(1);
                    HSSFCell cel3 = fila0.createCell(2);
                    HSSFCell cel4 = fila0.createCell(3);

                    HSSFCell celda = fila.createCell(cellCounter3);
                    HSSFCell celda2 = fila.createCell(cellCounter3+1);
                    HSSFCell celda3 = fila.createCell(cellCounter3+2);
                    HSSFCell celda4 = fila.createCell(cellCounter3+3);

                    cel1.setCellValue("Tiempo");
                    cel2.setCellValue("Usuario");
                    cel3.setCellValue("Id Tweet");
                    cel4.setCellValue("Usuario Inicial");


                    celda.setCellValue(timeTweet1);
                    celda2.setCellValue(nameUser1);
                    celda3.setCellValue(idCounter1);
                    celda4.setCellValue(originUser1);

                    filaCounter3++;

                    

                }
            }
        });
        
        eventBus.registerEventHandler(SimulationEvent.TYPE, new SimulationEventHandler() {

            @Override
            public void onSimulationEvent(SimulationEvent event) {
                if(event.getState() == SimulationEvent.FINISH){
                    try {
                        finalizo = true;
                        generateReport();
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(Output.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(Output.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            FileOutputStream archivo;
                    try {
                        archivo = new FileOutputStream("Libro.xls");
                        try {
                            libro.write(archivo);
                            archivo.close();
                        } catch (IOException ex) {
                            Logger.getLogger(Output.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(Output.class.getName()).log(Level.SEVERE, null, ex);
                    }



            }
        });


        
        

    }
    
    
    private void generateReport() throws FileNotFoundException, IOException{

        int filaCounter4 = 0;
        int cellCounter4 = 0;
        HSSFSheet hoja4 = libro.createSheet("Reporte");

        HSSFRow fila = hoja4.createRow(filaCounter4);
        HSSFRow fila1 = hoja4.createRow(filaCounter4+1);

        HSSFCell celda = fila.createCell(cellCounter4);
        HSSFCell celda2 = fila.createCell(cellCounter4+1);
        HSSFCell celda3 = fila.createCell(cellCounter4+2);
        HSSFCell celda4 = fila.createCell(cellCounter4+3);
        HSSFCell celda5 = fila.createCell(cellCounter4+3);


        celda.setCellValue("Tweets Nuevos Generados");
        celda2.setCellValue("ReTweets Generados");
        celda3.setCellValue("Tweets Totales");
        celda4.setCellValue("Tweets Promedio Por Usuario");
        celda5.setCellValue("tweets promedio por usuario por unidad de tiempo");


        HSSFCell celdas = fila1.createCell(cellCounter4);
        HSSFCell celdas2 = fila1.createCell(cellCounter4+1);
        HSSFCell celdas3 = fila1.createCell(cellCounter4+2);
        HSSFCell celdas4 = fila1.createCell(cellCounter4+3);
        HSSFCell celdas5 = fila1.createCell(cellCounter4+3);

        List<User> users2 = networkManager.getUsers();
        largo = users2.size();
        long promedio = (tweetCounter+reTweetCounter)/largo;
        

        celdas.setCellValue(tweetCounter);
        celdas2.setCellValue(reTweetCounter);
        celdas3.setCellValue(tweetCounter+reTweetCounter);
        celdas4.setCellValue(promedio);
        celdas5.setCellValue(((tweetCounter+reTweetCounter)/users2.size())/networkManager.getCurrentTime());

         
         





        System.out.println();
        System.out.println();
        System.out.println("===============REPORTE===================");
        System.out.println();
        System.out.println("tweets nuevos generados: "+tweetCounter);
        System.out.println("reTweets generados: "+reTweetCounter);
        System.out.println("tweets totales: "+(tweetCounter+reTweetCounter));
        List<User> users = networkManager.getUsers();
        System.out.println("tweets promedio por usuario: "+((tweetCounter+reTweetCounter)/users.size()));
        System.out.println("tweets promedio por usuario por unidad de tiempo: "+((tweetCounter+reTweetCounter)/users.size())/networkManager.getCurrentTime());
        
        Map<Integer,Integer> counters = new HashMap<Integer, Integer>();
        

        
        for(User user:users){
            for(Tweet tweet:user.getIncomingTweets()){
                if(counters.containsKey(tweet.getSteps())){
                    counters.put(tweet.getSteps(),counters.get(tweet.getSteps())+1);
                }else{
                    counters.put(tweet.getSteps(),1);
                }
            }
        }

        for(User user:users){

                  
                    String u1 = "Normal";
                    String u2 = "Empresa";
                    String u3 = "Famoso";

                    // set de valores para grafico barras todos los usuarios
                    dataset3.addValue(user.getIncomingTweets().size(), "Tweets Recibidos", user.getName());
                    dataset3.addValue(user.getOwnTweets().size(), "Tweets Enviados", user.getName());

                    // discriminacion de tipos de usuarios para setear valores para sus respectivos graficos
                    if(u1.equals(user.getType())){

                             dataset4.addValue(user.getIncomingTweets().size(), "Tweets Recibidos", user.getName());
                             dataset4.addValue(user.getOwnTweets().size(), "Tweets Enviados", user.getName());
                         }
                        if(u2.equals(user.getType())){

                                    dataset5.addValue(user.getIncomingTweets().size(), "Tweets Recibidos", user.getName());
                                    dataset5.addValue(user.getOwnTweets().size(), "Tweets Enviados", user.getName());
                                                         }
                                if(u3.equals(user.getType())){

                                            dataset6.addValue(user.getIncomingTweets().size(), "Tweets Recibidos", user.getName());
                                            dataset6.addValue(user.getOwnTweets().size(), "Tweets Enviados", user.getName());
                                
                                                    }
                    }
            
        



        System.out.println("--------Histograma------------");
        List<Integer> keys = new ArrayList<Integer>(counters.keySet());
        Collections.sort(keys);
        for(Integer key:keys){
            System.out.println(key+" saltos --------> "+counters.get(key));

            // setear valores de los saltos
            dataset.setValue(key, counters.get(key));  // valores para grafico torta
            dataset2.addValue(counters.get(key),"cont", key); // valores grafico barras

         


        }

        GraphOut g1 = new GraphOut();
        GraphOut g2 = new GraphOut();
        GraphOut g3 = new GraphOut();
        GraphOut g4 = new GraphOut();
        GraphOut g5 = new GraphOut();
        GraphOut g6 = new GraphOut();

        // generacion de graficos

        g2.barras(dataset2, "Saltos", "Saltos", "Cantidad Tweets");
        g1.pastel(dataset, "SaltosTweets");
        g3.barras(dataset3, "Recibidos vs Enviados", "Usuarios", "cantidad");
        g4.barras(dataset4, "Recibidos vs Enviados I", "Usuarios Normales", "cantidad");
        g5.barras(dataset5, "Recibidos vs Enviados II", "Usuarios Empresas", "cantidad");
        g6.barras(dataset6, "Recibidos vs Enviados III", "Usuarios Famosos", "cantidad");


       


    }
    
    
    
    
    /*
    private void bind(){
        eventBus.registerEventHandler(AddUserEvent.TYPE, new AddUserEventHandler() {

            @Override
            public void addUser(AddUserEvent event) {
                if(!finalizo){
                    System.out.println(getTime()+"se a añadido un nuevo usuario: "+event.getUser().getName());
                }
            }
        });
        
        eventBus.registerEventHandler(NewTweetEvent.TYPE, new NewTweetEventHandler() {

            @Override
            public void onNewTweet(NewTweetEvent event) {
                if(!finalizo){
                    tweetCounter++;
                    System.out.println(tweetCounter+") "+getTime()+"el usuario: "+event.getUser().getName()+" a generado un nuevo tweet con id: "+event.getTweet().getId());
                }
            }
        });
        
        eventBus.registerEventHandler(NewReTweetEvent.TYPE, new NewReTweetEventHandler() {

            @Override
            public void onReTweet(NewReTweetEvent event) {
                if(!finalizo){
                    reTweetCounter++;
                    System.out.println(reTweetCounter+") "+getTime()+"el usuario: "+event.getUser().getName()+" a retuitiado un tweet con id: "+event.getTweet().getId()+" cuyo autor original es: "+event.getTweet().getOwner().getName());
                }
            }
        });
        
        eventBus.registerEventHandler(SimulationEvent.TYPE, new SimulationEventHandler() {

            @Override
            public void onSimulationEvent(SimulationEvent event) {
                if(event.getState() == SimulationEvent.FINISH){
                    finalizo = true;
                    generateReport();
                }
            }
        });
    }
    
    
    private void generateReport(){
        System.out.println();
        System.out.println();
        System.out.println("===============REPORTE===================");
        System.out.println();
        System.out.println("tweets nuevos generados: "+tweetCounter);
        System.out.println("reTweets generados: "+reTweetCounter);
        System.out.println("tweets totales: "+(tweetCounter+reTweetCounter));
        List<User> users = networkManager.getUsers();
        System.out.println("tweets promedio por usuario: "+((tweetCounter+reTweetCounter)/users.size()));
        System.out.println("tweets promedio por usuario por unidad de tiempo: "+((tweetCounter+reTweetCounter)/users.size())/networkManager.getCurrentTime());
        
        Map<Integer,Integer> counters = new HashMap<Integer, Integer>();
        for(User user:users){
            for(Tweet tweet:user.getIncomingTweets()){
                if(counters.containsKey(tweet.getSteps())){
                    counters.put(tweet.getSteps(),counters.get(tweet.getSteps())+1);
                }else{
                    counters.put(tweet.getSteps(),1);
                }
            }
        }
        System.out.println("--------Histograma------------");
        List<Integer> keys = new ArrayList<Integer>(counters.keySet());
        Collections.sort(keys);
        for(Integer key:keys){
            System.out.println(key+" saltos --------> "+counters.get(key));
        }
    }
    * 
    */
}