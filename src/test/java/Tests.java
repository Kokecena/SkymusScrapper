import com.github.kokecena.skymus.service.SkymusServiceImplements;

public class Tests {
    public static void main(String[] args) {
        new SkymusServiceImplements().getTracks("eurobeat").subscribe(System.out::println);
    }
}
