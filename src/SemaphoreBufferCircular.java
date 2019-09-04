import java.util.concurrent.Semaphore;

public class SemaphoreBufferCircular implements Buffer {

	private Semaphore sem = new Semaphore(1);
	private Semaphore cond = new Semaphore(0);
	private int write = 0, read = 0, used = 0;
	private int bufferArr[];
	
	public SemaphoreBufferCircular(int tamanho) {
		bufferArr = new int[tamanho];
	}

	// place value into buffer
		public void set(int value) {		
			
			try {
				sem.acquire();
				while(bufferArr.length == this.used) {
					sem.release();
					cond.acquire();
					sem.acquire();
				}
				
				this.used++;
				this.bufferArr[this.write] = value;
				this.write++;
				this.write = this.write % this.bufferArr.length;		
				System.out.printf("Producer writes\t%2d", value);
				cond.release();
				
			} catch (Exception e) {
				// TODO: handle exception
			} finally {
				sem.release();
			}
			
		} // end method set
		
		public int get() {
			int arrValue = 0; 
			
			try {
				sem.acquire();
				while(this.used == 0) {
					sem.release();
					cond.acquire();
					sem.acquire();
				}		
				
				arrValue = this.bufferArr[this.read];		
				
				this.used--;
				this.read++;
				this.read = this.read % this.bufferArr.length;
				
				System.out.printf("Consumer reads\t%2d", arrValue);	
				cond.release();
			} catch (Exception e) {
				// TODO: handle exception
			} finally {
				sem.release();
			}
			
				
			
			return arrValue;				
		} // end method get
}
