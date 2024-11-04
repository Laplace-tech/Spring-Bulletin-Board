package com.anna.sbb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class SbbApplication {

	public static void main(String[] args) {
		SpringApplication.run(SbbApplication.class, args);
	}

}

/*순수 get : 3,876,000
부수 get : 308,000

2,200,000 
=> 2/26, 3/11, 4/9 .... 12/10 
200,000 * 11 = 2,200,000
	4,400,000
2,200,000
=> 1/10, 2/20 ... 11/10 
200,000 * 11 = 2,200,000

22번 넣음 ㅑ캬캬캬캬

21개월

2+6+6+7 = 21

* 예상 수령액: 19,567,590원 <= 적금

* not적금 : 
3,000,000 + 1,100,000 = 4,100,000 (2024)
80 * 4 + 110 * 7 = 770 + 320 = 10,900,000  (2025)



*/