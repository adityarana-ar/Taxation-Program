/**
 * Child class extending Person to represent an adult
 * @author Aditya Rana
 * @version 1.0
 */
public class Adult extends Person{
    /**
     * String value representing employer
     */
    private String employer;

    /**
     * Constructor initializing the Adult object
     * @param name Adult's name
     * @param birthday Adult's birthday
     * @param ssn Adult's ssn number
     * @param grossIncome Adult's gross income
     * @param employer Adult's employer
     */
    public Adult(String name, String birthday, String ssn, float grossIncome, String employer) {
        super();
        setName(name);
        setBirthday(birthday);
        setSSN(ssn);
        setGrossIncome(grossIncome);
        this.employer = employer;
    }

    /**
     * Method that overrides the parent toString method
     * @return String output value
     */
    @Override
    public String toString() {
        String result = super.toString() + " $" + String.format("%.2f", this.getGrossIncome());
        return result;
    }

    /**
     * Method returning adjusted income of employed Adult
     * @return float value for the income adjusted after
     * social security and medicare taxes
     */
    public float adjustedIncome() { //FIX ME (LOGIC)
        float result = 0;

        if (this.getGrossIncome() < Taxation.getSocialSecurityIncomeLimit()) {
            result = (this.getGrossIncome() - (this.getGrossIncome() * Taxation.getSocialSecurityRate()/200)
            - (this.getGrossIncome() * Taxation.getMedicareRate()/200));
        }
        else {
            result = (this.getGrossIncome() - (Taxation.getSocialSecurityIncomeLimit() * Taxation.getSocialSecurityRate()/200)
            - (this.getGrossIncome() * Taxation.getMedicareRate()/200));
        }

        return result;
    }

    /**
     * Method returning the amount of tax withheld
     * @return float value for the amount of total tax withheld
     */
    public float taxWithheld() {
        float result = 0;
        float income = getGrossIncome();

        if (income >= 50000.0f) {
            result += (0.1 * 50000.0f);
            income -= (float) 50000.0;
            // System.out.println(income);
        }
        else {
            result += (0.1f * income);
            income -= (float) 50000.0;
        }
        if (income >= 100000.0f) {
            result += ((float) 0.15 * 100000.0f);
            income -= (float) 100000.0;
            // System.out.println(income);
        }
        else if (income > 0) {
            result += ((float) 0.15 * income);
            income -= (float) 100000.0;
            // System.out.println(income);
        }
        if (income > 0) {
            result += ((float)0.2 * income);
            // System.out.println(income);
        }

        return result;
    }

    /**
     * Method overriding parent method deduction
     * @param fam type Family
     * @return float value representing the adjusted income exempt from taxation
     */
    @Override
    public float deduction(Family fam) {
        float result = Taxation.getAdultBaseExemption();


        if (adjustedIncome() > 100000.0f) {
            int extra =  (((int)(adjustedIncome() - 100000)) / 1000);
            if ((0.5 * (float) extra) > 30) {
                result -= result * (30.0f/100);
            }
            else {result -= result * ((0.5/100) * (float) extra);}
        }

        if (result > adjustedIncome()) {
            return adjustedIncome();
        }

        if ((fam.getFilingStatus() == 1) && (fam.getNumChildren() >= 1)) {
            result *= 2;
        }

        return result;
    }

    /**
     * Getter method for employer
     * @return String for adult's employer
     */
    public String getEmployer() {
        return employer;
    }

}
