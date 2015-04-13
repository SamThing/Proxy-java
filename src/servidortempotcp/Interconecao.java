package servidortempotcp;

import java.util.ArrayList;

public class Interconecao {
    public ArrayList<NomeEstado> dispositivos;
    
    public Interconecao(){
        dispositivos = new ArrayList<>();
    }
    
    public void addNomeEstado(String nome, String ID){
        int i, n;
        n = dispositivos.size();
        for (i = 0; i < n; i++){
            if (nome.equals(dispositivos.get(i).getNome()) && ID.equals(dispositivos.get(i).getID())){
                return;
            }
        }
        dispositivos.add(new NomeEstado(nome, ID));
    }
    
    public NomeEstado getNomeEstado(String nome, String ID){
        int i, n;
        n = dispositivos.size();
        for (i = 0; i < n; i++){
            if (nome.equals(dispositivos.get(i).getNome()) && ID.equals(dispositivos.get(i).getID())){
                return dispositivos.get(i);
            }
        }
        return null;
    }
    
    public void removeNomeEstado(String nome, String ID){
        int i, n;
        n = dispositivos.size();
        for (i = 0; i < n; i++){
            if (nome.equals(dispositivos.get(i).getNome()) && ID.equals(dispositivos.get(i).getID())){
                dispositivos.remove(i);
                System.out.println("Removi o dispositivo");
                break;
            }
        }
    }
    
    public void setNomeEstadoAUTO(String nome, String ID,boolean auto) throws Exception{
        int i, n;
        n = dispositivos.size();
        for (i = 0; i < n; i++){
            if (nome.equals(dispositivos.get(i).getNome()) && ID.equals(dispositivos.get(i).getID())){
                dispositivos.get(i).setAuto(auto);
                return;
            }
        }
        throw new Exception("Dispositivo OFF");
    }
    
    public boolean getNomeEstadoAUTO(String nome, String ID) throws Exception{
        int i, n;
        n = dispositivos.size();
        if (dispositivos.isEmpty()) {
            throw new Exception("Dispositivo OFF");
        }
        for (i = 0; i < n; i++){
            if (nome.equals(dispositivos.get(i).getNome()) && ID.equals(dispositivos.get(i).getID())){
                return dispositivos.get(i).isAuto();
            }
        }
        throw new Exception("Dispositivo OFF");
    }
    
    public ArrayList<String> getDispositivosOnLine(String nome){
        ArrayList<String> nomes = new ArrayList<>();
        int i, n;
        n = dispositivos.size();
        for (i = 0; i < n; i++){
            if (nome.equals(dispositivos.get(i).getNome())){
                nomes.add(dispositivos.get(i).getID());
            }
        }
        return nomes;
    }
    
}
