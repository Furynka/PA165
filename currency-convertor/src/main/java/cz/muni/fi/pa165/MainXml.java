package cz.muni.fi.pa165;


import cz.muni.fi.pa165.currency.CurrencyConvertor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.math.BigDecimal;
import java.util.Currency;

public class MainXml{
    public static void main(String[] args) {
        Currency currencyCzk = Currency.getInstance("CZK");
        Currency currencyEur = Currency.getInstance("EUR");

        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        CurrencyConvertor convertor = (CurrencyConvertor) context.getBean("CurrencyConvertorImpl");

        System.out.println(convertor.convert(currencyEur, currencyCzk, new BigDecimal("10")));
    }
}
