import com.github.kokecena.skymus.service.SkymusServiceImplements;

import java.io.IOException;

public class Tests {
    public static void main(String[] args) throws IOException {
        new SkymusServiceImplements().getTracks("eurobeat").subscribe(System.out::println);
    }
}
