package cz.muni.fi.pa165.currency;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.ServiceConfigurationError;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CurrencyConvertorImplTest {

    @Mock
    private ExchangeRateTable exchangeRateTable;
    private CurrencyConvertor convertor;
    private Currency currencyEur = Currency.getInstance("EUR");
    private Currency currencyCzk = Currency.getInstance("CZK");
    private BigDecimal testAmount = new BigDecimal("10");

    @Before
    public void beforeEachTest() {
        convertor = new CurrencyConvertorImpl(exchangeRateTable);
    }

    @Test
    public void testConvert() throws ExternalServiceFailureException {
        when(exchangeRateTable.getExchangeRate(currencyEur, currencyCzk)).thenReturn(new BigDecimal("24.567"));

        BigDecimal result = convertor.convert(currencyEur, currencyCzk, new BigDecimal("12.34"));
        assertThat(result).isEqualTo(new BigDecimal("303.16"));
    }

    @Test
    public void testConvertWithNullSourceCurrency() {
        assertThatExceptionOfType(IllegalArgumentException.class).
                isThrownBy(() -> convertor.convert(null, currencyCzk, testAmount)).
                withMessage("Source currency can not be null");
    }

    @Test
    public void testConvertWithNullTargetCurrency() {
        assertThatExceptionOfType(IllegalArgumentException.class).
                isThrownBy(() -> convertor.convert(currencyCzk, null, testAmount)).
                withMessage("Target currency can not be null");
    }

    @Test
    public void testConvertWithNullSourceAmount() {
        assertThatExceptionOfType(IllegalArgumentException.class).
                isThrownBy(() -> convertor.convert(currencyEur, currencyCzk, null)).
                withMessage("Amount can not be null");
    }

    @Test
    public void testConvertWithUnknownCurrency() throws ExternalServiceFailureException{
        when(exchangeRateTable.getExchangeRate(currencyEur, currencyCzk)).thenReturn(null);

        assertThatExceptionOfType(UnknownExchangeRateException.class).
                isThrownBy(() -> convertor.convert(currencyEur, currencyCzk, testAmount)).
                withMessage("Unknown exchange rate");
    }

    @Test
    public void testConvertWithExternalServiceFailure() throws ExternalServiceFailureException {
        ExternalServiceFailureException exception = new ExternalServiceFailureException("External service failure");

        when(exchangeRateTable.getExchangeRate(currencyEur, currencyCzk)).
                thenThrow(exception);

        assertThatExceptionOfType(UnknownExchangeRateException.class).
                isThrownBy(() -> convertor.convert(currencyEur, currencyCzk, testAmount)).
                withCause(exception);
    }

}
