package servidortempotcp;

import java.net.ServerSocket;

public class ServidorAndroid extends Thread{
    
    private int portaAndroid;
    private ServerSocket socketServerAndroid;
    private ClienteAndroid cliente_android;
    private String base_dir;
    private Interconecao interconecao;
    
    public ServidorAndroid(int androidPorta, String base_dir, Interconecao interconecao) throws InterruptedException{
        this.interconecao = interconecao;
        this.base_dir = base_dir;
        portaAndroid = androidPorta;
        try{
            socketServerAndroid = new java.net.ServerSocket(portaAndroid);
        }
        catch(Exception e){
            System.out.println("Erro criacao de socket android " +e.getMessage());
            Thread.sleep(500);
            System.exit(1);
        }
        System.out.println("Servidor Android no ar na porta " + portaAndroid + " !!");
    }
    
    @Override
    public void run(){
        while(true){
            try{
                cliente_android = new ClienteAndroid(socketServerAndroid.accept(), base_dir, interconecao);
                System.out.println("Android conectado");
                cliente_android.start();
            }
            catch (Exception e) {
                System.out.println("Erro: cliente android "+e.getMessage());
            }
        }
    }
}
