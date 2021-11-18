package test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

public class Test4 {
	public static void main(String[] args) throws InterruptedException, ClassNotFoundException, IOException {
		/*putMap("abc", System.currentTimeMillis(), "C:/ITS/performance.dat");
		long end_time = System.currentTimeMillis();
		HashMap<String, Long> map = getMap("C:/ITS/performance.dat");
		System.out.println(end_time);
		System.out.println(getvalue(map, "abc"));
		long difference = end_time - getvalue(map, "abc");*/
		System.out.println(File.separator);
		System.out.println("/Users/al/tmp/000/111/222/abc.json".substring(0, "/Users/al/tmp/000/111/222/abc.json".lastIndexOf(File.separator)));
	}

	private static Long getvalue(HashMap<String, Long> map, String key) {
		Long value = map.get(key);
		return value == null ? 0 : value;
	}
	
	private static HashMap<String, Long> getMap(String db) throws IOException, ClassNotFoundException {
		final File file = new File(db);
		FileInputStream f = new FileInputStream(file);
		final ObjectInputStream input = new ObjectInputStream(f);
		// Reads the first object in
		final Object readObject = input.readObject();
		input.close();
		return (HashMap<String, Long>) readObject;
	}
	
	private static void putMap(String key, long value, String database) throws IOException, ClassNotFoundException {
		final File file = new File(database);
		FileInputStream f = new FileInputStream(file);
		final ObjectInputStream input = new ObjectInputStream(f);
		// Reads the first object in
		final Object readObject = input.readObject();
		input.close();
		f.close();
		HashMap<String, Long> map = new HashMap<String, Long>();
		try {
			map = (HashMap<String, Long>) readObject;
		} catch (Exception e) {
		}
		// Prints out everything in the map.
		map.put(key, value);
		{
			//File file2 = new File(database);
			FileOutputStream f2 = new FileOutputStream(file);
			ObjectOutputStream s = new ObjectOutputStream(f2);
			s.writeObject(map);
			s.close();
			f2.close();
		}
	}

}
