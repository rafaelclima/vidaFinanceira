package br.com.rafaellima.demo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.rafaellima.demo.service.AuthService;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class DemoApplicationTests {

	@MockBean
	private AuthService authService;

	@Test
	void contextLoads() {
	}

}