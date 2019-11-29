import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Stack;





public class SimpleCalculator extends JFrame implements ActionListener
{
   private static final int WIDTH = 300;
   private static final int HEIGHT = 400;
   
   private JScrollPane outputWindowScrollPane;
   private JTextArea outputWindow; // JLabel showing the results of calculations, created here so it can be accessed
   // by the SimpleCalculatorActionPerformedMethods inner class
   
   private String tempHolder = "";
   private Double valueOne = null; // "left side" of the calculation, as well as the result of a completed calculation
   private String operator = ""; // operator used
   private Double valueTwo = null; // "right side" of the calculation
   private String outputText = ""; // for saving and adjusting the outputWindow JLabel as user action is performed
   private ArrayList<String> calculationHolder = new ArrayList<String>(); // holds all the numbers and operators in a single calculation
   private Stack<String> valueStack = new Stack<String>();
   private Stack<String> operatorStack = new Stack<String>();
   private String lastVal = ""; // last value entered before calculation performed
   
   // actionMethods is an instance of the inner class used to hold the methods needed for the actionPerformed method
   private SimpleCalculatorActionPerformedMethods actionMethods = new SimpleCalculatorActionPerformedMethods();

   
   public static void main(String[] args)
   {
      new SimpleCalculator();
   }
   
   public SimpleCalculator() {


      // calcWindow is the outer JFrame for the calculator
      JFrame calcWindow = new JFrame();
      calcWindow.setSize(WIDTH, HEIGHT);
      calcWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      calcWindow.setLayout(new BorderLayout());
      
      
      // outputWindow is the top of the calculator that shows the results of user input
      outputWindow = new JTextArea(Double.toString(0.0));
      outputWindow.setFont(new Font("Label.font", Font.BOLD, 18));
      outputWindow.setEditable(false);
      outputWindow.setLineWrap(true);

      outputWindowScrollPane = new JScrollPane(outputWindow);
      outputWindowScrollPane.setPreferredSize(new Dimension(WIDTH, 80));
      outputWindowScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
      outputWindowScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
      

      
      // the bottom section of the calculator, holds all the buttons in a grid for the user to interact with
      JPanel buttonPanel = new JPanel();
      buttonPanel.setLayout(new GridLayout(6, 5));

      JButton sqRoot = new JButton("sqrt(x)");
      JButton square = new JButton("x^2");
      JButton one = new JButton("1");
      JButton two = new JButton("2");
      JButton three = new JButton("3");
      JButton divide = new JButton("/");
      JButton four = new JButton("4");
      JButton five = new JButton("5");
      JButton six = new JButton("6");
      JButton multiply = new JButton("*");
      JButton seven = new JButton("7");
      JButton eight = new JButton("8");
      JButton nine = new JButton("9");
      JButton subtract = new JButton("-");
      JButton clear = new JButton("C");
      JButton zero = new JButton("0");
      JButton equals = new JButton("=");
      JButton sum = new JButton("+");
      JButton decimal = new JButton(".");
      JButton switchSign = new JButton("+/-");
      JButton undo = new JButton("Back");
      JButton reciprocal = new JButton("1/x");
      JButton n2 = new JButton(" ");
      JButton n3 = new JButton(" ");
      
      // attaches actionListeners to all the buttons, interacts with actionPerformed
      sqRoot.addActionListener(this);
      square.addActionListener(this);
      one.addActionListener(this);
      two.addActionListener(this);
      three.addActionListener(this);
      divide.addActionListener(this);
      four.addActionListener(this);
      five.addActionListener(this);
      six.addActionListener(this);
      multiply.addActionListener(this);
      seven.addActionListener(this);
      eight.addActionListener(this);
      nine.addActionListener(this);
      subtract.addActionListener(this);
      clear.addActionListener(this);
      zero.addActionListener(this);
      equals.addActionListener(this);
      sum.addActionListener(this);
      decimal.addActionListener(this);
      switchSign.addActionListener(this);
      undo.addActionListener(this);
      reciprocal.addActionListener(this);
      n2.addActionListener(this);
      n3.addActionListener(this);
      
      
      // adds the buttons to buttonPanel
      buttonPanel.add(sqRoot);
      buttonPanel.add(square);
      buttonPanel.add(switchSign);
      buttonPanel.add(clear);
      buttonPanel.add(undo);
      buttonPanel.add(reciprocal);
      buttonPanel.add(n2);
      buttonPanel.add(n3);
      buttonPanel.add(seven);
      buttonPanel.add(eight);
      buttonPanel.add(nine);
      buttonPanel.add(divide);
      buttonPanel.add(four);
      buttonPanel.add(five);
      buttonPanel.add(six);
      buttonPanel.add(multiply);
      buttonPanel.add(one);
      buttonPanel.add(two);
      buttonPanel.add(three);
      buttonPanel.add(subtract);
      buttonPanel.add(decimal);
      buttonPanel.add(zero);
      buttonPanel.add(equals);
      buttonPanel.add(sum);
      
      // outputWindow and buttonPanel are added to the main JFrame, outputWindow at the top and buttonPanel at the bottom
      calcWindow.add(outputWindowScrollPane, BorderLayout.NORTH);
      calcWindow.add(buttonPanel, BorderLayout.CENTER);
      
      
      calcWindow.setVisible(true);
   }
   
   
   // This section is for listening to and responding to user input
   // Includes the function actionPerformed (which is the actionListener for this program)
   // Includes the private class SimpleCalculatorActionPerformedMethods which holds the methods used
   // in the actionPerformed method 
      
