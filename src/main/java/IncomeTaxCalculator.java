public class IncomeTaxCalculator {
    private static final double TAXRATE_18PRECENT = .1865;
    private static final double TAXRATE_36PRECENT = .3655;
    private static final double TAXRATE_22PRECENT = .2295;
    private static final double TAXRATE_40PRECENT = .4085;
    private static final double TAXRATE_51PRECENT = .5195;

    private static final double DISC1_MAX_INCOME = 20142.0;
    private static final double DISC2_MAX_INCOME_WITHOUT_AOW = 33994.0;
    private static final double DISC2_MAX_INCOME_WITH_AOW = 34404.0;
    private static final double DISC3_MAX_INCOME = 68507.0;

    public double calculateTax(double taxableIncome, long birthday) {
        double totalTax = 0.0;
        boolean aowEligible = isAowEligible(birthday);

        if (taxableIncome > DISC1_MAX_INCOME) {
            totalTax += DISC1_MAX_INCOME * getTaxrate(1, aowEligible);
            taxableIncome -= DISC1_MAX_INCOME;
        } else {
            return taxableIncome * getTaxrate(1, aowEligible);
        }

        if (aowEligible && taxableIncome > DISC2_MAX_INCOME_WITH_AOW) {
            totalTax += DISC2_MAX_INCOME_WITH_AOW * getTaxrate(2, true);
            taxableIncome -= DISC2_MAX_INCOME_WITH_AOW;
        } else if (taxableIncome > DISC2_MAX_INCOME_WITHOUT_AOW) {
            totalTax += DISC2_MAX_INCOME_WITHOUT_AOW * getTaxrate(2, aowEligible);
            taxableIncome -= DISC2_MAX_INCOME_WITHOUT_AOW;
        } else {
            return totalTax + (taxableIncome * getTaxrate(2, aowEligible));
        }

        if (taxableIncome > DISC3_MAX_INCOME) {
            totalTax += DISC3_MAX_INCOME * getTaxrate(3, aowEligible);
            taxableIncome -= DISC3_MAX_INCOME;
        } else {
            return totalTax + (taxableIncome * getTaxrate(3, aowEligible));
        }

        return totalTax + (taxableIncome * getTaxrate(4, aowEligible));
    }

    // Package private for testing purpose
    boolean isAowEligible(long birthday) {
        return birthday < -694310400
            || (birthday > -694310400 && birthday < -665366400)
            || (birthday > -665366400 && birthday < -636422400)
            || (birthday > -636422400 && birthday < -607564800)
            || (birthday > -607564800 && birthday < -583977600);
    }

    private double getTaxrate(int disc, boolean aowEligible) {
        if (disc == 1 && aowEligible) return TAXRATE_18PRECENT;
        if (disc == 1) return TAXRATE_36PRECENT;

        if (disc == 2 && aowEligible) return TAXRATE_22PRECENT;
        if (disc == 2) return TAXRATE_40PRECENT;

        if (disc == 3) return TAXRATE_40PRECENT;
        return TAXRATE_51PRECENT;
    }
}
