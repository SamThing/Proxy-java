package servidortempotcp;

import java.net.ServerSocket;

public class ServidorDispositivo extends Thread{
    
    private int portaDispositivo;
    private ServerSocket socketServerDispositivo;
    private ClienteDispositivo cliente_dispositivo;
    private String base_dir;
    private Interconecao interconecao;
    
    public ServidorDispositivo(int dispositivoPorta, String base_dir, Interconecao interconecao) throws InterruptedException{
        this.interconecao = interconecao;
        this.base_dir = base_dir;
        portaDispositivo = dispositivoPorta;
        try{
            socketServerDispositivo = new java.net.ServerSocket(portaDispositivo);
        }
        catch(Exception e){
            System.out.println("Erro criacao de socket dispositivo " +e.getMessage());
            Thread.sleep(500);
            System.exit(1);
        }
        System.out.println("Servidor Dispositivo no ar na porta " + portaDispositivo + " !!");
    }
    
    @Override
    public void run(){
        while(true){
            try{
                cliente_dispositivo = new ClienteDispositivo(socketServerDispositivo.accept(), base_dir, interconecao);
                System.out.println("Dispositivo conectado");
                cliente_dispositivo.start();
            }
            catch (Exception e) {
                System.out.println("Erro: cliente dispositivo "+e.getMessage());
            }
        }
    }
}
