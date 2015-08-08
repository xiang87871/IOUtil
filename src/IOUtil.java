import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Watchable;
import java.util.ArrayList;
import java.util.List;


public class IOUtil {
	
	
	/**
	 * 从给出的文件中读取一个byte数组
	 * @param path
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("resource")
	public static byte[] getByte(String path) throws IOException {
		File file = new File(path);
		if(!file.exists()) return null;
		FileInputStream fileInputStream = new FileInputStream(file);
		FileChannel channel = fileInputStream.getChannel();
		ByteBuffer bf = ByteBuffer.allocate((int) channel.size());
		bf.clear();
		channel.read(bf);
		return bf.array();
	}
	
	
	/**
	 * 读取数据
	 * @param path 路径
	 * @return 读取数据 不去重
	 */
	public static List<String> getListLineString(String path) {
		return getListLineString(path, false);
	}
	
	
	/**
	 * 读取数据
	 * @param path 路径
	 * @param deduplication 是否去重
	 * @return 读取数据
	 * @return
	 */
	public static List<String> getListLineString(String path, boolean deduplication) {
		BufferedReader br = null;
		List<String> list = new ArrayList<String>();
		try{
			File file = new File(path);
			if(!file.exists()) throw new RuntimeException("路径错误");
			br = new BufferedReader(new FileReader(file));
			for(String line = br.readLine();line != null; line = br.readLine()) {
				if(deduplication && list.contains(line)) 
					continue;
				list.add(line);
				
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(br!=null)
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		
		return list;
	}
	
	/**
	 * 写入数据
	 * @param list 数据
	 * @param path 路径
	 */
	public static void writeString(String path, List<String> list) {
		PrintWriter writer = null;
		try{
			File file = new File(path);
			if(!file.exists()) file.createNewFile();
			writer = new PrintWriter(file);
			for (String string : list) {
				writer.println(string);
			}
			writer.flush();
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(writer != null) writer.close();
		}
	}
		
	
	/**
	 * 读取一个文件中的所有字符
	 * @param path
	 * @return
	 */
	public static String readStringByNIO(String path) {
		FileInputStream fis = null;
		byte[] bs = new byte[]{};
		try{
			File file = new File(path);
			if(!file.exists()) file.createNewFile();
			fis = new FileInputStream(file);
			FileChannel channel = fis.getChannel();
			ByteBuffer bb = ByteBuffer.allocate(1024);
			channel.read(bb);
			bb.flip();
			bs = bb.array();
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(fis != null)
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		return new String(bs);
	}
	
	
	public static void writeStringByNIO(String s, String path) {
		FileOutputStream fos = null;
		try {
			File file = new File(path);
			if(!file.exists()) file.createNewFile();
			fos = new FileOutputStream(file);
			FileChannel channel = fos.getChannel();
			ByteBuffer wrap = ByteBuffer.wrap(s.getBytes());
			wrap.flip();
			while(wrap.hasRemaining()) {
				channel.write(wrap);
			}
			channel.force(true);
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(fos != null)
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}
	
	
	
}
