import java.util.ArrayList;

public class CalcMethodsTesting {
    
    
    

    public static void main(String[] args) {
        
        actionPerformedFindResult();
    }
    

    public static void actionPerformedFindResult()
    {
        String tempHolder = "";
        ArrayList<String> calculationHolder = new ArrayList<String>();
        ArrayList<String> valueStack = new ArrayList<String>();
        ArrayList<String> operatorStack = new ArrayList<String>();
        calculationHolder.add("22");
        calculationHolder.add("33");
        calculationHolder.add("44");


        if (tempHolder != "") { // if there is a value in the tempHolder buffer, add to calculationHolder ArrayList
            calculationHolder.add(tempHolder); 
            tempHolder = "";
        }
        if (isGivenValOperator(calculationHolder.get(calculationHolder.size() - 1))) { // if there is extraneous operator at end of calculationHolder, remove
            calculationHolder.remove(calculationHolder.size() - 1);
        }

        int i;
        for (i = 0; i < calculationHolder.size(); i++) { // parse calculationHolder using shunting yard algorithm
            if (isStringNumber(calculationHolder.get(i))) { // if number, push onto valueStack
                valueStack.add(calculationHolder.get(i));
            }
            else if (isGivenValOperator(calculationHolder.get(i))) {
             
            }
        }

        System.out.println(calculationHolder);
        System.out.println(valueStack);
    }

    public static boolean isGivenValOperator (String val) {
        return ((val == "+") ||
                (val == "-") ||
                (val == "*") ||
                (val == "/"));
    }

    public static boolean isStringNumber(String stringToCheck) {
        try {
            Double.parseDouble(stringToCheck);
            return true;
        }
        catch (NumberFormatException e) {
            return false;
        }
    }
}
