package dev.leonardovcl.sweetcontrol;

import org.hibernate.dialect.MySQL8Dialect;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.nativex.hint.NativeHint;
import org.springframework.nativex.hint.TypeHint;

@NativeHint(types = {
		@TypeHint(types = MySQL8Dialect.class, typeNames = "org.hibernate.dialect.MySQL8Dialect")
})
@SpringBootApplication
public class SweetControlApplication {

	public static void main(String[] args) {
		SpringApplication.run(SweetControlApplication.class, args);
	}

}
