package example.api.v1.greetings.controllers;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import example.api.infra.ServiceResponse;
import example.api.v1.greetings.entities.Greeting;

@RestController
public class GreetingsController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    // http://localhost:8080/api/v1/greetings
    // http://localhost:8080/api/v1/greetings?name=foo
//    @RequestMapping(value = "/v1/greetings", method= RequestMethod.GET, produces = "application/json")
//    @RequestMapping("/v1/greetings")
    @RequestMapping(value = "/v1/greetings", method= RequestMethod.GET)
    public ServiceResponse<Greeting> greeting(@RequestParam(value="name", defaultValue="World") String name) {
        return new ServiceResponse<Greeting>(new Greeting(counter.incrementAndGet(),
                            String.format(template, name)));
    }
}
