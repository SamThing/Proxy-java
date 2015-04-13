package servidortempotcp;

import java.util.Date;

public class Evento {
    Date data;
    String estado;

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }
    
    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    public Evento(Date a, String b){
        data = a;
        estado = b;
    }
    
    public Evento(){
        data = null;
        estado = null;
    }
    
}
