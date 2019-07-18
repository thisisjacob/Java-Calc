import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

// to do:
// improve appearance
// what to do if a second operator is used in one operation
public class SimpleCalculator extends JFrame implements ActionListener
{
   private static final int WIDTH = 300;
   private static final int HEIGHT = 300;
   
   private JLabel outputWindow;
   
   private String valueOne = "";
   private String operator = "";
   private String valueTwo = "";
   private String outputText = "";

   
   public static void main(String[] args)
   {
      SimpleCalculator calcWindow = new SimpleCalculator();
   
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
      outputWindow.setPreferredSize(new Dimension(WIDTH, 70)); 
      

      
      // the bottom section of the calculator, holds all the buttons in a grid for the user to interact with
      JPanel buttonPanel = new JPanel();
      buttonPanel.setLayout(new GridLayout(4, 4));
      JButton one = createButton("1");
      JButton two = createButton("2");
      JButton three = createButton("3");
      JButton divide = createButton("/");
      JButton four = createButton("4");
      JButton five = createButton("5");
      JButton six = createButton("6");
      JButton multiply = createButton("*");
      JButton seven = createButton("7");
      JButton eight = createButton("9");
      JButton nine = createButton("9");
      JButton subtract = createButton("-");
      JButton clear = createButton("C");
      JButton zero = createButton("0");
      JButton equals = createButton("=");
      JButton sum = createButton("+");
      
      // attaches actionListeners to all the buttons
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
      
      
      // adds buttons to buttonPanel
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
      
      // outputWindow and buttonPanel are added to the main JFrame, calcWindow
      calcWindow.add(outputWindow, BorderLayout.NORTH);
      calcWindow.add(buttonPanel, BorderLayout.CENTER);
      
      
      calcWindow.setVisible(true);
   }
   
   public JButton createButton(String buttonText)
   {
      JButton tempButton = new JButton(buttonText);
      return tempButton;
   
   }
   
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
      // if (operator != "") // second operator 
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
      else 
      {
         outputText = Double.toString(Double.parseDouble(valueOne) / Double.parseDouble(valueTwo));
      }
      valueOne = outputText;
      System.out.println(outputText);
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
   
   // for responding to user input
   public void actionPerformed(ActionEvent e)
   {
      if (e.getActionCommand() == "C") // clear command used, outputWindow text and all calculation variables reset
      {
         actionPerformedClear(e);
      }  
      else if ((e.getActionCommand() == "/") || // records use of operator, stores what operator was chosen
          (e.getActionCommand() == "*") ||
          (e.getActionCommand() == "-") ||
          (e.getActionCommand() == "+"))
      {
         if (valueTwo == "")
         {
            actionPerformedOperator(e);
         }
      }

      else if (e.getActionCommand() == "=") // equals, performs the calculation
      {
         actionPerformedFindResult();
      }
      else  // operator not yet used or clear has been activated, builds up integer as a String
      {
         actionPerformedBuildValues(e);
      }
      
   
   }
}