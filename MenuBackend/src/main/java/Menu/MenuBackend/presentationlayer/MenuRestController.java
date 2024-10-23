package Menu.MenuBackend.presentationlayer;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class MenuRestController {

    @GetMapping("/test")
    public String test() {
        return "test123";
    }

}