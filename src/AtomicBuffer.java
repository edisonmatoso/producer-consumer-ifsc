import java.util.concurrent.atomic.AtomicInteger;

public class AtomicBuffer implements Buffer {
	
	private int buffer[];
	private int setPos = 0, getPos = 0;
	private AtomicInteger tamanho;
	
	public AtomicBuffer(int t) {
		tamanho = new AtomicInteger(0);
		buffer = new int[t];
	}

	@Override
	public void set(int value) throws InterruptedException {
		while(buffer.length == tamanho.addAndGet(0));
		
		tamanho.incrementAndGet();
		this.buffer[this.setPos] = value;
		this.setPos++;
		this.setPos = this.setPos % this.buffer.length;	
		System.out.printf("Producer writes\t%2d", value);
	}

	@Override
	public int get() throws InterruptedException {
		int arrValue = 0;
		
		while(tamanho.addAndGet(0) == 0);
		tamanho.decrementAndGet();
		
		arrValue = this.buffer[this.getPos];
		
		this.getPos++;
		this.getPos = this.getPos % this.buffer.length;
		
		System.out.printf("Consumer reads\t%2d", arrValue);		
		
		return arrValue;
	}

	
}