   // The actionListener for this program, responds to user input (press of the calculator buttons)
   public void actionPerformed(ActionEvent e)
   {
      if (e.getActionCommand().equals("C")) // clear command used, outputWindow text and all calculation variables reset
      {
         actionMethods.actionPerformedClear();
      }  
      else if (e.getActionCommand().equals("=") && (calculationHolder.size() != 0)) // equals, performs the calculation, shows results, saves result
      {
         actionMethods.actionPerformedFindResult();
      }
      else if (e.getActionCommand().equals(".")) { // adds decimal to number if not already inserted in number
         actionMethods.actionPerformedDecimal();
      }
      else if (e.getActionCommand().equals("+/-") && !(tempHolder.equals(""))) {
         actionMethods.actionPerformedChangeSign(e);
      }
      else if (((e.getActionCommand().equals("x^2")) || (e.getActionCommand().equals("sqrt(x)")) || (e.getActionCommand().equals("1/x"))) && // applies one of the given mathematicals if tempHolder not empty
                (tempHolder.length() != 0)) {
         actionMethods.actionPerformedFunctionApplied(e);
      }
      else if (e.getActionCommand().equals("Back")) {
         actionMethods.actionPerformedUndo(e);
      }
      else if (isGivenValOperator(e.getActionCommand()) || isStringNumber(e.getActionCommand())) // operator not yet used or clear has been activated, builds up the valueOne or valueTwo String
      {
         actionMethods.actionPerformedBuildValues(e);
      }
         
      
   }
   
   // A class that holds all the methods that are to be used in actionPerformed
   private class SimpleCalculatorActionPerformedMethods
   {
      // if action event recieves a command for clearing the calculator
      // then all the variables for holding information and the text output
      // are set to be blank
      private void actionPerformedClear()
      {
         valueOne = null;
         operator = "";
         valueTwo = null;
         outputText = "";
         lastVal = "";
         tempHolder = "";
         valueStack.clear();
         operatorStack.clear();
         calculationHolder.clear();
         outputWindow.setText("0.0");
      }
      
