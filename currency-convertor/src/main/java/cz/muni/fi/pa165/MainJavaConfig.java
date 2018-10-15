package cz.muni.fi.pa165;

import cz.muni.fi.pa165.currency.CurrencyConvertor;
import cz.muni.fi.pa165.currency.JavaConfig;
import cz.muni.fi.pa165.currency.LoggingAspect;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.math.BigDecimal;
import java.util.Currency;

public class MainJavaConfig {

    public static void main(String[] args) {
        Currency currencyCzk = Currency.getInstance("CZK");
        Currency currencyEur = Currency.getInstance("EUR");
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(JavaConfig.class, LoggingAspect.class);

        CurrencyConvertor convertor = applicationContext.getBean("currencyConvertor", CurrencyConvertor.class);

        System.out.println(convertor.convert(currencyEur, currencyCzk, new BigDecimal("10")));
    }
}
