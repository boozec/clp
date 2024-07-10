package codegen;

public class Label {

	private static String funDef = "";
	private static int labelCounter = 0;
	private static int functionLabelCounter = 0;

	public static void addFunDef(String s) {
		funDef += s;
	}

	public static String getFunDef() {
		return funDef;
	}

	/**
	 * Create a new basic label. Use this method to define labels for if, while and
	 * for statemests.
	 */
	public static String newBasic(String base) {
		return base + (labelCounter++);
	}

	/**
	 * Create a new label for a function definition.
	 */
	public static String newFun(String base) {
		return base + (functionLabelCounter++);
	}

}
