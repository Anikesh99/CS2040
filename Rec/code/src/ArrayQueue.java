import java.util.NoSuchElementException;
import java.lang.OutOfMemoryError;

/**
 * Queue that is backed with a simple object array.
 * @author benleong
 */
public class ArrayQueue implements Queue {

    /**
     * Test code
     * @param args
     */
    private int x;
    private Object[] array;

    public ArrayQueue(int x){
        this.x = x;
        array = new Object[x];
    }

    public static void main(String[] args) {
        ArrayQueue q = new ArrayQueue(5);
        for (int i=0; i<5; i++) {
            q.enqueue(i);
        }
        for (int i=0; i<5; i++) {
            if (i==2) {
                System.out.println(q.peek());
            }
            System.out.println(q.dequeue());
        }
    }

    @Override
    public void enqueue(Object o) throws OutOfMemoryError{
        Object[] newArray = new Object[array.length + 1];
        for(int i = 0; i < array.length; i++){
            newArray[i + 1] = array[i];
        }
        newArray[0] = o;
        this.array = newArray;
    }

    /**
     * Removes and returns the object at the head of the queue.
     * @return object at the head of the queue.
     * @throws NoSuchElementException if queue is empty
     */
    @Override
    public Object dequeue() throws NoSuchElementException{
        Object returner = array[0];
        Object[] newArray = new Object[x - 1];

        for(int i = x - 2; i >= 0; i--){
            newArray[i] = array[i + 1];
        }

        this.array = newArray;
        return returner;
    }

    /**
     * Returns the object at the head of the queue.
     * @return object at the head of the queue.
     * @throws NoSuchElementException if queue is empty
     */
    @Override
    public Object peek() throws NoSuchElementException {
        return array[0];
    }
}
