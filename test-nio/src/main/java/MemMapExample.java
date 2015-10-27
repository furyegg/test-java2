import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class MemMapExample {
	private static int mem_map_size = 10;
	private static String fn = "example_memory_mapped_file.txt";

	public static void main(String[] args) throws Exception {
		RandomAccessFile memoryMappedFile = new RandomAccessFile(fn, "rw");

		//Mapping a file into memory
		final MappedByteBuffer out1 = memoryMappedFile.getChannel().map(FileChannel.MapMode.READ_WRITE, 0, 10);
		final MappedByteBuffer out2 = memoryMappedFile.getChannel().map(FileChannel.MapMode.READ_WRITE, 11, 20);
		final MappedByteBuffer out3 = memoryMappedFile.getChannel().map(FileChannel.MapMode.READ_WRITE, 21, 30);

		Thread r1 = new Thread() {
			public void run() {
				for (int i = 0; i < mem_map_size; i++) {
					out1.put((byte) 'A');
				}
			}
		};

		Thread r2 = new Thread() {
			public void run() {
				for (int i = 0; i < mem_map_size; i++) {
					out2.put((byte) 'B');
				}
			}
		};

		Thread r3 = new Thread() {
			public void run() {
				for (int i = 0; i < mem_map_size; i++) {
					out3.put((byte) 'C');
				}
			}
		};

		r2.start();
		r3.start();
		r1.start();

//		out1.put(3, (byte) 'X');

		//Writing into Memory Mapped File

//		System.out.println("File '" + fn + "' is now " + Integer.toString(mem_map_size) + " bytes full.");

		// Read from memory-mapped file.
//		for (int i = 0; i < 30 ; i++) {
//			System.out.print((char) out1.get(i));
//		}
//		System.out.println("\nReading from memory-mapped file '" + fn + "' is complete.");

		memoryMappedFile.close();
	}
}