
public class SynchronizedBuffer implements Buffer{

	private int[] bufferArr;
	private int write, read, used;
	
	public SynchronizedBuffer(int capacity) {
		// TODO Auto-generated constructor stub
		this.bufferArr = new int[capacity];
		this.used = this.write = this.read = 0;
	}

	// place value into buffer
	public synchronized void set(int value) throws InterruptedException {
		while(bufferArr.length == this.used) {
			wait();
		}
		
		this.used++;
		this.bufferArr[this.write] = value;
		this.write++;
		this.write = this.write % this.bufferArr.length;		
		System.out.printf("Producer writes\t%2d", value);
		notifyAll();
	} // end method set

	// return value from buffer
	public synchronized int get() throws InterruptedException {
		int arrValue = 0; 
		
		while(this.used == 0) {
			wait();
		}		
		
		arrValue = this.bufferArr[this.read];		
		
		this.used--;
		this.read++;
		this.read = this.read % this.bufferArr.length;
		
		System.out.printf("Consumer reads\t%2d", arrValue);		
		notifyAll();
		return arrValue;			
	} // end method get
} // end class UnsynchronizedBuffer
