import java.util.NoSuchElementException;

/**
 *  Simple Queue interface.
 *  @author benleong
 */
public interface Queue {

    /**
     * Enqueues an object in the queue
     * @param o Object to be enqueued
     * @throws OutOfMemoryError if queue is full.
     */
    public void enqueue(Object o) throws OutOfMemoryError;

    /**
     * Removes and returns the object at the head of the queue.
     * @return object at the head of the queue.
     * @throws NoSuchElementException if queue is empty
     */
    public Object dequeue() throws NoSuchElementException;

    /**
     * Returns the object at the head of the queue.
     * @return object at the head of the queue.
     * @throws NoSuchElementException if queue is empty
     */
    public Object peek() throws NoSuchElementException;

}
