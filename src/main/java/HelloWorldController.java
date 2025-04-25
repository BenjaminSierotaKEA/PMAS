import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("")
public class HelloWorldController {

    @GetMapping("")
    public String helloWorld(){
        System.out.println("hello from hello world");
        return "hello-world";
    }
}