      // uses the shunting yard algorithm to calculation the results of a given series of values and operations given the ArrayList calculationHolder
      // result is then output to the calculation screen, saved as only value in valueStack
      public void actionPerformedFindResult()
      {
         String currentOperator = "";


         if (tempHolder != "") { // if there is a value in the tempHolder buffer, add to calculationHolder ArrayList
            calculationHolder.add(tempHolder); 
            tempHolder = "";
         }
         if (isGivenValOperator(calculationHolder.get(calculationHolder.size() - 1))) { // if there is extraneous operator at end of calculationHolder, remove
            calculationHolder.remove(calculationHolder.size() - 1);
         }

         int i;
         for (i = 0; i < calculationHolder.size(); i++) { // begin to parse calculationHolder using shunting yard algorithm

            if (isStringNumber(calculationHolder.get(i))) { // if number, push onto valueStack
               valueStack.push(calculationHolder.get(i));
            }
            else if (calculationHolder.get(i).equals("(")) { // if left parenthesis, push to top of operatorStack
               operatorStack.push("(");
            }
            else if (calculationHolder.get(i).equals(")")) { // if right parenthesis, evaluate items inside the two parenthesis
               while (operatorStack.get(operatorStack.size() - 1) != "(") {
                  operator = operatorStack.pop();
                  valueTwo = Double.parseDouble(valueStack.pop());
                  valueOne = Double.parseDouble(valueStack.pop());
                  applyOperator();
               }
               operatorStack.pop();
            }
            else if (isGivenValOperator(calculationHolder.get(i))) { // if token is operator, evaluate procedure
               currentOperator = calculationHolder.get(i);
               while ((operatorStack.size() != 0) &&
                      (checkPrecedence(operatorStack.peek(), currentOperator))) { // operates on high precedence operators
                  operator = operatorStack.pop();
                  valueTwo = Double.parseDouble(valueStack.pop()); 
                  valueOne = Double.parseDouble(valueStack.pop());
                  applyOperator();
               }
               operatorStack.push(currentOperator);
            }
         }

         while (operatorStack.size() != 0) { // while operatorStack still has items, operate on numbers (after higher precedence ops performed)
            operator = operatorStack.pop();
            valueTwo = Double.parseDouble(valueStack.pop());
            valueOne = Double.parseDouble(valueStack.pop());
            applyOperator();
         }
         
         // change output to user to calculation, make only value in calculationHolder, save to tempHolder, clear calculationHolder
         tempHolder = valueStack.peek();
         outputText = valueStack.peek();
         lastVal = "";
         outputWindow.setText(valueStack.peek());
         calculationHolder.clear();
         calculationHolder.add(valueStack.peek());
      }
      
      public void actionPerformedDecimal() { // adds decimal to current number if it does not already have a decimal inserted
         boolean decimalInNumber = false; 
         int i;
         for (i = 0; i < tempHolder.length(); i++) { // check if decimal in current number
            if (tempHolder.charAt(i) == '.') {
               decimalInNumber = true; // if true, decimal already in number, do not insert another
            }
         }
         if (decimalInNumber == false) { // if false, insert decimal, make adjustments to output
            tempHolder = tempHolder + ".";
            outputText = outputText + ".";
            outputWindow.setText(outputText);
         }
      }

      public void actionPerformedFunctionApplied(ActionEvent givenE) { // applies a mathematical function to tempHolder, updates
         if (givenE.getActionCommand().equals("x^2")) {                     // output to user to show result of that function, value
            outputText = outputText.substring(0, (outputText.length() - tempHolder.length())); // usable in further calculations
            tempHolder = Double.toString(Math.pow(Double.parseDouble(tempHolder), 2));
            outputText = outputText + tempHolder;
            outputWindow.setText(outputText);
         }
         else if (givenE.getActionCommand().equals("sqrt(x)")) { // apply square root function
            outputText = outputText.substring(0, (outputText.length() - tempHolder.length()));
            tempHolder = Double.toString(Math.pow(Double.parseDouble(tempHolder), 0.5));
            outputText = outputText + tempHolder;
            outputWindow.setText(outputText);
         }
         else if (givenE.getActionCommand().equals("1/x")) {
            int tempHolderOriginalSize = tempHolder.length();
            tempHolder = Double.toString(1 / Double.parseDouble(tempHolder));
            outputText = outputText.substring(0, outputText.length() - tempHolderOriginalSize) + tempHolder;
            outputWindow.setText(outputText);
         }
      }

