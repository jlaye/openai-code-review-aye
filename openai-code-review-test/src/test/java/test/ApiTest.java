package test;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class ApiTest {

    @Test
    public void test() {
        System.out.println(Integer.parseInt("abc1234"));
        System.out.println(Integer.parseInt("test1"));
        System.out.println(Integer.parseInt("test2"));
        System.out.println(Integer.parseInt("test3"));
        System.out.println(Integer.parseInt("test4"));
    }

}
