package model;

public class ProposalThin
{
    private String name;
    private short state;

    public ProposalThin(String name, short state) {
        this.name = name;
        this.state = state;
    }
    public String getName() {
        return name;
    }

    public short getState() {
        return state;
    }
}
