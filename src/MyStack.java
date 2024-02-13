
import java.util.ArrayList;
/**
 * @author Matthew Mucha
 */
public class MyStack<T> implements StackInterface<T> 
{
    private ArrayList<T> stack;
    private int size;
    private int maxSize;

    // Default constructor
    public MyStack() 
    {
        this.maxSize = Integer.MAX_VALUE;
        this.size = 0;
        this.stack = new ArrayList<>();
    }

    // Constructor with size parameter
    public MyStack(int maxSize) 
    {
        this.maxSize = maxSize;
        this.size = 0;
        this.stack = new ArrayList<>(maxSize);
    }

    @Override
    public boolean isEmpty() 
    {
        return size == 0;
    }

    @Override
    public boolean isFull() 
    {
        return size == maxSize;
    }

    @Override
    public T pop() throws StackUnderflowException 
    {
        if (isEmpty()) 
        {
            throw new StackUnderflowException("Stack is empty");
        }
        
        T poppedElement = stack.remove(--size);
        return poppedElement;
    }

    @Override
    public T top() throws StackUnderflowException 
    {
        if (isEmpty()) 
        {
            throw new StackUnderflowException("Stack is empty");
        }
        
        return stack.get(size - 1);
    }

    @Override
    public int size() 
    {
        return size;
    }

    @Override
    public boolean push(T e) throws StackOverflowException 
    {
        if (isFull()) 
        {
            throw new StackOverflowException("Stack is full");
        }
        
        stack.add(e);
        size++;
        return true;
    }

    @Override
    public String toString() 
    {
        StringBuilder result = new StringBuilder();
        for (T element : stack) 
        {
            result.append(element).append("");
        }
        
        return result.toString().trim();
    }

    @Override
    public String toString(String delimiter) 
    {
    	
    	
        StringBuilder result = new StringBuilder();
        
        for (T element : stack) 
        {
            result.append(element).append(delimiter);
        }
        
        
        return result.length() > 0 ? result.substring(0, result.length() - delimiter.length()) : "";
    }

    @Override
    public void fill(ArrayList<T> list) throws StackOverflowException
    {
    	
        for (T element : list) 
        {
            push(element);
        }
    }
}