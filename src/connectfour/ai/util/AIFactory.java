package connectfour.ai.util;

import java.util.*;

public class AIFactory {
	private static final AbstractMap<String, AISupplier> suppliers = new HashMap<>();
	
	public static void registerSupplier(String name, AISupplier supplier) throws AIFactoryException {
		if (suppliers.containsKey(name)) {
			throw new AIFactoryException("A supplier under the name " + name + " has already been registered.");
		}
		suppliers.put(name, supplier);
	}
	
	public static void unregisterSupplier(String name) throws AIFactoryException {
		if (!suppliers.containsKey(name)) {
			throw new AIFactoryException("A supplier under the name " + name + " does not exist.");
		}
		suppliers.remove(name);
	}
	
	public String[] getRegisteredNames() {
		String[] names = new String[suppliers.size()];
		int index = 0;
		for (String val : suppliers.keySet()) {
			names[index] = val;
			index++;
		}
		return names;
	}
	
	public AbstractAI getAI(String name) throws AIFactoryException {
		if (!suppliers.containsKey(name)) {
			throw new AIFactoryException("A supplier under the name " + name + " does not exist.");
		}
		return suppliers.get(name).get();
	}
}
