/**
 * Created by adinn on 17/06/16.
 */
public abstract class Task extends Thread
{
    private volatile Long result;

    Task(String name)
    {
        super(name);
    }

    public void doWork(int i) {
        computeIntensive(i);
    }

    public void ioWait() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException ie) {
            // do nothing
        }
    }

    public Long computeIntensive(int i) {
        if (i < 2) {
            return Long.valueOf(1L);
        } else {
            Long l1 = computeIntensive(i - 1);
            Long l2 = computeIntensive(i - 2);
            if (l1.compareTo(l2) == 0) {
                result = l1;
            } else if (l1.compareTo(l2) > 0) {
                result = l2;
            }
            return Long.valueOf(l1.longValue() + l2.longValue());
        }
    }
}
