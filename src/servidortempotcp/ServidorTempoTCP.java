package servidortempotcp;

import java.net.ServerSocket;

public class ServidorTempoTCP extends Thread{
    
    private int portaDispositivo;
    private int portaAndroid;
    private ServerSocket socketServerDispositivo;
    private ServerSocket socketServerAndroid;
    private ClienteDispositivo cliente_dispositivo;
    private ClienteAndroid cliente_android;
    private String base_dir;
    private Interconecao interconecao;
    
    public ServidorTempoTCP(int androidPorta, int dispositivoPorta, String base_dir, Interconecao interconecao) throws Exception{
        this.interconecao = interconecao;
        this.base_dir = base_dir;
        portaDispositivo = dispositivoPorta;
        portaAndroid = androidPorta;
        try{
            socketServerDispositivo = new java.net.ServerSocket(portaDispositivo);
        }
        catch(Exception e){
            System.out.println("Erro criacao de socket dispositivo " +e.getMessage());
            Thread.sleep(500);
            System.exit(1);
        }
        System.out.println("Servidor Dispositivo no ar na porta " + portaDispositivo + " !!");
        
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
                cliente_dispositivo = new ClienteDispositivo(socketServerDispositivo.accept(), base_dir, interconecao);
                System.out.println("cliente da dispositivo conectado");
                cliente_dispositivo.start();
                
                cliente_android = new ClienteAndroid(socketServerAndroid.accept(), base_dir, interconecao);
                System.out.println("cliente android conectado");
                cliente_android.start();
            }
            catch (Exception e) {
                System.out.println("Erro: cliente dispositivo "+e.getMessage());
            }
        }
    }
    
}
