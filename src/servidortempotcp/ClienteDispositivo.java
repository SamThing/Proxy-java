package servidortempotcp;

import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ClienteDispositivo extends Cliente{
    private String ID;

    public ClienteDispositivo(Socket s, String base_dir, Interconecao interconecao) {
        super(s, base_dir, interconecao);
    }
    
    @Override
    public void run(){
        //nome = (String) ouvir(); ID = (String) ouvir(); 
        nome = "sam"; ID = "teste";
        String x = System.getProperty("os.name");  
        if (x.startsWith("Windows")){
            nome += "\\";
        }
        else{
            nome += "//";
        }
        interconecao.addNomeEstado(nome, ID);
        String tempo_antigo = "blablabla";
        int i = 0;
        while(i == 0){
            try {
            Thread.sleep(2000);
            
            //Object recebido = ouvir();
            if (interconecao.getNomeEstado(nome, ID).isAuto()){
                String tempo_atual = Tempo();
                String tempo_dia_atual = tempo_atual.substring(11, 16);
                if (!tempo_atual.equals(tempo_antigo)) { 
                    if (VerificaEventosDia(tempo_dia_atual)== 1) break;
                    if (VerificaEventosCalendario(tempo_atual) == 1) break;
                    tempo_antigo = tempo_atual;
                    System.out.println("Tempo atual: "+tempo_dia_atual);
                }
            }
            else{
                tempo_antigo = "blablabla";
                if (Envia(interconecao.getNomeEstado(nome, ID).getEstado()) == 1){
                    break;
                }
            }
            } catch (Exception ex) {
                //System.out.println("Erro  "+ex.getMessage());
            }
        }    
        interconecao.removeNomeEstado(nome, ID);
        System.exit(1);
    }
        
    public String Tempo(){
        return new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date());
    }
    
    private int VerificaEventosCalendario(String data) throws Exception{
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        ArrayList<Evento> eventos = null;
        Log log = new Log(base_dir);
        eventos = log.OrganizaLista(log.lerLog(nome, ID), (java.util.Date)formatter.parse(data));
        log.EscreverLog(nome, ID, eventos);
        int n = eventos.size();
        int i = 0;
            for (i = 0; i < n; i++){
                if (formatter.format(eventos.get(i).getData()).equals(data)){
                    System.out.println("Enviei o evento "+eventos.get(i).getEstado());
                    return Envia(eventos.get(i).getEstado());
                }
            }
        return Envia(" ");
    }
    
    private int VerificaEventosDia(String data) throws Exception{
        DateFormat formatter = new SimpleDateFormat("HH:mm");
        ArrayList<Evento> eventos = null;
        Log log = new Log(base_dir);
        eventos = log.OrganizaLista(log.lerLog(nome, ID, "diario"), (java.util.Date)formatter.parse(data));
        int n = eventos.size();
        int i = 0;
            for (i = 0; i < n; i++){
                if (formatter.format(eventos.get(i).getData()).equals(data)){
                    System.out.println("Enviei o evento "+eventos.get(i).getEstado());
                    return Envia(eventos.get(i).getEstado());
                }
            }
        return Envia(" ");
    }
    
}