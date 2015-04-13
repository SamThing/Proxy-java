package servidortempotcp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class Log {
    String base_dir;
    DateFormat formatter;
    DateFormat formatter_diario;
    
    public Log(String diretorio){
        base_dir = diretorio;   
        formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        formatter_diario = new SimpleDateFormat("HH:mm");
    }
    
    public void EscreverLog(String nome, String ID, ArrayList<Evento> lista) throws IOException{
        String msg;
        File diretorio = new File(base_dir+nome);
        if (!diretorio.exists()) {  
           diretorio.mkdirs(); //mkdir() cria somente um diretório, mkdirs() cria diretórios e subdiretórios.  
        }
        String path = (base_dir+nome+ID+".log");        
        try (BufferedWriter buffWrite = new BufferedWriter(new FileWriter(path))) {
            int i;
            msg = "“<?xml version=’1.0′ encoding=’ISO-8859-1′ ?>";
            buffWrite.append(msg + "\n");
            int n = lista.size();
            for (i=0; i<n; i++) {
                msg = "<evento>";
                buffWrite.append(msg + "\n");
                msg = "<data>"+formatter.format(lista.get(i).getData())+"<\\data>";
                buffWrite.append(msg + "\n");
                msg = "<estado>"+lista.get(i).getEstado()+"<\\estado>";
                buffWrite.append(msg + "\n");
                msg = "<\\evento>";
                buffWrite.append(msg + "\n");
                //System.out.println("Salvei: Data ("+new SimpleDateFormat("dd/MM/yyyy HH:mm").format(lista.get(i).getData())+") Estado "+lista.get(i).getEstado());
            }
        }
        System.out.println("Log salvo");
    }
    
    public void EscreverLog(String nome, String ID, ArrayList<Evento> lista, String sla) throws IOException{
        String msg;
        File diretorio = new File(base_dir+nome);
        if (!diretorio.exists()) {  
           diretorio.mkdirs(); //mkdir() cria somente um diretório, mkdirs() cria diretórios e subdiretórios.  
        }
        String path = (base_dir+nome+ID+".log");        
        try (BufferedWriter buffWrite = new BufferedWriter(new FileWriter(path))) {
            int i;
            msg = "“<?xml version=’1.0′ encoding=’ISO-8859-1′ ?>";
            buffWrite.append(msg + "\n");
            int n = lista.size();
            for (i=0; i<n; i++) {
                msg = "<evento>";
                buffWrite.append(msg + "\n");
                msg = "<data>"+formatter_diario.format(lista.get(i).getData())+"<\\data>";
                buffWrite.append(msg + "\n");
                msg = "<estado>"+lista.get(i).getEstado()+"<\\estado>";
                buffWrite.append(msg + "\n");
                msg = "<\\evento>";
                buffWrite.append(msg + "\n");
                //System.out.println("Salvei: Data ("+new SimpleDateFormat("HH:mm").format(lista.get(i).getData())+") Estado "+lista.get(i).getEstado());
            }
        }
        System.out.println("Log salvo");
    }

    public ArrayList<Evento> lerLog(String nome, String ID) throws IOException, ParseException {
        String path = base_dir+nome;
        File diretorio = new File(path);
        if (!diretorio.exists()) {  
           diretorio.mkdirs(); //mkdir() cria somente um diretório, mkdirs() cria diretórios e subdiretórios.  
        }
        path += ID+".log";
        File arq = new File(path);
        if (!arq.exists()) {  
            BufferedWriter buffWrite = new BufferedWriter(new FileWriter(arq));
        }
        ArrayList<Evento>  eventos = new ArrayList<>();
        eventos.clear();
        String data, estado;
        //leitor do arquivo texto (ponteiro)
        BufferedReader leitor = new BufferedReader(new FileReader(path));
        leitor.readLine(); //<xml
        while(true){
          leitor.readLine(); //<pessoa
          data=leitor.readLine();
          if(data == null)
            break;
          estado = leitor.readLine(); //</pessoa
          //Para pegar entre as Tags, foi preciso criar uma lógica para quebrar a String
          data = data.substring(data.indexOf(">")+1, data.indexOf("\\")-1); //pegando entre as tags
          estado = estado.substring(estado.indexOf(">")+1, estado.indexOf("\\")-1); //pegando entre as tags
          
          Date date = (java.util.Date)formatter.parse(data); 
          eventos.add(new Evento(date, estado));
          
          //System.out.println(date);
          
          leitor.readLine();
        }
        return OrganizaLista(eventos);
    }
    
    public ArrayList<Evento> lerLog(String nome, String ID, String sla) throws IOException, ParseException {
        String path = base_dir+nome;
        File diretorio = new File(path);
        if (!diretorio.exists()) {  
           diretorio.mkdirs(); //mkdir() cria somente um diretório, mkdirs() cria diretórios e subdiretórios.  
        }
        path += ID+"diario.log";
        File arq = new File(path);
        if (!arq.exists()) {  
            BufferedWriter buffWrite = new BufferedWriter(new FileWriter(arq));
        }
        ArrayList<Evento>  eventos = new ArrayList<>();
        eventos.clear();
        String data, estado;
        //leitor do arquivo texto (ponteiro)
        BufferedReader leitor = new BufferedReader(new FileReader(path));
        leitor.readLine(); //<xml
        while(true){
          leitor.readLine(); //<pessoa
          data=leitor.readLine();
          if(data == null)
            break;
          estado = leitor.readLine(); //</pessoa
          //Para pegar entre as Tags, foi preciso criar uma lógica para quebrar a String
          data = data.substring(data.indexOf(">")+1, data.indexOf("\\")-1); //pegando entre as tags
          estado = estado.substring(estado.indexOf(">")+1, estado.indexOf("\\")-1); //pegando entre as tags
          
          Date date = (java.util.Date)formatter_diario.parse(data); 
          eventos.add(new Evento(date, estado));
          
          //System.out.println(date);
          
          leitor.readLine();
        }
        return OrganizaLista(eventos);
    }
    
    public ArrayList<Evento> OrganizaLista(ArrayList<Evento> eventos){
        int i, j;
        int n = eventos.size();
        for (i = 0; i < n; i++) {
            for (j = 0; j < n; j++){
                if (eventos.get(i).getData().before(eventos.get(j).getData())){
                    Collections.swap(eventos, i, j);
                }
            }
        }
        return eventos;
    }
    
    public ArrayList<Evento> OrganizaLista(ArrayList<Evento> eventos, Date data){
        int i;
        boolean marcador = true;
        int n = eventos.size();
        for (i = 0; i < n; i++) {
            if (eventos.get(i).getData().before(data)){
                eventos.remove(eventos.get(i));
                marcador = false;
                break;
            }
        }
        if (!marcador) return OrganizaLista(eventos, data);
        return eventos;
    }
}