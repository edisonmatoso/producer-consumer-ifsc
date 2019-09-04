import java.util.concurrent.locks.ReentrantLock;

//Fig. 23.9: UnsynchronizedBuffer.java
//UnsynchronizedBuffer represents a single shared integer.

public class LockBufferCircular implements Buffer {
	private ReentrantLock mutex = new ReentrantLock();
	private int[] bufferArr;
	private int write = 0, read = 0, used = 0;
	
	public LockBufferCircular(int arrLength) {
		this.bufferArr = new int[arrLength];
	}

	// place value into buffer
	public void set(int value) {		
		
		try {
			mutex.lock();
			while(bufferArr.length == this.used) {
				mutex.unlock();
				mutex.lock();
			}
			
			this.used++;
			this.bufferArr[this.write] = value;
			this.write++;
			this.write = this.write % this.bufferArr.length;		
			System.out.printf("Producer writes\t%2d", value);
			
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			mutex.unlock();
		}
		
	} // end method set

	// return value from buffer
	public int get() {
		int arrValue = 0; 
		
		try {
			mutex.lock();
			while(this.used == 0) {
				mutex.unlock();
				mutex.lock();
			}		
			
			arrValue = this.bufferArr[this.read];		
			
			this.used--;
			this.read++;
			this.read = this.read % this.bufferArr.length;
			
			System.out.printf("Consumer reads\t%2d", arrValue);	
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			mutex.unlock();
		}
		
			
		
		return arrValue;				
	} // end method get
} // end class UnsynchronizedBuffer

/**************************************************************************
 * (C) Copyright 1992-2005 by Deitel & Associates, Inc. and * Pearson Education,
 * Inc. All Rights Reserved. * * DISCLAIMER: The authors and publisher of this
 * book have used their * best efforts in preparing the book. These efforts
 * include the * development, research, and testing of the theories and programs
 * * to determine their effectiveness. The authors and publisher make * no
 * warranty of any kind, expressed or implied, with regard to these * programs
 * or to the documentation contained in these books. The authors * and publisher
 * shall not be liable in any event for incidental or * consequential damages in
 * connection with, or arising out of, the * furnishing, performance, or use of
 * these programs. *
 *************************************************************************/
