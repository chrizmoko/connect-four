package connectfour.ai.util;

import java.util.*;
import java.nio.file.*;
import java.util.stream.Stream;
import connectfour.ai.AIClassLoaderResponder;

public class AIClassLoader {
	private static AbstractSet<AISupplier> loadedSuppliers = new HashSet<>();
	private static AbstractSet<AI> loadedAIs = new HashSet<>();
	private static Path searchDirectory = null;
	
	public static void setSearchDirectoryPath(String dirpath) throws AIClassLoaderException {
		Path path = Paths.get(dirpath);
		if (!Files.exists(path)) {
			throw new AIClassLoaderException("Search directory path does not exist.");
		}
		if (!Files.isDirectory(path)) {
			throw new AIClassLoaderException("Search path does not point to a directory.");
		}
		searchDirectory = path;
	}
	
	public static String getSearchDirectoryPath() throws AIClassLoaderException {
		if (searchDirectory == null) {
			throw new AIClassLoaderException("Search directory path does not exist.");
		}
		return searchDirectory.toString();
	}
	
	public static void findClasses() throws Exception {
		if (searchDirectory == null) {
			throw new AIClassLoaderException("Search directory path does not exist.");
		}
		
		Path[] paths;
		
		// Retrieve all the paths in the search directory
		try (Stream<Path> pathstream = Files.list(searchDirectory).filter(p -> !Files.isDirectory(p))) {
			Object[] obj = pathstream.toArray();
			paths = new Path[obj.length];
			for (int i = 0; i < obj.length; i++) {
				paths[i] = (Path)obj[i];
				// System.out.println(paths[i]);
			}
		} 
		
		// Load the AI and AI suppliers source files from the search directory
		String packageName = AIClassLoaderResponder.getPackageName();
		for (int i = 0; i < paths.length; i++) {
			String className = paths[i].getName(paths[i].getNameCount() - 1).toString().replaceFirst(".java", "");
			Class<?> cls = ClassLoader.getSystemClassLoader().loadClass(packageName + "." + className);
			Object obj = cls.getConstructor().newInstance();
			if (obj instanceof AI) {
				loadedAIs.add((AI)obj);
			}
			if (obj instanceof AISupplier) {
				loadedSuppliers.add((AISupplier)obj);
			}
		}	
	}
	
	public static String[] listSupplierAINames() throws AIClassLoaderException {
		if (searchDirectory == null) {
			throw new AIClassLoaderException("Search directory has not been specified.");
		}
		ArrayList<String> names = new ArrayList<>();
		for (AISupplier supp : loadedSuppliers) {
			names.add(supp.getAIName());
		}
		String[] arr = new String[names.size()];
		return names.toArray(arr);
	}
	
	public static Collection<AI> getInstancesAI() {
		return loadedAIs;
	}
	
	public static Collection<AISupplier> getInstancesSuppliers() {
		return loadedSuppliers;
	}
}
