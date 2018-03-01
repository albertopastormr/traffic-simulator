package logic;

import error.EventException;
import ini.IniSection;

public class CongestedJunction extends GenericJunction {
    public CongestedJunction(String id) {
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
