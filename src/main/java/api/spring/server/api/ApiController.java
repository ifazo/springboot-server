package api.spring.server.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController {
    
    private final Api api;

    public ApiController() {
        this.api = new Api();
    }

    @GetMapping("/api")
    public String getApi() {
        return api.getApi();
    }
}
