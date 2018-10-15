package cz.muni.fi.pa165.currency;

import javax.inject.Named;
import java.math.BigDecimal;
import java.util.Currency;

@Named
public class ExchangeRateTableImpl implements ExchangeRateTable {

    private BigDecimal conversionRate;

    @Override
    public BigDecimal getExchangeRate(Currency sourceCurrency, Currency targetCurrency) throws ExternalServiceFailureException {
        if (sourceCurrency.getCurrencyCode().equals("EUR") && targetCurrency.getCurrencyCode().equals("CZK")) {
            return new BigDecimal("27");
        }
        return null;
    }

    public void setConversionRate(String conversionRate) {
        this.conversionRate = new BigDecimal(conversionRate);
    }

    public BigDecimal getConversionRate() {
        return conversionRate;
    }
}
