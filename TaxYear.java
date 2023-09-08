/**
 * Class for the taxation year
 * @author Aditya Rana
 * @version 1.0
 */
public class TaxYear {
    /**
     * Field for the maximum number of returns
     * that can be filed for the year
     */
    private int max;

    /**
     * Field to store an array of all families
     * that file taxes
     */
    private Family[] families;

    /**
     * Static field to store current index
     */
    private static int index = 0;

    /**
     * Constructor for the Tax Year object
     * @param max int value for the maximum amount
     * of returns that can be filed for the year
     */
    public TaxYear(int max) {
        this.max = max;
        this.families = new Family[this.max];
    }

    /**
     * Method to file a family's tax return for the year
     * @param fam Object of type Family
     * @return boolean type which is false if there is an error
     * otherwise its true and works as intended.
     */
    public boolean taxFiling(Family fam) {
        // Files tax (FIX_ME)
        if ((fam.getNumAdults() == 0)||(fam.getTaxableIncome() < 0)) {
            return false;
        }
        else if ((fam.getFilingStatus() == 2) && (fam.getNumAdults() <= 1)) {
            return false;
        }
        else if ((fam.getNumAdults() > 1) && ((fam.getFilingStatus() == 1) ||(fam.getFilingStatus() == 3))) {
            return false;
        }
        else if (fam.getNumAdults() > 2) {
            return false;
        }
        if (families.length == 1) {
            families[0] = fam;
        }
        else {
            families[index] = fam;
            index++;
        }
        return true;
    }

    /**
     * Method to return total tax withheld by all families
     * till the point of time when the method is called
     * @return float value representing total tax withheld
     */
    public float taxWithheld() {
        float result = 0.0f;

        for (int i = 0; i < families.length; i++) {
            if (families[i] != null) {
                result += families[i].getTaxWithheld();
            }
        }

        return result;
    }

    /**
     * Method returning total tax owed by families
     * @return float value for total tax owed by families
     */
    public float taxOwed() {
        float result = 0.0f;

        for (Family fam : families) {
            if (fam != null) {
                result += fam.preCreditTax();
            }
        }
        return result;
    }

    /**
     * Method to return the total tax due for the tax year
     * @return float value for the total tax due
     */
    public float taxDue() {
        float tax = 0.0f;

        for (Family fam : families) {
            if (fam != null) {
                tax += fam.calculateTax();
            }
        }
        return tax;
    }

    /**
     * Method returning all tax credits given to families in the year
     * @return float value for total tax credits given to all families
     */
    public float taxCredits() {
        float result = 0.0f;

        for (int i = 0; i < families.length; i++) {
            if (families[i] != null) {
                result += families[i].taxCredit();
            }
        }

        return result;
    }

    /**
     * Method returning number of tax returns filed in the financial year
     * @return int value for the number of tax returns filed
     */
    public int numberOfReturnsFiled() {
        int counter = 0;

        for (int i = 0; i < families.length; i++) {
            if (families[i] != null) {
                counter++;
            }
        }

        return counter;
    }

    /**
     * Method to return the number of people included in the tax returns filed
     * @return int value for the number of people in tax returns
     */
    public int numberOfPersonsFiled() {
        int result = 0;

        for (int i = 0; i < families.length; i++) {
            if (families[i] != null) {
                result += families[i].getNumAdults() + families[i].getNumChildren();
            }
        }

        return result;
    }

    /**
     * Method to return the family at an index
     * @param index index value for the family to be returned
     * @return Family object stored at index in families
     */
    public Family getTaxReturn(int index) {
        if (families[index] != null) {
            return families[index];
        }
        else {
            return null;
        }
    }
}
