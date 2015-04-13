package servidortempotcp;

public class Main {
    public static void main(String[] a) throws Exception{
        Interconecao interconecao = new Interconecao();
        String base_dir;
        String x = System.getProperty("os.name");  
        System.out.println(x);
        if (x.startsWith("Windows")){
            System.out.println("Sou Windows!!");
            base_dir = new String("C:\\dropbox\\");
        }
        else{
            System.out.println("Sou linux!!");
            base_dir = "//var//www//Dropbox//Plugman//";
        }
        ServidorAndroid android = new ServidorAndroid(1234, base_dir, interconecao);
        android.start();
        ServidorDispositivo dispositivo = new ServidorDispositivo(55, base_dir, interconecao);
        dispositivo.start();
    }
}
