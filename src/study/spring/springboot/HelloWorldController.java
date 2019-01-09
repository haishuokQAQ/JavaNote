package study.spring.springboot;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {
	@RequestMapping("index/{name}")
	@ResponseBody
	public String index(@PathVariable String name) {
		return "Hello" + name;
	}
}
