package cz.muni.fi.pa165.currency;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.Currency;


/**
 * This is base implementation of {@link CurrencyConvertor}.
 *
 * @author petr.adamek@embedit.cz
 */
public class CurrencyConvertorImpl implements CurrencyConvertor {

    private final ExchangeRateTable exchangeRateTable;
    private final Logger logger = LoggerFactory.getLogger(CurrencyConvertorImpl.class);

    public CurrencyConvertorImpl(ExchangeRateTable exchangeRateTable) {
        this.exchangeRateTable = exchangeRateTable;
    }

    @Override
    public BigDecimal convert(Currency sourceCurrency, Currency targetCurrency, BigDecimal sourceAmount) {
        logger.trace("Convert method called");

        checkArguments(sourceCurrency, targetCurrency, sourceAmount);
        BigDecimal exchangeRate = getExchangeRate(sourceCurrency, targetCurrency);

        BigDecimal result = sourceAmount.multiply(exchangeRate);
        result = result.setScale(2, BigDecimal.ROUND_HALF_EVEN);

        return result;
    }

    private void checkArguments(Currency sourceCurrency, Currency targetCurrency, BigDecimal sourceAmount) {
        if (sourceCurrency == null) {
            throw new IllegalArgumentException("Source currency can not be null");
        } else if (targetCurrency == null) {
            throw new IllegalArgumentException("Target currency can not be null");
        } else if (sourceAmount == null) {
            throw new IllegalArgumentException("Amount can not be null");
        }
    }

    private BigDecimal getExchangeRate(Currency sourceCurrency, Currency targetCurrency) {
        try {
            BigDecimal exchangeRate = exchangeRateTable.getExchangeRate(sourceCurrency, targetCurrency);
            if (exchangeRate == null) {
                throw new NullPointerException();
            }
            return exchangeRate;
        } catch (ExternalServiceFailureException e) {
            logger.error(e.getMessage());
            throw new UnknownExchangeRateException("External service failure", e);
        } catch (NullPointerException e) {
            logger.warn("Unknown exchange rate");
            throw new UnknownExchangeRateException("Unknown exchange rate", e);
        }
    }


}
