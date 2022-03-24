package connectfour.ai.util;

import java.util.*;
import connectfour.ai.*;

public class AIFactory {
	private final AbstractMap<String, AISupplier> suppliers;

	public AIFactory() {
		suppliers = new HashMap<>();
	}

	public static AIFactory getDefaultFactory() {
		AISupplier[] supplierArray = new AISupplier[]{
			new HumanPlayerSupplier(),
			
			new BlockerAISupplier(),
			new NextColumnAISupplier(),
			new RandomAISupplier(),
			//new MinMaxAISupplier()
		};

		AIFactory factory = new AIFactory();
		for (AISupplier supplier : supplierArray) {
			factory.suppliers.put(supplier.getAIName(), supplier);
		}
		return factory;
	}
	
	public void registerSupplier(String name, AISupplier supplier) throws AIFactoryException {
		if (name == null) {
			throw new AIFactoryException("A supplier cannot have a name that is a null value.");
		}
		if (supplier == null) {
			throw new AIFactoryException("A supplier cannot be a null value.");
		}
		if (suppliers.containsKey(name)) {
			throw new AIFactoryException("A supplier named \"" + name + "\" already exists.");
		}
		suppliers.put(name, supplier);
	}
	
	public void unregisterSupplier(String name) throws AIFactoryException {
		if (name == null) {
			throw new AIFactoryException("A supplier cannot have a name that is a null value.");
		}
		if (!suppliers.containsKey(name)) {
			throw new AIFactoryException("A supplier named \"" + name + "\" does not exist.");
		}
		suppliers.remove(name);
	}
	
	public AbstractList<String> getRegisteredNames() {
		ArrayList<String> names = new ArrayList<>(suppliers.keySet());
		names.sort((String s1, String s2) -> s1.compareTo(s2));
		names.sort((String s1, String s2) -> (s1.toLowerCase()).compareTo(s2.toLowerCase()));
		return names;
	}
	
	public AbstractAI getAI(String name) throws AIFactoryException {
		if (name == null) {
			throw new AIFactoryException("A supplier cannot have a name that is a null value.");
		}
		if (!suppliers.containsKey(name)) {
			throw new AIFactoryException("A supplier named \"" + name + "\" does not exist.");
		}
		return suppliers.get(name).get();
	}

	public AISupplier getSupplier(String name) throws AIFactoryException {
		if (name == null) {
			throw new AIFactoryException("A supplier cannot have a name that is a null value.");
		}
		if (!suppliers.containsKey(name)) {
			throw new AIFactoryException("A supplier named \"" + name + "\" does not exist.");
		}
		return suppliers.get(name);
	}

	public int size() {
		return suppliers.size();
	}
}
