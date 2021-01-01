package connectfour.ai.util;

import java.util.*;
import java.io.*;

public class AISupplierLoader {
	private static final String CLASS_EXTENSION = ".class";
	private static final AbstractList<String> packageNames = new ArrayList<>();
	private static final AbstractSet<Class<AISupplier>> loadedSuppliers = new HashSet<>();
	
	public static void addPackageName(String packageName) {
		packageNames.add(packageName);
	}
	
	public static void removePackageName(String packageName) {
		packageNames.remove(packageName);
	}
	
	public static void findClasses() throws Exception {
		for (String name : packageNames) {
			findClasses(name);
		}
	}
	
	public static void findClasses(String packageName) throws Exception {
		// Retrieve all paths that are files in the package
		ClassLoader loader = ClassLoader.getSystemClassLoader();
		File directory = new File(loader.getResource(packageName.replace('.', '/')).toURI());
		for (File file : directory.listFiles((File f) -> f.isFile())) {
			// Retrieve all classes in the directory (package)
			if (file.getName().endsWith(CLASS_EXTENSION)) {
				String className = file.getName().replace(CLASS_EXTENSION, "");
				Class<?> cls = Class.forName(packageName + "." + className);
				
				// Add class to the set of suppliers if it implements the AISupplier interface
				for (Class<?> inter : cls.getInterfaces()) {
					if (implementsInterface(inter, AISupplier.class)) {
						loadedSuppliers.add((Class<AISupplier>)cls);
						break;
					}
				}
			}
		}
	}
	
	public static void resetClasses() {
		loadedSuppliers.removeAll(loadedSuppliers);
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
	
	private static boolean implementsInterface(Class<?> subInter, Class<?> inter) {
		if (!subInter.isInterface() || !inter.isInterface()) {
			return false;
		}
		if (subInter.getName().equals(inter.getName())) {
			return true;
		}
		Class<?>[] inters = subInter.getInterfaces();
		boolean result = false;
		for (int i = 0; i < inters.length; i++) {
			if (implementsInterface(inters[i], inter)) {
				return true;
			}
		}
		return result;
	}
}
