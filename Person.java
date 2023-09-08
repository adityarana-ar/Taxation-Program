/**
 * Parent class to represent a person for taxation purposes.
 * @author Aditya Rana
 * @version 1.0
 */
public class Person {
    /**
     * Unique natural number of type int.
     */
    private int id;

    /**
     * Counter to keep track of id
     */
    private static int id_counter = 1;

    /**
     * String type variable to store the name of person.
     */
    private String name;

    /**
     * String variable to keep track of person's birthdate.
     * Format: YYYY/MM/DD
     */
    private String birthday;

    /**
     * Social Security Number (xxx-xx-xxxx)
     */
    private String ssn;

    /**
     * Non-negative Gross Income of the person with 2 decimal places
     */
    private float grossIncome;
    
    /**
     * Constructor initializing the id variable.
     */
    public Person() {
        this.id = id_counter++;
    }

    /**
     * Method to set the Name and return a boolean value based on
     * if it is valid or not
     * @param name name of the person
     * @return a boolean value based on whether input is valid or not
     */
    public boolean setName(String name) {
        for (int i = 0; i < name.length(); i++) {
            if ((!Character.isLetter(name.charAt(i))) && (name.charAt(i) != ' ')) {
                return false;
            }
        }

        this.name = name;
        return true;
    }

    /**
     * Method to set person's birthday
     * @param birthday String variable of the person's birthday
     * in YYYY/MM/DD format
     * @return a boolean value based on whether it is valid or not
     */
    public boolean setBirthday(String birthday) {
        if (birthday.length() != 10) {
            return false;
        }
        for (int i = 0; i < birthday.length(); i++) {
            if (((i == 4) || (i == 7)) && (birthday.charAt(i) == '/')){
                continue;
            }
            else if (Character.isDigit(birthday.charAt(i))) {
                continue;
            }
            else {
                return false;
            }
        }

        this.birthday = birthday;
        return true;
    }

    /**
     * Method to set person's SSN number
     * @param ssn the SSN number as a string
     * @return boolean value based on whether the input is valid or not
     */
    public boolean setSSN(String ssn) {
        if (ssn.length() != 11) {
            return false;
        }

        for (int i = 0; i < ssn.length(); i++) {
            if ((i == 3) || (i == 6)) {
                if (ssn.charAt(i) == '-') {
                    continue;
                }
                else {
                    return false;
                }
            }
            else {
                if (Character.isDigit(ssn.charAt(i))) {
                    continue;
                }
                else {
                    return false;
                }
            }
        }

        this.ssn = ssn;
        return true;
    }

    /**
     * Method to set the person's Gross Income
     * @param income a float value representing gross income
     * @return boolean value based on whether input is valid or not
     */
    public boolean setGrossIncome(float income) {
        if (income < 0.0) {
            this.grossIncome = 0;
            return false;
        }

        this.grossIncome = income;
        return true;
    }

    /**
     * Gets grossIncome for person
     * @return grossIncome
     */
    public float getGrossIncome() {
        return grossIncome;
    }

    /**
     * Gets person's ID
     * @return person's id
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the person's name followed by masked SSN
     * and birthday in special format
     * @return String in special format
     */
    public String toString() {
        String result = name + " " + maskSSN(ssn) + " " + maskBirthday(birthday);
        return result;
    }

    /**
     * Helper method to mask birthday
     * @param birthday
     * @return String for masked birthdate 
     */
    public static String maskBirthday(String birthday) {
        String result = birthday.substring(0, 4) + "/**/**";
        return result;
    }

    /**
     * Helper method to mask SSN
     * @param ssn Person's SSN number
     * @return masked SSN
     */
    public static String maskSSN(String ssn) {
        String result = "xxx-xx-" + ssn.substring(7, 11);
        return result;
    }

    /**
     * Getter method for name
     * @return String field for name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Getter method for SSN
     * @return String field for SSN
     */
    public String getSSN() {
        return this.ssn;
    }

    /**
     * Empty placeholder method to be implemented by child classes
     */
    public float deduction(Family fam) {return (float) 0.0;}
}
