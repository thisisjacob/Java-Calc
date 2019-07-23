import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class SimpleCalculator extends JFrame implements ActionListener
{
   private static final int WIDTH = 300;
   private static final int HEIGHT = 400;
   
   private JLabel outputWindow; // JLabel showing the results of calculations, created here so it can be accessed
   // by the SimpleCalculatorActionPerformedMethods inner class
   
   private String valueOne = ""; // "left side" of the calculation, as well as the result of a completed calculation
   private String operator = "";
   private String valueTwo = ""; // "right side" of the calculation
   private String outputText = ""; // for saving and adjusting the outputWindow JLabel as user action is performed
   
   // actionMethods is an instance of the inner class used to hold the methods needed for the actionPerformed method
   private SimpleCalculatorActionPerformedMethods actionMethods = new SimpleCalculatorActionPerformedMethods();

   
   public static void main(String[] args)
   {
      SimpleCalculator currentRun = new SimpleCalculator();
   }
   
   public SimpleCalculator()
   {

      // calcWindow is the outer JFrame for the calculator
      JFrame calcWindow = new JFrame();
      calcWindow.setSize(WIDTH, HEIGHT);
      calcWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      calcWindow.setLayout(new BorderLayout());
      
      // outputWindow is the top of the calculator that shows the results of user input
      outputWindow = new JLabel(valueOne, SwingConstants.CENTER);
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
      else if ((e.getActionCommand() == "/") || // records use of operator, stores what operator was chosen
               (e.getActionCommand() == "*") || // cannot have already used an operator in current calculation, cannot use before input is given for valueOne
               (e.getActionCommand() == "-") ||
               (e.getActionCommand() == "+"))
      {
         if ((valueTwo == "") && (operator == "") && (valueOne != "")) 
         {
            actionMethods.actionPerformedOperator(e);
         }
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
         valueOne = "";
         operator = "";
         valueTwo = "";
         outputText = "";
         outputWindow.setText("");
      }
      
      // if an operator (other than =) is entered, this records the operator and updates the output text to the user
      public void actionPerformedOperator (ActionEvent givenE)
      {
         operator = givenE.getActionCommand();
         outputText = outputText + "  " + givenE.getActionCommand() + "  ";
         outputWindow.setText(outputText);
      }
      
      // if the user enters = (equals), the calculation is performed and the output is updated with its results,
      // valueOne is given the result if further calculation on the result is needed, and the other calculation variables are set to blank
      public void actionPerformedFindResult()
      {
         if (operator == "+")
         {
            outputText = Double.toString(Double.parseDouble(valueOne) + Double.parseDouble(valueTwo));
         }
         else if (operator == "-")
         {
            outputText = Double.toString(Double.parseDouble(valueOne) - Double.parseDouble(valueTwo));
         }
         else if (operator == "*")
         {
            outputText = Double.toString(Double.parseDouble(valueOne) * Double.parseDouble(valueTwo));
         }
         else if (operator == "/")
         {
            if (Double.parseDouble(valueTwo) == 0)
            {
               outputText = "Division by 0";
            }
            else
            {
               outputText = Double.toString(Double.parseDouble(valueOne) / Double.parseDouble(valueTwo));
            }
         }
         valueOne = outputText;
         valueTwo = "";
         operator = "";
         outputWindow.setText(outputText);
      }
      
      
      // called if the user enters one of the numbers
      // if there is no operator entered yet, a String of the number is appended to the valueOne string
      // otherwise it is appended to the valueTwo string   
      public void actionPerformedBuildValues (ActionEvent givenE)
      {
         if (operator == "")
         {
            valueOne = valueOne + givenE.getActionCommand(); // remembers value of first behind the scenes
            outputText = outputText + givenE.getActionCommand();
            outputWindow.setText(outputText);
         }
         else
         {
            valueTwo = valueTwo + givenE.getActionCommand();
            outputText = outputText + givenE.getActionCommand();
            outputWindow.setText(outputText);
         }
      }
   }
   // end of actionPerformed method and related functions
}  