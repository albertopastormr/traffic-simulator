package logic;

import error.EventException;
import ini.IniSection;

public class Roundabout extends GenericJunction {
    public Roundabout(String id, int minValueInterval, int maxValueInterval) {

        super(id);
    }

    @Override
    public void completeSectionDetails(IniSection is) {

    }

    @Override
    public String getSectionName() {
        return null;
    }

    @Override
    public void advance() throws EventException {

    }
}
