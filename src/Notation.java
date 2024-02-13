import java.util.List;

/** 
 * 
 *  @author Matthew Mucha
 */

public class Notation 
{
	public Notation()
	{
		
	}
	
	
	   
	public static String convertInfixToPostfix(String infix) throws InvalidNotationFormatException  
	{
		MyStack<String> operatorStack = new MyStack<>();
	    MyQueue<String> postfixQueue = new MyQueue<>();

	    try {
	        for (int i = 0; i < infix.length(); i++) 
	        {
	            char currentChar = infix.charAt(i);

	            switch (currentChar) 
	            {
	                case '+':
	                case '-':
	                    handleOperator(currentChar, 1, operatorStack, postfixQueue);
	                    break;
	                case '*':
	                case '/':
	                case '%':
	                    handleOperator(currentChar, 2, operatorStack, postfixQueue);
	                    break;
	                case '(':
					try 
					{
						operatorStack.push(String.valueOf(currentChar));
					} catch (StackOverflowException e) 
					{
						throw new InvalidNotationFormatException("Overflow"); 
					}
	                    break;
	                    
	                case ')':
	                    handleClosingParenthesis(operatorStack, postfixQueue);
	                    break;
	                    
	                default:
	                    // Operand, enqueue it directly
	                    postfixQueue.enqueue(String.valueOf(currentChar));
	                    break;
	            }
	        }

	        // Pop any remaining operators
	        while (!operatorStack.isEmpty()) 
	        {
	            try 
	            {
					postfixQueue.enqueue(operatorStack.pop());
				} catch (StackUnderflowException e) 
	            {
					throw new InvalidNotationFormatException(e);
				}
	        }

	        if (postfixQueue.isEmpty()) 
	        {
	            throw new QueueUnderflowException("Empty postfix queue");
	        }

	        return postfixQueue.toString();
	        
	    } 
	    catch (QueueUnderflowException | QueueOverflowException e) 
	    {
	        // Instead of printing stack trace, throw a new exception with a meaningful message
	        throw new InvalidNotationFormatException(e);
	    }
	    // or handle appropriately based on your needs
	}

	private static void handleOperator(char operator, int precedence, MyStack<String> operatorStack, MyQueue<String> postfixQueue) throws QueueUnderflowException, InvalidNotationFormatException 
	{
	    try 
	    {
	        while (!operatorStack.isEmpty() && getPrecedence(operatorStack.top()) >= precedence) 
	        {
	            try 
	            {
	                postfixQueue.enqueue(operatorStack.pop());
	            } catch (QueueOverflowException e) {
	                // Handle the exception or print an error message
	                e.printStackTrace();
	                throw new InvalidNotationFormatException("Error enqueueing to postfixQueue");
	            }
	        }
	    } catch (StackUnderflowException e) 
	    {
	        // Handle the exception or print an error message
	        e.printStackTrace();
	        throw new InvalidNotationFormatException("Error in handleOperator");
	    }
	    try 
	    {
	        operatorStack.push(String.valueOf(operator));
	    } catch (StackOverflowException e) 
	    {
	        // Handle the exception or print an error message
	        e.printStackTrace();
	        throw new InvalidNotationFormatException("Error pushing to operatorStack");
	    }
	}

	private static void handleClosingParenthesis(MyStack<String> operatorStack, MyQueue<String> postfixQueue) throws QueueUnderflowException, InvalidNotationFormatException {
	    try 
	    {
	        while (!operatorStack.isEmpty() && !operatorStack.top().equals("(")) 
	        {
	            postfixQueue.enqueue(operatorStack.pop());
	        }
	    } 
	    catch (StackUnderflowException | QueueOverflowException e) 
	    {
	        // Handle the exceptions or print error messages
	        e.printStackTrace();
	        throw new InvalidNotationFormatException("Error in handleClosingParenthesis");
	    }

	    try 
	    {
	        if (!operatorStack.isEmpty() && operatorStack.top().equals("(")) 
	        {
	            operatorStack.pop(); // Discard the '('
	        } 
	        else 
	        {
	            // Error: Mismatched parentheses
	            throw new InvalidNotationFormatException("Mismatched parentheses");
	        }
	    } 
	    catch (StackUnderflowException | InvalidNotationFormatException e) 
	    {
	        // Handle the exceptions or print error messages
	        e.printStackTrace();
	        throw new InvalidNotationFormatException("Error in handleClosingParenthesis");
	    }
	}

