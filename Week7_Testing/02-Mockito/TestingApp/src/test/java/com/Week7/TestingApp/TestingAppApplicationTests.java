package com.Week7.TestingApp;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.assertj.core.data.Offset;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;


@Slf4j
class TestingAppApplicationTests {


    @BeforeAll
    static void setupOnce() {
        log.info("Setiing up...");
    }
    @AfterAll
    static void tearDown() {
        log.info("tearing down the method");
    }



	@Test
    //@Disabled
	void testNumberOne() {
        int a=5;
        int b=4;

        int result=addTwoNumbers(a,b);
        //Assertions.assertEquals(9,result);
        Assertions.assertThat(result)
                .isEqualTo(9)
                .isCloseTo(8, Offset.offset(1));

	}

    @Test
    void testNumberTwo() {
        int a=5;
        int b=0;
        //double result=divideTwoNumbers(a,b);

        Assertions.assertThatThrownBy(()->divideTwoNumbers(a,b))
                .isInstanceOf(ArithmeticException.class);
    }

    int addTwoNumbers(int a,int b){
        return a+b;
    }

    double divideTwoNumbers(int a,int b){

        try {
            double result = a / b;
            return result;
        } catch (ArithmeticException e) {
            log.error(e.getLocalizedMessage());
            throw new ArithmeticException(e.getLocalizedMessage());
        }

    }

}
