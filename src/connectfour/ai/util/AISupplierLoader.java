package connectfour.ai.util;

import java.util.*;
import java.util.stream.Stream;
import java.nio.file.*;

public class AISupplierLoader {
	private static final String packageName = "connectfour.ai";
	private static final String extension = ".class";
	private static final AbstractSet<Class<AISupplier>> loadedSuppliers = new HashSet<>();
	
	public static void findClasses() throws Exception {
		// Retrieve all paths that are files in the package
		ClassLoader loader = ClassLoader.getSystemClassLoader();
		Path directory = Paths.get(loader.getResource(packageName.replace('.', '/')).toURI());
		try (Stream<Path> pathStream = Files.list(directory).filter(p -> !Files.isDirectory(p))) {
			
			// Retrieve all classes in the package
			for (Path path : pathStream.toArray(Path[]::new)) {
				String className = path.getName(path.getNameCount() - 1).toString();
				if (className.endsWith(extension)) {
					Class<?> cls = Class.forName(packageName + "." + className.replace(extension, ""));
					
					// Add class to supplier list if it implements AISupplier interface
					for (Class<?> inter : cls.getInterfaces()) {
						if (isSubinterfaceOf(inter, AISupplier.class)) { 
							loadedSuppliers.add((Class<AISupplier>)cls);
							break;
						}
					}
				}
			}	
		}
	}
	
	public static Collection<Class<AISupplier>> getSupplierClasses() {
		return loadedSuppliers;
	}
	
	public static Collection<AISupplier> getSupplierInstances() {
		ArrayList<AISupplier> instances = new ArrayList<>(loadedSuppliers.size());
		try {
			for (Class<AISupplier> cls : loadedSuppliers) {
				AISupplier supplier = cls.getConstructor().newInstance();
				instances.add(supplier);
			}
			instances.trimToSize();
		} catch (Exception e) {
			instances = null;
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
			if (isSubinterfaceOf(inters[i], inter)) {
				return true;
			}
		}
		return result;
	}
}
