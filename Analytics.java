/**
 * Class for the analytics of tax returns
 * @author Aditya Rana
 * @version 1.0
 */
public class Analytics {
    /**
     * Float field for the poverty threshold
     */
    private static float povertyThreshold = 27750.0f;

    /**
     * TaxYear object for the year
     */
    private TaxYear year;

    /**
     * Constructor which runs statistics on a certain tax year
     * @param year TaxYear object for the year
     */
    public Analytics(TaxYear year) {
        this.year = year;
    }

    /**
     * Method to set the poverty threshold
     * @param threshold float value for the threshold
     */
    public void setPovertyThreshold(float threshold) {
        this.povertyThreshold = threshold;
    }
    
    /**
     * Method to return the average family income
     * @return float value for the average family income
     */
    public float averageFamilyIncome() {
        int num = year.numberOfReturnsFiled();
        float result = 0.0f;
        
        for (int i = 0; i < num; i++) {
            result += year.getTaxReturn(i).getTaxableIncome();
        }

        result = result/num;

        return result;
    }

    /**
     * Method to return the average person's income for the tax year
     * @return float value for the average income for a person
     */
    public float averagePersonIncome() {
        int num = year.numberOfReturnsFiled();
        int people = 0;
        float result = 0.0f;

        for (int i = 0; i < num; i++) {
            people += year.getTaxReturn(i).getNumAdults() + year.getTaxReturn(i).getNumChildren();
            result += year.getTaxReturn(i).getTaxableIncome();
        }

        result = result/people;

        return result;
    }

    /**
     * Method to return the max Family income
     * @return float value for highest family income
     */
    public float maxFamilyIncome() {
        int num = year.numberOfReturnsFiled();
        float max = year.getTaxReturn(0).getTaxableIncome();

        for (int i = 1; i < num; i++) {
            if (year.getTaxReturn(i).getTaxableIncome() > max) {
                max = year.getTaxReturn(i).getTaxableIncome();
            }
        }

        return max;
    }

    /**
     * Method to return max personal income
     * @return float value for highest personal income
     */
    public float maxPersonIncome() {
        int num = year.numberOfReturnsFiled();
        float max = year.getTaxReturn(0).getMaxPersonIncome();

        for (int i = 0; i < num; i++) {
            if (year.getTaxReturn(i).getMaxPersonIncome() > max) {
                max = year.getTaxReturn(i).getMaxPersonIncome();
            }
        }

        return max;
    }

    /**
     * Method to return the number of families below poverty limit
     */
    public int familiesBelowPovertyLimit() {
        int num = year.numberOfReturnsFiled();

        int counter = 0;

        for (int i = 0; i < num; i++) {
            if (year.getTaxReturn(i).getTaxableIncome() < povertyThreshold) {
                counter++;
            }
        }

        return counter;
    }

    /**
     * Method to return a family's rank based on their income
     * @return int value for the rank
     */
    public int familyRank(Family fam) {
        int rank = 1;

        int num = year.numberOfReturnsFiled();

        for (int i = 0; i < num; i++) {
            if (fam.getTaxableIncome() < year.getTaxReturn(i).getTaxableIncome()) {
                rank++;
            }
        }

        return rank;
    }

}
