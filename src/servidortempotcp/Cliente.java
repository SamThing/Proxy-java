package servidortempotcp;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Cliente extends Thread{
        private ObjectOutputStream output;
        private ObjectInputStream  inStream;
        private Socket connection;
        public String nome;
        public String base_dir;
        public Interconecao interconecao;
        
        public Cliente(Socket s, String base_dir, Interconecao interconecao){
            this.interconecao = interconecao;
            this.base_dir = base_dir;
            connection = s;
            try{
                output = new ObjectOutputStream(s.getOutputStream());
                }
            catch (Exception e) {
                System.out.println("Erro: out "+e.getMessage());
            }
            try{
                inStream = new ObjectInputStream(s.getInputStream());
                }
            catch (Exception e) {
                System.out.println("Erro: in "+e.getMessage());
            }
            try{
                output.flush();
            }
            catch (Exception e) {
                System.out.println("Erro: flush "+e.getMessage());
            }
        }
        
        public int Envia(Object pac){
            try{
                output.writeObject(pac);
                output.flush();
                return 0;
            }
            catch (Exception e){
                String texto = "";
                if (pac instanceof String) texto = (String) pac;
                System.out.println("Erro de envio de mensagem  ("+texto+")");
                System.out.println(e.getMessage());
                System.out.println("Cliente desconectado");
                this.closeConnection(); 
                return 1;
            }
        }            
        
        public Object ouvir(){
            Object recebido = new Object();
            try {
                recebido = inStream.readObject();
            }
            catch (Exception e ){
                System.out.println(e.getMessage());
                return null;
            }
            return recebido;
        }
        
        private void closeConnection(){
            try{
                if (this.isAlive()){
                    this.interrupt();
                    connection.close();
                }	 
            }
            catch (Exception exp){
                System.out.println("Cliente ausente");
            }
        }
    }
