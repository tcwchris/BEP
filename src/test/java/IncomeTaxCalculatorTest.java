import org.junit.Before;
import org.junit.Test;

import java.time.Instant;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class IncomeTaxCalculatorTest {

    private IncomeTaxCalculator taxCalculator;

    @Before
    public void setUp() {
        taxCalculator = new IncomeTaxCalculator();
    }

    @Test
    public void returnZeroAsThereIsNoTaxPay() {
        double result = taxCalculator.calculateTax(0.0, Instant.now().toEpochMilli());
        assertThat(result, is(equalTo(0.0)));
    }

    @Test
    public void calculateTaxWhenAowEligibleDisc1() {
        double result = taxCalculator.calculateTax(20000.0, -725068800);
        assertThat(result, is(equalTo(3730.0)));
    }

    @Test
    public void calculateTaxWhenAowEligibleDisc2()  {
        double result = taxCalculator.calculateTax(33000.0, -725068800);
        assertThat(result, is(equalTo(6707.394)));
    }

    @Test
    public void calculateTaxWhenAowEligibleDisc3() {
        double result = taxCalculator.calculateTax(123000.0, -725068800);
        assertThat(result, is(equalTo(39615.66)));
    }

    @Test
    public void calculateTaxWhenAowEligibleDisc4() {
        double result = taxCalculator.calculateTax(200000.0, -725068800);
        assertThat(result, is(equalTo(79611.277)));
    }

    @Test
    public void calculateTaxWhenNotAowEligibleDisc1() {
        double result = taxCalculator.calculateTax(20000.0, 1524130053);
        assertThat(result, is(equalTo(7310.0)));
    }

    @Test
    public void calculateTaxWhenNotAowEligibleDisc2() {
        double result = taxCalculator.calculateTax(33000.0, 1524130053);
        assertThat(result, is(equalTo(12614.394)));
    }

    @Test
    public void calculateTaxWhenNotAowEligibleDisc3() {
        double result = taxCalculator.calculateTax(110000.0, 1524130053);
        assertThat(result, is(equalTo(44068.894)));
    }

    @Test
    public void calculateTaxWhenNotAowEligibleDisc4() {
        double result = taxCalculator.calculateTax(200000.0, 1524130053);
        assertThat(result, is(equalTo(89420.521)));
    }

    @Test
    public void isAowEligibleBornBefore1948() {
        assertThat(taxCalculator.isAowEligible(-725068800), is(true));
    }

    @Test
    public void isAowEligibleBornBetween1948And1949() {
        assertThat(taxCalculator.isAowEligible(-679190400), is(true));
    }

    @Test
    public void isAowEligibleBornBetween1949And1950() {
        assertThat(taxCalculator.isAowEligible(-650332800), is(true));
    }

    @Test
    public void isAowEligibleBornBetween1950And1951() {
        assertThat(taxCalculator.isAowEligible(-781833600), is(true));
    }

    @Test
    public void isNotAowEligible() {
        assertThat(taxCalculator.isAowEligible(1524130053), is(false));
    }
}