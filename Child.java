/**
 * Child class extending Person to represent a child
 * @author Aditya Rana
 * @version 1.0
 */
public class Child extends Person{
    /**
     * String field representing Child's school
     */
    private String school;

    /**
     * double value representing the amount of tuition paid for school
     */
    private float tuition;

    /**
     * Child constructor initializing fields
     * @param name String value for Child's name
     * @param birthday String value for Child's birthday in format YYYY/MM/DD
     * @param ssn String representing SSN number in the format xxx-xx-xxxx
     * @param grossIncome float value for the gross income earned by the Child
     * @param school String variable for the Child's school
     * @param tuition float variable representing the amount of tuition paid
     */
    public Child(String name, String birthday, String ssn, float grossIncome, String school, float tuition) {
        super();
        setName(name);
        setBirthday(birthday);
        setSSN(ssn);
        setGrossIncome(grossIncome);
        this.school = school;
        this.tuition = tuition;
    }

    /**
     * toString() method overriding the parent method to print name, masked SSN, masked birthday, and school
     * @return String value for output
     */
    @Override
    public String toString() {
        String result = super.toString() + " " + school;
        return result;
    }

    /**
     * Getter method for tuition
     * @return float value for tuition paid for Child
     */
    public float getTuition() {
        return this.tuition;
    }

    /**
     * Method overriding parent method for deduction
     * @return float value for gross income that is exempt from taxation
     */
    @Override
    public float deduction(Family fam) {
        float result = Taxation.getChildBaseExemption();

        if (fam.getNumChildren() > 2) {
            if (((fam.getNumChildren() - 2) * (0.05)) > 0.5) {
                result -= 0.5 * result;
            }
            else {result -= ((fam.getNumChildren() - 2) * (0.05)) * result;}
        }

        if (result > this.getGrossIncome()) {
            return this.getGrossIncome();
        }
        else {
            return result;
        }
    }
}
