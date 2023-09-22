package zhanuzak;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;

@SpringBootApplication
public class RestaurantProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestaurantProjectApplication.class, args);
        System.err.println("☺尺乇丂ｲﾑひ尺ﾑ刀ｲ ᄽᄿrunᄽᄿ");

        BigDecimal service = new BigDecimal("10");
        BigDecimal priceAverage = new BigDecimal("97");
        BigDecimal percentage=priceAverage.multiply(service.divide(new BigDecimal("100")));
        BigDecimal grantTotal=priceAverage.add(percentage);

        System.out.println("Итоговая сумма (с добавлением процентов): " + grantTotal);
    }

}
