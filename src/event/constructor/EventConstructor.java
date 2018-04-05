package event.constructor;

import event.Event;
import ini.IniSection;

public abstract class EventConstructor {

    protected String tag;
    protected String[] keys;
    protected String[] defaultValues;

    public EventConstructor() {
        this.tag = null;
        this.keys = null;
        this.defaultValues = null;
    }

    public abstract Event parser(IniSection section);

    protected static String validID(IniSection section, String key) {
        String s = section.getValue(key);
        if (!isValidID(s))
            throw new IllegalArgumentException("El valor " + s + " para " + key +
                    " no es un ID valido");
        else return s;
    }

    // valid IDs can contain numbers, underscore or letters
    private static boolean isValidID(String id) {
        return id != null && id.matches("[a-z0-9_]+");
    }

    protected static int parseInt(IniSection section, String key) {
        String v = section.getValue(key);
        if (v == null)
            throw new IllegalArgumentException("Nonexistent value for the key: " + key);
        else
            return Integer.parseInt(section.getValue(key));
    }

    protected static int parseInt(IniSection section, String key, int defaultValue) {
        String v = section.getValue(key);
        return ( (v != null) ? Integer.parseInt(section.getValue(key)) : defaultValue );
    }

    protected static int parseIntNoNegative(IniSection section, String key, int defaultValue) {
        int i = EventConstructor.parseInt(section, key, defaultValue);
        if (i < 0)
            throw new IllegalArgumentException("Value " + i + " for " + key + " is not a valid ID\n");
        else
            return i;
    }

    public String template(){
    	return "";
	}
}
