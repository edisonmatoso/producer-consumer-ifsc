import java.util.concurrent.locks.ReentrantLock;

//Fig. 23.9: UnsynchronizedBuffer.java
//UnsynchronizedBuffer represents a single shared integer.

public class LockBuffer implements Buffer {
	private int buffer = -1; // shared by producer and consumer threads
	private ReentrantLock mutex = new ReentrantLock();

	// place value into buffer
	public void set(int value) {		
		try {
			mutex.lock();
			while(buffer != -1) {
				mutex.unlock();
				mutex.lock();
			}
			buffer = value;
			System.out.printf("Producer writes\t%2d", value);
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
				mutex.unlock();
				mutex.lock();
			}			
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
