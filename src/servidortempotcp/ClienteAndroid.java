package servidortempotcp;

import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ClienteAndroid extends Cliente{
    private final Log log;

    public ClienteAndroid(Socket s, String base_dir, Interconecao interconecao) {
        super(s, base_dir, interconecao);
        log = new Log(base_dir);
    }
    
    @Override
    public void run(){
        nome = (String) ouvir();
        String x = System.getProperty("os.name");  
        if (x.startsWith("Windows")){
            nome += "\\";
        }
        else{
            nome += "//";
        }
        int i = 0;
        while(i == 0){
            try {
            Thread.sleep(2000);
            i = Envia("Android");
            String recebido = (String) ouvir();
            if (recebido.equals("getList")){
                getList();
            }
            if (recebido.equals("setList")){
                setList();
            }
            } catch (Exception ex) {
                //System.out.println("Erro na run() "+ex.getMessage());
            }
        }    
    }
    
    private int getList() throws Exception{
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String ID = null;
        while(ID == null){
            ID = (String) ouvir();
        }
        ArrayList<Evento> lista = log.lerLog(nome, ID);
        for (Evento evento : lista){
            if (Envia("data"+formatter.format(evento.getData())) == 1) return 1;
            if (Envia("esta"+evento.getEstado()) == 1) return 1;
        }
        System.out.println("Lista enviada com sucesso");
        return Envia("ok");
    }
    
    private void setList() throws Exception{
        String ID = null;
        while(ID == null){
            ID = (String) ouvir();
        }
        boolean saida = false;
        try{
            if (interconecao.getNomeEstadoAUTO(nome, ID)) {
                interconecao.setNomeEstadoAUTO(nome, ID, false);
                saida = true;
            }
            else saida = false;
        }catch (Exception ex) {
            System.out.println("Erro "+ex.getMessage());
        }
                
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date date = new Date();
        String estado = new String();
        ArrayList<Evento> eventos = new ArrayList<>();
        while (true){
            String recebido = (String) ouvir();
            if (recebido.equals("ok")){
                log.EscreverLog(nome, ID, eventos);
                try{
                    interconecao.setNomeEstadoAUTO(nome, ID, saida);
                }catch(Exception ex) {}
                System.out.println("Lista recebida com sucesso");
                return;
            }
            if (recebido.lastIndexOf("data") >= 0) {
                date = (java.util.Date)formatter.parse(recebido.substring(4));
            } 
            if (recebido.lastIndexOf("esta") >= 0) {
                estado = recebido.substring(4);
                Evento aux = new Evento(date, estado);
                eventos.add(aux);
            }
        }
    }
}
