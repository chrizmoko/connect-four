package connectfour.ai.util;

import java.util.*;
import java.nio.file.*;
import java.util.stream.Stream;
import connectfour.ai.AISupplierResponder;

public class AISupplierLoader {
	private static AbstractSet<Class<AISupplier>> loadedSuppliers = new HashSet<>();
	private static Path searchDirectory = null;
	
	public static void setSearchDirectoryPath(String dirpath) throws AISupplierLoaderException {
		Path path = Paths.get(dirpath);
		if (!Files.exists(path)) {
			throw new AISupplierLoaderException("Search directory path does not exist.");
		}
		if (!Files.isDirectory(path)) {
			throw new AISupplierLoaderException("Search path does not point to a directory.");
		}
		searchDirectory = path;
	}
	
	public static String getSearchDirectoryPath() throws AISupplierLoaderException {
		if (searchDirectory == null) {
			throw new AISupplierLoaderException("Search directory path does not exist.");
		}
		return searchDirectory.toString();
	}
	
	public static void findClasses() throws Exception {
		if (searchDirectory == null) {
			throw new AISupplierLoaderException("Search directory path does not exist.");
		}
		
		// Retrieve all the paths in the search directory
		Path[] paths;
		try (Stream<Path> pathstream = Files.list(searchDirectory).filter(p -> !Files.isDirectory(p))) {
			Object[] obj = pathstream.toArray();
			paths = new Path[obj.length];
			for (int i = 0; i < obj.length; i++) {
				paths[i] = (Path)obj[i];
			}
		} 
		
		// Load AI suppliers source files from the search directory
		String packageName = AISupplierResponder.getPackageName();
		for (int i = 0; i < paths.length; i++) {
			String className = paths[i].getName(paths[i].getNameCount() - 1).toString().replaceFirst(".java", "");
			Class<?> cls = ClassLoader.getSystemClassLoader().loadClass(packageName + "." + className);
			
			// Check if the loaded class implements the AISupplier interface
			for (Class<?> inter : cls.getInterfaces()) {
				if (isSubinterfaceOf(inter, AISupplier.class)) {
					loadedSuppliers.add((Class<AISupplier>)cls);
					break;
				}
			}
		}
	}
	
	public static Collection<Class<AISupplier>> getSupplierClasses() {
		return loadedSuppliers;
	}
	
	public static Collection<AISupplier> getSupplierInstances() {
		ArrayList<AISupplier> instances = new ArrayList<>();
		try {
			for (Class<AISupplier> cls : loadedSuppliers) {
				AISupplier supp = cls.getConstructor().newInstance();
				instances.add(supp);
			}
		} catch (Exception e) {
			return null;
		}
		return instances;
	}
	
	private static boolean isSubinterfaceOf(Class<?> subinter, Class<?> inter) {
		if (!subinter.isInterface() || !inter.isInterface()) {
			return false;
		}
		if (subinter.getName().equals(inter.getName())) {
			return true;
		}
		Class<?>[] inters = subinter.getInterfaces();
		boolean result = false;
		for (int i = 0; i < inters.length; i++) {
			result = isSubinterfaceOf(inters[i], inter);
			System.out.println(inters[i].getName() + " " + result);
			if (result) {
				break;
			}
		}
		return result;
	}
}
