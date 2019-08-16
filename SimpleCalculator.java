import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;





// CURRENT TODO:
// ALTER actionPerformedFindResult SO IT PARSES THE calculationHolder STRING AND CALCULATES FROM THAT
// OTHER STRING VARIABLES MAY BE NEEDED AS INTERMEDIARIES
// TODO: USER CHOICE OF WHETHER TO USE VALUE OF LAST CALCULATION






public class SimpleCalculator extends JFrame implements ActionListener
{
   private static final int WIDTH = 300;
   private static final int HEIGHT = 400;
   
   private JLabel outputWindow; // JLabel showing the results of calculations, created here so it can be accessed
   // by the SimpleCalculatorActionPerformedMethods inner class
   
   private String tempHolder = "";
   private Double valueOne = null; // "left side" of the calculation, as well as the result of a completed calculation
   private String operator = ""; // operator used
   private String lastOperator = ""; // last operator used in calculation
   private Double valueTwo = null; // "right side" of the calculation
   private double currentValOfCalculation = 0; // current and then eventually final value of equals
   private String outputText = ""; // for saving and adjusting the outputWindow JLabel as user action is performed
   private ArrayList<String> calculationHolder = new ArrayList<String>();
   private String lastVal = "";
   
   // actionMethods is an instance of the inner class used to hold the methods needed for the actionPerformed method
   private SimpleCalculatorActionPerformedMethods actionMethods = new SimpleCalculatorActionPerformedMethods();

   
   public static void main(String[] args)
   {
      String[] testString = {"hey", "there", null, null};
      SimpleCalculator currentRun = new SimpleCalculator();
   }
   
   public SimpleCalculator() {

      // calcWindow is the outer JFrame for the calculator
      JFrame calcWindow = new JFrame();
      calcWindow.setSize(WIDTH, HEIGHT);
      calcWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      calcWindow.setLayout(new BorderLayout());
      
      // outputWindow is the top of the calculator that shows the results of user input
      outputWindow = new JLabel(Double.toString(currentValOfCalculation), SwingConstants.CENTER);
      outputWindow.setFont(new Font("Label.font", Font.BOLD, 18));
      outputWindow.setPreferredSize(new Dimension(WIDTH, 80)); 
      

      
      // the bottom section of the calculator, holds all the buttons in a grid for the user to interact with
      JPanel buttonPanel = new JPanel();
      buttonPanel.setLayout(new GridLayout(4, 4));
      JButton one = new JButton("1");
      JButton two = new JButton("2");
      JButton three = new JButton("3");
      JButton divide = new JButton("/");
      JButton four = new JButton("4");
      JButton five = new JButton("5");
      JButton six = new JButton("6");
      JButton multiply = new JButton("*");
      JButton seven = new JButton("7");
      JButton eight = new JButton("9");
      JButton nine = new JButton("9");
      JButton subtract = new JButton("-");
      JButton clear = new JButton("C");
      JButton zero = new JButton("0");
      JButton equals = new JButton("=");
      JButton sum = new JButton("+");
      
      // attaches actionListeners to all the buttons, interacts with actionPerformed
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
      
      
      // adds the buttons to buttonPanel
      buttonPanel.add(one);
      buttonPanel.add(two);
      buttonPanel.add(three);
      buttonPanel.add(divide);
      buttonPanel.add(four);
      buttonPanel.add(five);
      buttonPanel.add(six);
      buttonPanel.add(multiply);
      buttonPanel.add(seven);
      buttonPanel.add(eight);
      buttonPanel.add(nine);
      buttonPanel.add(subtract);
      buttonPanel.add(clear);
      buttonPanel.add(zero);
      buttonPanel.add(equals);
      buttonPanel.add(sum);
      
      // outputWindow and buttonPanel are added to the main JFrame, outputWindow at the top and buttonPanel at the bottom
      calcWindow.add(outputWindow, BorderLayout.NORTH);
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
      if (e.getActionCommand() == "C") // clear command used, outputWindow text and all calculation variables reset
      {
         actionMethods.actionPerformedClear(e);
      }  
      else if (e.getActionCommand() == "=") // equals, performs the calculation, sets result to valueOne, shows result, resets other variables
      {
         actionMethods.actionPerformedFindResult();
      }
      else  // operator not yet used or clear has been activated, builds up the valueOne or valueTwo String
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
      private void actionPerformedClear(ActionEvent givenE)
      {
         valueOne = null;
         operator = "";
         valueTwo = null;
         outputText = "";
         outputWindow.setText("");
      }
      
      // if the user enters = (equals), the calculation is performed and the output is updated with its results,
      // valueOne is given the result if further calculation on the result is needed, and the other calculation variables are set to blank
      // !!: CURRENTLY NON FUNCTIONING AS A RESULT OF CHANGE IN SYSTEM FOR HOLDING DATA
      // !!: UPDATE TO WORK WITH THIS NEW SYSTEM, ALSO UPDATE TO WORK WITH MULTIPLE OPERATIONS IN ONE CALCULATION
      // !!: VALUES ALWAYS SAVING BETWEEN OPERATIONS REGARDLESS OF USER ACTION
      public void actionPerformedFindResult()
      {
         ArrayList<String> valueStack = new ArrayList<String>();
         ArrayList<String> operatorStack = new ArrayList<String>();

         if (tempHolder != "") { // if there is a value in the tempHolder buffer, add to calculationHolder ArrayList
            calculationHolder.add(tempHolder); 
            tempHolder = "";
         }
         if (isGivenValOperator(calculationHolder.get(calculationHolder.size() - 1))) { // if there is extraneous operator at end of calculationHolder, remove
            calculationHolder.remove(calculationHolder.size() - 1);
         }

         int i;
         for (i = 0; i < (calculationHolder.size() - 1); i++) { // parse calculationHolder using shunting yard algorithm
            if (isStringNumber(calculationHolder.get(i))) { // if index i of calculationHolder is number, push onto valueStack
               valueStack.add(calculationHolder.get(i));
            }
            else if (isGivenValOperator(calculationHolder.get(i))) {
               
            }
         }
      }



      
      // called if the user enters one of the numbers or operators
      // adds to the ArrayList calculationHolder either a full number (ie 5555) or an operator used,
      // disallowing repetition of operators
      // !!: PREVENT USE OF OPERATORS AT START OF CALCULATION
      public void actionPerformedBuildValues (ActionEvent givenE)
      {
         if (!(isGivenValOperator(givenE.getActionCommand()))) { // enters if user does not enter operator
            tempHolder = tempHolder + givenE.getActionCommand(); // builds up the tempHolder String with given numbers that will eventually be added to the array
            lastVal = givenE.getActionCommand();
            outputText = outputText + givenE.getActionCommand();
            outputWindow.setText(outputText);
         }
         else if (!(isGivenValOperator(lastVal)) &&                  // enters when operator is used and previous value is not an operator
                  (isGivenValOperator(givenE.getActionCommand()))) { // adds full tempHolder number String to array, then adds operator, resets tempHolder 
            calculationHolder.add(tempHolder);                       // in preparation for next number to be added
            calculationHolder.add(givenE.getActionCommand());
            lastVal = givenE.getActionCommand();
            tempHolder = "";
            outputText = outputText + givenE.getActionCommand();
            outputWindow.setText(outputText);
            System.out.println(tempHolder);
         }
         
         System.out.println(calculationHolder);
         
      }
   }
   // end of actionPerformed method and related functions

   // START OF MISC UTILITY FUNCTIONS

   public boolean isGivenValOperator (String val) {
      return ((val == "+") ||
              (val == "-") ||
              (val == "*") ||
              (val == "/"));
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

}  