import java.util.ArrayList;

public class MyQueue<T> implements QueueInterface<T> 
/**
 * @author Matthew Mucha
 */
{
    private static final int DEFAULT_SIZE = 50;
    private int maxSize;
    private Object[] queue;
    private int front, rear, qSize;

    public MyQueue() 
    {
        this(DEFAULT_SIZE);
    }

    public MyQueue(int size) 
    {
    	maxSize = size;
    	queue = new Object[maxSize];
    	qSize = 0;
    	front = 0;
    	rear = -1;
    }

    @Override
    public boolean isEmpty() 
    {
        return qSize == 0;
    }

    @Override
    public boolean isFull() 
    {
        return qSize == maxSize;
    }

    @Override
    public T dequeue() throws QueueUnderflowException 
    {
    	if (isEmpty()) 
        {
            throw new QueueUnderflowException("Queue is empty");
        }

        T value = (T) queue[front];
        queue[front] = null;
        front = (front + 1) % maxSize;
        qSize--;

        return value;
    }

    @Override
    public int size() 
    {
        return qSize;
    }

    @Override
    public boolean enqueue(T value) throws QueueOverflowException 
    {
        if (isFull()) 
        {
            throw new QueueOverflowException("Queue is full");
        }

        rear = (rear + 1) % maxSize;
        queue[rear] = value;
        qSize++;

        return true;
    }

    @Override
    public String toString() 
    {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < qSize; i++) 
        {
            result.append(queue[(front + i) % maxSize]);
            if (i < qSize - 1) 
            {
                result.append("");
            }
        }

        return result.toString();
    }

    @Override
    public String toString(String delimiter) 
    {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < qSize; i++) 
        {
            result.append(queue[(front + i) % maxSize]);
            if (i < qSize - 1) 
            {
                result.append(delimiter);
            }
        }

        return result.toString();
    }

    @Override
    public void fill(ArrayList<T> list) throws QueueOverflowException 
    {
        if (list.size() > maxSize) 
        {
            throw new QueueOverflowException("List size exceeds the queue size");
        }

        for (T element : list) 
        {
            enqueue(element);
        }
    }
}