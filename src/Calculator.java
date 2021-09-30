import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Stack;

public class Calculator implements ActionListener {

    JFrame frame;//Frame for our calculator application
    JTextField screen;//Displays input & output for calculator
    //Buttons to process calculator
    JButton[] numberButtons = new JButton[10];
    JButton addButton, subButton, mulButton, divButton;
    JButton testButton, equButton, delButton, clrButton, negButton;
    JPanel panel;//Panel contains numerical buttons and calculation operators
    static boolean errorFlag = false;


    /*
        Input: None
        Output: None
        Description: This is the constructor which initializes the calculator. In this class we create the JFrame for
        the application, the JTextfield to display the input/output of the calculator, and create the buttons for
        the calculator. All buttons have ActionListners to process each button being pressed.
     */
    Calculator() {

        frame = new JFrame("Calculator");

        frame.setSize(400, 550);
        //setLayout so we can specify coordinates
        frame.setLayout(null);

        screen = new JTextField();
        screen.setBackground(Color.white);
        screen.setBounds(50, 25, 300, 50);
        screen.setEditable(false);

        //Initialize buttons and add event listeners
        addButton = new JButton("+");
        addButton.addActionListener(this);
        addButton.setFont(new java.awt.Font("Sans Serif", 2, 18)); // NOI18N
        addButton.setBackground(Color.LIGHT_GRAY);
        //addButton.setOpaque(true);
        subButton = new JButton("-");
        subButton.addActionListener(this);
        subButton.setFont(new java.awt.Font("Sans Serif", 2, 18)); // NOI18N
        subButton.setBackground(Color.LIGHT_GRAY);
        mulButton = new JButton("*");
        mulButton.addActionListener(this);
        mulButton.setFont(new java.awt.Font("Sans Serif", 2, 18)); // NOI18N
        mulButton.setBackground(Color.LIGHT_GRAY);
        divButton = new JButton("/");
        divButton.addActionListener(this);
        divButton.setFont(new java.awt.Font("Sans Serif", 2, 18)); // NOI18N
        divButton.setBackground(Color.LIGHT_GRAY);
        testButton = new JButton("^");
        testButton.addActionListener(this);
        testButton.setFont(new java.awt.Font("Sans Serif", 2, 18)); // NOI18N
        testButton.setBackground(Color.LIGHT_GRAY);
        equButton = new JButton("=");
        equButton.addActionListener(this);
        equButton.setFont(new java.awt.Font("Sans Serif", 2, 18)); // NOI18N
        equButton.setBackground(Color.LIGHT_GRAY);
        delButton = new JButton("<--");
        delButton.addActionListener(this);
        delButton.setFont(new java.awt.Font("Sans Serif", 2, 18));// NOI18N
        delButton.setBackground(Color.LIGHT_GRAY);
        clrButton = new JButton("AC");
        clrButton.addActionListener(this);
        clrButton.setFont(new java.awt.Font("Sans Serif", 2, 18)); // NOI18N
        clrButton.setBackground(Color.LIGHT_GRAY);
        negButton = new JButton("+/-");
        negButton.addActionListener(this);
        negButton.setFont(new java.awt.Font("Sans Serif", 2, 18)); // NOI18N
        negButton.setBackground(Color.LIGHT_GRAY);
        for (int i = 0; i < 10; i++) {
            numberButtons[i] = new JButton(String.valueOf(i));
            numberButtons[i].addActionListener(this);
            numberButtons[i].setFont(new java.awt.Font("Sans Serif", 2, 18)); // NOI18N
            numberButtons[i].setBackground(Color.LIGHT_GRAY);
        }

        negButton.setBounds(50, 430, 100, 50);
        delButton.setBounds(150, 430, 100, 50);
        clrButton.setBounds(250, 430, 100, 50);

        panel = new JPanel();
        panel.setBounds(50, 100, 300, 300);
        panel.setLayout(new GridLayout(4, 4, 10, 10)); //Define layout of panel

        //Add number buttons and operations to panel
        panel.add(numberButtons[1]);
        panel.add(numberButtons[2]);
        panel.add(numberButtons[3]);
        panel.add(addButton);
        panel.add(numberButtons[4]);
        panel.add(numberButtons[5]);
        panel.add(numberButtons[6]);
        panel.add(subButton);
        panel.add(numberButtons[7]);
        panel.add(numberButtons[8]);
        panel.add(numberButtons[9]);
        panel.add(mulButton);
        panel.add(testButton);
        panel.add(numberButtons[0]);
        panel.add(equButton);
        panel.add(divButton);

        //Add negative, delete and clear to frame
        frame.add(panel);
        frame.add(negButton);
        frame.add(delButton);
        frame.add(clrButton);
        frame.add(screen);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /*
     Input: String Arguments
     Output: None
     Description: This is the main class which creates a Calculator instance
     */
    public static void main(String[] args) {

        Calculator calc = new Calculator();
    }
    /*
     Input: ActionEvents(when buttons are pressed)
     Output: None
     Description: This processes buttons being pressed. If a button is pressed, we go through the if-else ladder to process
     what to do according to the button. If equal is pressed we calculate the expression on screen and displaying the output.
     if negative is pressed, we invert the sign of the number. If clear is pressed, we clear the screen. If delete is pressed
     we remove the last character from the right.
    */
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

       if (e.getSource()==equButton) {
            String expr = screen.getText();
            //Calculate string expression
            Integer ans = calculate(expr);
            if(errorFlag == true) {
                screen.setText("ERROR");
                errorFlag = false;
            }
            else
                screen.setText(String.valueOf(ans));
        }
        else if (e.getSource() == negButton) {
            //Reverse sign of digit
            Integer neg = Integer.parseInt(screen.getText());
            neg *= -1;
            screen.setText(String.valueOf(neg));
        }
        else if (e.getSource() == clrButton) {
            //Set screen to empty
           screen.setText("");
       }
       else if(e.getSource()==delButton) {
           //Delete last character of string by by outputting all character except the last
            String string = screen.getText();
           screen.setText("");
            for(int i=0;i<string.length()-1;i++) {
                screen.setText(screen.getText()+string.charAt(i));
            }
        }
       else {
           //Should NOT get here
            screen.setText(screen.getText() + command);
        }
    }
    /*
      Input: Expression string we are trying to calculate
      Output: None
      Description: Given a expression string we use this function to parse the elements of the string to compute the
      corresponding answer. We use a stack to give a high order to multiplication and division. For numbers, we use the formula
      below to construct the numerical values since we process the string left to right. For math operators, we push
      addition and subtraction numbers back on to the stack and only calculating multiplication and division first which
      the results then pushed back onto the stack. Finally we iterate through the stack and add all of the numbers and return
      this result.
    */
    public static int calculate(String s) {
        //Error check String
        if (s == null || s.length() == 0)
            return 0;
        int calculate = 0;
        char sign = '+';
        //Use a stack to calculate  * and / first and push answer onto stack
        Stack<Integer> stack = new Stack();
        //Iterate throught expression from left to right
        for (int i=0; i<s.length(); i++) {
            //Check if current character is a digit
            if (Character.isDigit(s.charAt(i)))
                calculate = calculate * 10 + s.charAt(i) - '0'; //use this formula to calculate the digit since we move left to right
            if ((!Character.isDigit(s.charAt(i))) || i == s.length() - 1) {//Perform opperation
                if (sign == '+')
                    stack.push(calculate);
                else if (sign == '-')
                    stack.push(-calculate);
                else if (sign == '*')
                    stack.push(stack.pop() * calculate);
                else if (sign == '/'){
                    if(calculate == 0) {
                        errorFlag = true;
                        return -1;
                    }
                    else{
                        stack.push(stack.pop() / calculate);
                    }
                }
                else if( sign == '^'){
                    stack.push((int)Math.pow(stack.pop(), calculate));
                }
                sign = s.charAt(i);
                calculate = 0;
            }
        }
        int result = 0;
        for (int items: stack)
            result += items;

        return result;
    }
}