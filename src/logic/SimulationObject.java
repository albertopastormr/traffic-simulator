package logic;

import error.EventException;
import ini.IniSection;

public abstract class SimulationObject {

    protected String id;

    public SimulationObject(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    @Override
    public String toString() {
        return this.id;
    }

    public String generateReport(int time) {
        IniSection is = new IniSection(this.getSectionName());
        is.setValue("id", this.id);
        is.setValue("time", time + 1);
        this.completeSectionDetails(is);
        return is.toString() + "\n";
    }

    public abstract void completeSectionDetails(IniSection is);
    public abstract String getSectionName();

    public abstract void advance() throws EventException;


}