      // when called, switches the sign of tempHolder (the current number being altered) by string manipulation
      public void actionPerformedChangeSign(ActionEvent givenE) { 
         if (tempHolder.charAt(0) == '-') { // if already negative, switch to positive
            outputText = outputText.substring(0, (outputText.length() - tempHolder.length()));
            tempHolder = tempHolder.substring(1); 
            outputText = outputText + tempHolder;
            outputWindow.setText(outputText);
         }
         else { // if positive, switch to negative
            outputText = outputText.substring(0, (outputText.length() - tempHolder.length()));
            tempHolder = "-" + tempHolder;
            outputText = outputText + tempHolder;
            outputWindow.setText(outputText);
         }
      }

      public void actionPerformedUndo(ActionEvent givenE) {
         System.out.println("begin");
         System.out.println("calculationHolder: " + calculationHolder);
         System.out.println("calculationHolder.size():" + calculationHolder.size());
         System.out.println("tempHolder: " + tempHolder);
         if (calculationHolder.size() != 0 && calculationHolder.get(calculationHolder.size() - 1).length() == 0) { // if next item in calculationHolder ArrayList is empty, remove that item before doing anything else
            calculationHolder.remove(calculationHolder.size() - 1);
         }

         if (calculationHolder.size() != 0 && tempHolder.equals("") // if next value to remove must be an operator, remove the operator
            && (calculationHolder.get(calculationHolder.size() - 1).length() != 0) 
            && lastVal != "undidOperator") { 
            System.out.println(1);
            lastVal = "undidOperator"; // set to generic number since operator was removed
            calculationHolder.remove(calculationHolder.size() - 1); // remove operator
            outputText = outputText.substring(0, outputText.length() - 1);
            outputWindow.setText(outputText);
            tempHolder = calculationHolder.get(calculationHolder.size() - 1); // set tempHolder to last number in calculationHolder, remove that number from calculationHolder
            calculationHolder.remove(calculationHolder.size() - 1);
         }
         else if (tempHolder.length() != 0) { // if there are values in tempHolder, remove values from tempHolder
            System.out.println(2);
            if (calculationHolder.size() != 0 && tempHolder.equals(calculationHolder.get(calculationHolder.size() - 1))) {
               calculationHolder.set((calculationHolder.size() - 1), calculationHolder.get(calculationHolder.size() - 1).substring(0, calculationHolder.get(calculationHolder.size() - 1).length() - 1));
            }
            tempHolder = tempHolder.substring(0, tempHolder.length() - 1);
            if (tempHolder.equals("-")) { // if last value is a negative modifier, clear tempHolder and remove from output text
               tempHolder = "";
               outputText = outputText.substring(0, outputText.length() - 2);
               outputWindow.setText(outputText);
            }
            else { // otherwise just set output text to itself minus the last value in the String
               outputText = outputText.substring(0, outputText.length() - 1); 
               outputWindow.setText(outputText);
            }
            
         }
         else if (calculationHolder.size() != 0 && tempHolder.equals("")) { // if last item in ArrayList calculationHolder is not empty, set that value to be itself minus the last value
            System.out.println(3);
            tempHolder = calculationHolder.get(calculationHolder.size() - 1);
            tempHolder = tempHolder.substring(0, tempHolder.length() - 1);
            calculationHolder.remove(calculationHolder.size() - 1);
            outputText = outputText.substring(0, outputText.length() - 1);
            outputWindow.setText(outputText);
         }

         System.out.println("calculationHolder: " + calculationHolder);
         System.out.println("calculationHolder.size():" + calculationHolder.size());
         System.out.println("tempHolder: " + tempHolder);
      }
      
