import com.github.kokecena.skymus.service.SkymusServiceImplements;
import reactor.core.publisher.Mono;

public class Tests {
    public static void main(String[] args) {
        new SkymusServiceImplements().getTracks("eurobeatasdasdasasdasdasda")
                .switchIfEmpty(Mono.fromRunnable(() -> System.out.println("Vacio")))
                .subscribe(System.out::println);
    }
}
