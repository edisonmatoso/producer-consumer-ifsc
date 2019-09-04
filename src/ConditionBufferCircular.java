import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionBufferCircular implements Buffer{

	private ReentrantLock mutex = new ReentrantLock();
	private int[] bufferArr;
	private int write = 0, read = 0, used = 0;
	private Condition podeLer = mutex.newCondition();
	private Condition podeEscrever = mutex.newCondition();
	
	public ConditionBufferCircular(int arrLength) {
		this.bufferArr = new int[arrLength];
	}

	// place value into buffer
	public void set(int value) {		
		
		try {
			mutex.lock();
			while(bufferArr.length == this.used) {
				podeEscrever.await();
			}
			
			this.used++;
			this.bufferArr[this.write] = value;
			this.write++;
			this.write = this.write % this.bufferArr.length;		
			System.out.printf("Producer writes\t%2d", value);
			podeLer.signalAll();
			
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			mutex.unlock();
		}
		
	} // end method set
	
	public int get() {
		int arrValue = 0; 
		
		try {
			mutex.lock();
			while(this.used == 0) {
				podeLer.await();
			}		
			
			arrValue = this.bufferArr[this.read];		
			
			this.used--;
			this.read++;
			this.read = this.read % this.bufferArr.length;
			
			System.out.printf("Consumer reads\t%2d", arrValue);	
			podeEscrever.signalAll();
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			mutex.unlock();
		}
		
			
		
		return arrValue;				
	} // end method get
}
