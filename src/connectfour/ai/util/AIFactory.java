package connectfour.ai.util;

import java.util.*;

public class AIFactory {
	private static final AbstractMap<String, AISupplier> suppliers = new HashMap<>();
	
	public static void registerSupplier(String name, AISupplier supplier) throws AIFactoryException {
		if (name == null) {
			throw new AIFactoryException(
				"A supplier cannot have a name that is a null value."
			);
		}
		if (supplier == null) {
			throw new AIFactoryException(
				"A supplier cannot be a null value."
			);
		}
		if (suppliers.containsKey(name)) {
			throw new AIFactoryException(
				"A supplier under the name \"" + name + "\" has already been registered."
			);
		}
		suppliers.put(name, supplier);
	}
	
	public static void unregisterSupplier(String name) throws AIFactoryException {
		if (name == null) {
			throw new AIFactoryException(
				"A supplier cannot have a name that is a null value."
			);
		}
		if (!suppliers.containsKey(name)) {
			throw new AIFactoryException(
				"A supplier under the name \"" + name + "\" does not exist."
			);
		}
		suppliers.remove(name);
	}
	
	public static AbstractList<String> getRegisteredNames() {
		ArrayList<String> names = new ArrayList<>(suppliers.keySet());
		names.sort((String s1, String s2) -> s1.compareTo(s2));
		names.sort((String s1, String s2) -> (s1.toLowerCase()).compareTo(s2.toLowerCase()));
		return names;
	}
	
	public static AbstractAI getAI(String name) throws AIFactoryException {
		if (name == null) {
			throw new AIFactoryException(
				"A supplier cannot have a name that is a null value."
			);
		}
		if (!suppliers.containsKey(name)) {
			throw new AIFactoryException(
				"A supplier under the name \"" + name + "\" does not exist."
			);
		}
		return suppliers.get(name).get();
	}
}
