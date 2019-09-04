import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionBuffer implements Buffer{
	
	private ReentrantLock mutex = new ReentrantLock();
	private Condition podeLer = mutex.newCondition();
	private Condition podeEscrever = mutex.newCondition();
	private int buffer = -1; // shared by producer and consumer threads	
	
	// place value into buffer
		public void set(int value) {		
			try {
				mutex.lock();
				while(buffer != -1) {
					podeEscrever.await();
				}
				
				buffer = value;
				System.out.printf("Producer writes\t%2d", value);
				podeLer.signalAll();
			} catch (Exception e) {
				// TODO: handle exception
			} finally {
				mutex.unlock();
			}		
			
		} // end method set

		// return value from buffer
		public int get() {
			int value = 0;
			try {
				mutex.lock();
				while(buffer == -1) {
					podeLer.await();
				}
				
				podeEscrever.signalAll();
				value = buffer;
				buffer = -1; 
				System.out.printf("Consumer reads\t%2d", value);
			} catch (Exception e) {
				// TODO: handle exception
			} finally {
				mutex.unlock();
			}		
			
			return value;				
		} // end method get

}
