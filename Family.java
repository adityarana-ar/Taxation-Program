/**
 * Child class to Person class representing a Family of a Person
 * @author Aditya Rana
 * @version 1.0
 */
public class Family{
    /**
     * byte value representing the number of members in a family no greater than 50
     */
    private byte numMembers;

    /**
     * byte value representing the filing status for person
     * 1 - single
     * 2 - married filing jointly
     * 3 - married filing separately
     */
    private byte filingStatus;

    /**
     * Array of family member
     */
    private Person[] members;

    /**
     * Constructor for Family object
     * @param numMembers Array of Person[]
     * @param filingStatus byte value representing filing status
     */
    public Family(byte numMembers, byte filingStatus) {
        if ((numMembers < 51) && (numMembers > 0)) {
            this.numMembers = numMembers;
            this.members = new Person[numMembers];
        }
        else {
            this.numMembers = 0;
        }
        if ((filingStatus > 0) && (filingStatus < 4)) {
            this.filingStatus = filingStatus;
        }
    }

    /**
     * Method to add a member to the array
     * @param somePerson Person type variable for a person
     */
    public void addMember(Person somePerson) {
        // Person[] result = new Person[members.length + 1];

        // for (int i = 0; i < members.length; i++) {
        //     result[i] = members[i];
        // }

        // result[members.length] = somePerson;

        // this.members = result;

        for (int i = 0; i < members.length; i++) {
            if (members[i] == null) {
                members[i] = somePerson;
                break;
            }
        }
    }

    /**
     * Getter method to return the number of adults
     * @return byte value for the number of adults
     */
    public byte getNumAdults() {
        byte result = 0;

        for (int i = 0; i < members.length; i++) {
            if (members[i] instanceof Adult) {
                result++;
            }
        }

        return result;
    }

    /**
     * Getter method to return the number of children
     * @return byte value for the number of children
     */
    public byte getNumChildren() {
        byte result = 0;

        for (int i = 0; i < members.length; i++) {
            if (members[i] instanceof Child) {
                result++;
            }
        }

        return result;
    }

    /**
     * Getter method for the filing status
     * @return byte value for filing status
     */
    public byte getFilingStatus() {
        return this.filingStatus;
    }

    /**
     * Getter method for taxable income of family
     * of all adults plus income of all children minus the respective deductions
     * @return float value for total taxable income
     */
    public float getTaxableIncome() {
        float income = 0;
        float deductions = 0;

        for (int i = 0; i < members.length; i++) {
            if (members[i] instanceof Adult) {
                income += ((Adult)members[i]).adjustedIncome();
                deductions += members[i].deduction(this);
            }
            else {
                income += members[i].getGrossIncome();
                deductions += members[i].deduction(this);
            }
        }

        float result = income - deductions;

        return result;
    }

    /**
     * Helper method to return Person[] subset of members of only children
     * @return Child[] of only children
     */
    private Child[] getChildren() {
        Child[] children = new Child[getNumChildren()];
        int counter = 0;

        for (int i = 0; i < members.length; i++) {
            if (members[i] instanceof Child) {
                children[counter] = (Child) members[i];
                counter++;
            }
        }

        return children;
    }

    /**
     * Method returning the amount of tax credit that a family is eligible to receive
     * @return float value for the tax credit amount
     */
    public float taxCredit() {
        float result = 0;

        if (getTaxableIncome() > (Taxation.getMedianIncomePerCapita() * 0.5f)) {
            return 0.0f;
        }
        else {
            int extra = (int) getTaxableIncome() / 1000;
            result += (float) extra * 30;
            if (getNumChildren() > 0) {
                Child[] children = getChildren();
                for (int i = 0; i < getNumChildren(); i++) {
                    if (children[i].getTuition() < 1000) {
                        result += children[i].getTuition();
                    }
                    else {
                        result += 1000;
                    }
                }
            }

            if ((result > 2000.0f) || (result > preCreditTax())) {
                if (preCreditTax() < 2000.0f) {
                    result = preCreditTax();
                }
                else {
                    result = 2000.0f;
                }
            }

            if (filingStatus == 3) {
                result /= 2;
            }

            return result;
        }
    }
    
    /**
     * Getter method returning taxWithheld by all members of a family
     * @return float value for the total tax withheld by family members
     */
    public float getTaxWithheld() {
        float taxWithheld = 0.0f;

        for (int i = 0; i < members.length; i++) {
            if (members[i] instanceof Adult) {
                taxWithheld += ((Adult) members[i]).taxWithheld();
            }
        }

        return taxWithheld;
    }
    /**
     * Method calculating the amount of tax owed to a family or to be refunded
     * @return float value for the amount owed to a family or to be refunded
     */
    public float calculateTax() {
        float tax = preCreditTax();

        tax -= taxCredit() + getTaxWithheld();

        return tax;
    }

    /**
     * Method for calculating pre-credit Tax
     * @return float value for pre-credit tax
     */
    public float preCreditTax() {
        float tax = 0.0f;
        // Need Family class and its bracketIncome(Family, int b) to calculate the tax bracket

        int maxBracket = Taxation.maxIncomeTaxBracket(this);

        for (byte i = 1; i <= maxBracket; i++) {
            // System.out.println("Tax falling under bracket " + i + " is " + Taxation.bracketIncome(this, i));
            // System.out.println("Bracket rate: " + Taxation.bracketTaxRate(i, filingStatus));
            tax += Taxation.bracketIncome(this, i) * Taxation.bracketTaxRate(i, filingStatus);
        }

        return tax;
    }

    /**
     * Helper method to calculate the highest personal income
     * @return float value for the highest personal income
     */
    public float getMaxPersonIncome() {
        float max;
        if (members[0] instanceof Adult) {
            max = ((Adult)members[0]).adjustedIncome() - members[0].deduction(this);
        }
        else {
            max = members[0].getGrossIncome() - members[0].deduction(this);
        }
        for (int i = 0; i < members.length; i++) {
            float income = 0.0f;
            if (members[i] instanceof Adult) {
                income += ((Adult)members[i]).adjustedIncome() - members[i].deduction(this);
            }
            else {
                income += members[i].getGrossIncome() - members[i].deduction(this);
            }

            if (max < income){
                max = income;
            }
        }

        return max;
    }
}