	private static int getPrecedence(String operator) 
	{
	    switch (operator) 
	    {
	        case "+":
	        case "-":
	            return 1;
	        case "*":
	        case "/":
	        case "%":
	            return 2;
	        default:
	            return -1; // Unknown operator
	    }
	}
		

	    
	public static String convertPostfixToInfix(String postfix) throws InvalidNotationFormatException 
	{
	    MyStack<String> infixStack = new MyStack<>();

	    try 
	    {
	        for (int i = 0; i < postfix.length(); i++) 
	        {
	            char currentChar = postfix.charAt(i);

	            if (Character.isWhitespace(currentChar)) 
	            {
	                continue; // Ignore spaces
	            } 
	            else if (Character.isLetterOrDigit(currentChar)) 
	            {
	                infixStack.push(String.valueOf(currentChar));
	            } 
	            else if (isOperator(currentChar)) 
	            {
	                try 
	                {
	                    String operand2 = infixStack.pop();
	                    String operand1 = infixStack.pop();
	                    String result =  "(" + operand1 + currentChar + operand2 + ")";
	                    infixStack.push(result);
	                } 
	                catch (StackUnderflowException e) 
	                {
	                    throw new InvalidNotationFormatException("Invalid postfix expression: Not enough operands for operator");
	                }
	            } 
	            else 
	            {
	                throw new InvalidNotationFormatException("Invalid character in postfix expression: " + currentChar);
	            }
	        }

	        if (infixStack.size() != 1) 
	        {
	            throw new InvalidNotationFormatException("Invalid postfix expression: More than one value in the stack");
	        }

	        return infixStack.pop();
	    } 
	    catch (StackOverflowException | StackUnderflowException e) 
	    {
	        throw new InvalidNotationFormatException("Invalid postfix expression: Stack overflow");
	    }
	}
	private static boolean isOperator(char ch) 
	{
	    return ch == '+' || ch == '-' || ch == '*' || ch == '/';
	}

	public static double evaluatePostfixExpression(String postfixExpr) throws InvalidNotationFormatException 
	{
        MyStack<Integer> operandStack = new MyStack<>();

        try 
        {
            for (char currentChar : postfixExpr.toCharArray()) 
            {
                if (Character.isWhitespace(currentChar)) 
                {
                    // Ignore spaces
                    continue;
                } 
                else if (Character.isDigit(currentChar)) 
                {
                    // Operand, push it onto the stack
                    operandStack.push((int) (currentChar - '0'));
                } 
                else 
                {
                    // Operator
                    if (operandStack.size() < 2) 
                    {
                        throw new InvalidNotationFormatException("Not enough operands for operator");
                    }
                    int operand2 = operandStack.pop();
                    int operand1 = operandStack.pop();
                    int result;

                    switch (currentChar) 
                    {
                        case '+':
                            result = operand1 + operand2;
                            break;
                        case '-':
                            result = operand1 - operand2;
                            break;
                        case '*':
                            result = operand1 * operand2;
                            break;
                        case '/':
                            if (operand2 == 0) {
                                throw new InvalidNotationFormatException("Division by zero");
                            }
                            result = operand1 / operand2;
                            break;
                        case '%':
                            result = operand1 % operand2;
                            break;
                        default:
                            throw new InvalidNotationFormatException("Invalid operator: " + currentChar);
                    }

                    operandStack.push(result);
                }
            }

            if (operandStack.size() != 1) 
            {
                throw new InvalidNotationFormatException("Invalid postfix expression");
            }

            return operandStack.pop();
        } 
        catch (StackOverflowException | StackUnderflowException e) 
        {
            throw new InvalidNotationFormatException(e.getMessage());
        }
    }
		
		
	
	

	
}

	