      // called if the user enters one of the numbers or operators
      // adds to the ArrayList calculationHolder either a full number (ie 5555) or an operator used,
      // disallowing repetition of operators
      public void actionPerformedBuildValues (ActionEvent givenE)
      {
         if ((valueStack.size() != 0) &&                                // if result of last calculation still in memory, but next value is not operator
             (calculationHolder.size() == 1) &&                         // reset memory and start new calculation with new number
             (isGivenValOperator(givenE.getActionCommand()) != true)) {
            valueStack.clear();
            calculationHolder.clear();
            tempHolder = givenE.getActionCommand();
            lastVal = givenE.getActionCommand();
            outputText = givenE.getActionCommand();
            outputWindow.setText(outputText);
         }
         else if (!(isGivenValOperator(givenE.getActionCommand()))) { // enters if user does not enter operator
            tempHolder = tempHolder + givenE.getActionCommand(); // builds up the tempHolder String with given numbers that will eventually be added to the array
            lastVal = givenE.getActionCommand();
            outputText = outputText + givenE.getActionCommand();
            outputWindow.setText(outputText);
         }
         else if (!(isGivenValOperator(lastVal)) &&                  // if operator used, operator not last value used or first value in calculation,
                  (isGivenValOperator(givenE.getActionCommand())) && // then do this
                  ((tempHolder.length() != 0) || (calculationHolder.size() != 0))) {                                              
            calculationHolder.add(tempHolder);                       
            calculationHolder.add(givenE.getActionCommand());        // adds full tempHolder number String to calculationHolder arrayList, then adds operator, resets tempHolder 
            lastVal = givenE.getActionCommand();                     // in preparation for next number to be added, updates output to user
            tempHolder = "";
            outputText = outputText + givenE.getActionCommand();
            outputWindow.setText(outputText);
            System.out.println(tempHolder); // debug, tracking state
         }
         
         System.out.println(calculationHolder); // debug, tracking state
         
      }
   }
   // end of actionPerformed method and related functions

   // START OF MISC UTILITY FUNCTIONS

   public boolean isGivenValOperator (String val) {
      return ((val.equals("+")) ||
              (val.equals("-")) ||
              (val.equals("*")) ||
              (val.equals("/")) ||
              (val.equals("x^2")) ||
              (val.equals("sqrt(x)")));
   }

   // Checks is a given String can be converted into a number, returns true if so, false otherwise
   public boolean isStringNumber(String stringToCheck) {
      try {
         Double.parseDouble(stringToCheck);
         return true;
      }
      catch (NumberFormatException e) {
         return false;
      }
   }

   public void applyOperator() {
      if (operator.equals("+")) { // perform calculation, add calculated value to top of valueStack
         valueStack.push(Double.toString(valueOne + valueTwo));
      }
      else if (operator.equals("-")) {
         valueStack.push(Double.toString(valueOne - valueTwo));
      }
      else if (operator.equals("*")) {
         valueStack.push(Double.toString(valueOne * valueTwo));
      }
      else if (operator.equals("/")) {
         valueStack.push(Double.toString(valueOne / valueTwo));
      }
   }

   // compares the firstOperator and secondOperator precedence using standard mathematical order of operators
   // returns true if firstOperator has higher or the same precedence as secondOperator
   public boolean checkPrecedence(String firstOperator, String secondOperator) {
      int firstOpPrecedence = 0;
      int secondOpPrecedence = 0;

      if (firstOperator.equals("^")) {
         firstOpPrecedence = 4;
      }
      else if (firstOperator.equals("*")) {
         firstOpPrecedence = 3;
      }
      else if (firstOperator.equals("/")) {
         firstOpPrecedence = 3;
      }
      else if (firstOperator.equals("+")) {
         firstOpPrecedence = 2;
      }
      else if (firstOperator.equals("-")) {
         firstOpPrecedence = 2;
      }

      if (secondOperator.equals("^")) {
         secondOpPrecedence = 4;
      }
      else if (secondOperator.equals("*")) {
         secondOpPrecedence = 3;
      }
      else if (secondOperator.equals("/")) {
         secondOpPrecedence = 3;
      }
      else if (secondOperator.equals("+")) {
         secondOpPrecedence = 2;
      }
      else if (secondOperator.equals("-")) {
         secondOpPrecedence = 2;
      }

      if (firstOpPrecedence >= secondOpPrecedence) {
         return true;
      }
      return false;
   }
}

  