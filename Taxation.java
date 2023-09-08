/**
 * Class representing taxation
 * @author Aditya Rana
 * @version 1.0
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
public class Taxation {
    /**
     * Float value representing the social security rate
     */
    private static float socialSecurityRate = (float) 12.4;

    /**
     * Float value representing the social security income limit
     */
    private static float socialSecurityIncomeLimit = (float) 137700.0;

    /**
     * Float value representing the medicare rate
     */
    private static float medicareRate = (float) 2.9;

    /**
     * Float value representing the adult base exemption
     */
    private static float adultBaseExemption = (float) 3000.0;

    /**
     * Float value representing the child base exemption
     */
    private static float childBaseExemption = (float) 2000.0;

    /**
     * Float value representing median income per capita
     */
    private static float medianIncomePerCapita = (float) 31099.0;

    /**
     * Getter method for social security rate
     * @return float value for social security rate
     */
    public static float getSocialSecurityRate() {
        return socialSecurityRate;
    }

    /**
     * Getter method for medicare rate
     * @return float value for medicare rate
     */
    public static float getMedicareRate() {
        return medicareRate;
    }

    /**
     * Getter method for social security income limit
     * @return float value for social security income limit
     */
    public static float getSocialSecurityIncomeLimit() {
        return socialSecurityIncomeLimit;
    }

    /**
     * Getter method for baseAdultExemption
     * @return float value for baseAdultExemption
     */
    public static float getAdultBaseExemption() {
        return adultBaseExemption;
    }

    /**
     * Getter method for baseChildExemption
     * @return float value for baseChildExemption
     */
    public static float getChildBaseExemption() {
        return childBaseExemption;
    }

    /**
     * Getter method for medianIncomePerCapita
     * @return float value for medianIncomePerCapita
     */
    public static float getMedianIncomePerCapita() {
        return medianIncomePerCapita;
    }

    /**
     * Method to load the field variables from a text file
     * @param filename String value for the pathname
     */
    public static void loadParameters(String filename) {
         try {
             Scanner sc = new Scanner(new File(filename));
             String str = "";
             while (sc.hasNextLine()) {
                 str = sc.next();
                 if (str.equals("medicareRate")) {
                     medicareRate = sc.nextFloat();
//                     System.out.println("medicareRate found: " + medicareRate);
                 }
                 else if (str.equals("medianIncomePerCapita")) {
                     medianIncomePerCapita = sc.nextFloat();
//                     System.out.println("medianIncomePerCapita found: " + medianIncomePerCapita);
                 }
                 else if (str.equals("socialSecurityIncomeLimit")) {
                     socialSecurityIncomeLimit = sc.nextFloat();
//                     System.out.println("socialSecurityLimit found: " + socialSecurityIncomeLimit);
                 }
                 else if (str.equals("adultBaseExemption")) {
                     adultBaseExemption = sc.nextFloat();
//                     System.out.println("adultBaseExemption found: " + adultBaseExemption);
                 }
                 else if (str.equals("childBaseExemption")) {
                     childBaseExemption = sc.nextFloat();
//                     System.out.println("childBaseExemption found: " + childBaseExemption);
                 }
                 else if (str.equals("socialSecurityRate")) {
                     socialSecurityRate = sc.nextFloat();
//                     System.out.println("socialSecurityRate found: " + socialSecurityIncomeLimit);
                 }
             }

             sc.close();
         }
         catch (FileNotFoundException e) {
         }
    }

    /**
     * 2D array to store info about income brackets
     */
    private static float[][] incomeBracket = {{10000.0f, 20000.0f, 12000.0f},
    {40000.0f, 70000.0f, 44000.0f},
    {80000.0f, 160000.0f, 88000.0f},
    {160000.0f, 310000.0f, 170000.0f}};

    /**
     * 2D array to query tax rates
     */
    private static float[][] taxRate = {{0.1f, 0.1f, 0.1f},
    {0.12f, 0.12f, 0.12f},
    {0.22f, 0.23f, 0.24f},
    {0.24f, 0.25f, 0.26f},
    {0.32f, 0.33f, 0.35f}};

    /**
     * Getter method to return the number of tax brackets
     * @return int value for the highest tax bracket
     */
    public static byte getNumTaxBrackets() {
        return (byte) (incomeBracket.length + 1);
    }

    /**
     * Method to return the highest tax bracket for a family's income
     * @param fam Object of type Family
     * @return int value for the highest tax bracket number
     */
    public static byte maxIncomeTaxBracket(Family fam) {
        float income = fam.getTaxableIncome();
        byte result = 0;

        for (int i = 0; i < incomeBracket.length; i++) {
            if (income > incomeBracket[i][fam.getFilingStatus() - 1]) {
                result++;
            }
        }

        return (byte) (result+1);
    }

    /**
     * Method to return portion of the family's income that falls
     * within bracket int b
     * @param fam Object of type Family
     * @param b byte variable for tax bracket number
     * @return float value for portion of income
     */
    public static float bracketIncome(Family fam, byte b) {
       /*  if (b >= 1) {
            float result = incomeBrackets[0][fam.getFilingStatus() - 1];
            return result;
        }
        else {
            float result = (incomeBrackets[b-1][fam.getFilingStatus() - 1]
            - incomeBrackets[b - 2][fam.getFilingStatus() - 1]);
            return result;
        } */



        float taxIncome = fam.getTaxableIncome();
        float income = 0;
        byte filingStatus = (byte) (fam.getFilingStatus() - ((byte) 1));

        if (b == 5) {
            return taxIncome - incomeBracket[3][filingStatus];
        }
        if (taxIncome > incomeBracket[b-1][filingStatus]) {
            if (b > 1) {
                income = incomeBracket[b - 1][filingStatus] - incomeBracket[b - 2][filingStatus];
            }
            else {
                income = incomeBracket[b - 1][filingStatus];
            }
        }
        else {
            if (b > 1) {
                income = taxIncome - incomeBracket[b-2][filingStatus];
            }
            else {
                income = taxIncome;
            }
        }
        return income;
    }

    /**
     * Method to return the tax rate corresponding to bracket b
     * and filing status f
     * @param b byte value for the tax bracket number
     * @param f byte value for the filing status
     */
    public static float bracketTaxRate(byte b, byte f) {
        return taxRate[b-1][f-1];
    }

    public static void printFile(String filename) throws FileNotFoundException {
        File f = new File(filename);
        Scanner sc = new Scanner(f);

        while (sc.hasNextLine()) {
            System.out.println(sc.nextLine());
        }
    }
    
}
