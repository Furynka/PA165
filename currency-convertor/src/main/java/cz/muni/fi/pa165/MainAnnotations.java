package cz.muni.fi.pa165;

import cz.muni.fi.pa165.currency.CurrencyConvertor;
import cz.muni.fi.pa165.currency.CurrencyConvertorImpl;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.math.BigDecimal;
import java.util.Currency;

public class MainAnnotations {
    public static void main(String[] args) {
        Currency currencyCzk = Currency.getInstance("CZK");
        Currency currencyEur = Currency.getInstance("EUR");
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("cz.muni.fi.pa165");

        CurrencyConvertor convertor = (CurrencyConvertor) context.getBean(CurrencyConvertorImpl.class);

        System.out.println(convertor.convert(currencyEur,currencyCzk, new BigDecimal("10")));
    }
}
