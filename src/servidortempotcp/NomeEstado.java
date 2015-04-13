package servidortempotcp;

public class NomeEstado {
    private String nome;
    private String ID;
    private String estado;
    private boolean auto;

    public boolean isAuto() {
        return auto;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setAuto(boolean auto) {
        this.auto = auto;
    }
    
    public NomeEstado(String nome, String ID){
        this.nome = nome;
        this.ID = ID;
        this.estado = "";
        this.auto = true;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
