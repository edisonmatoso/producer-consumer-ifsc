import java.util.concurrent.Semaphore;

public class SemaphoreBuffer implements Buffer {
		private int buffer = -1; // shared by producer and consumer threads
		private Semaphore sem = new Semaphore(1);
		private Semaforo cond = new Semaforo(0);
		
		// place value into buffer
		public void set(int value) {			
			try {
				sem.acquire(); 
				
				while(buffer != -1) {
					sem.release();
					cond.acquire();
					sem.acquire();
				}
				System.out.printf("Producer writes\t%2d", value);
				buffer = value;
				cond.release();
			} catch (Exception e) {}
			finally {
				sem.release();
			}
			
		} // end method set

		// return value from buffer
		public int get() {
			int value = 0;
			try {
				sem.acquire();
				while(buffer == -1) {
					sem.release();
					cond.acquire();
					sem.acquire();
				}			
				value = buffer;
				buffer = -1;
				cond.release();
				System.out.printf("Consumer reads\t%2d", value);
			} catch (Exception e) {
				// TODO: handle exception
			}
			finally {
				sem.release();
			}
			return value;
		} // end method get
}
