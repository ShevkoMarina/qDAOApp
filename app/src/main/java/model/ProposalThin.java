package model;

public class ProposalThin
{
    private long id;
    private String name;
    private short state;

    public ProposalThin(long id, String name, short state) {
        this.id = id;
        this.name = name;
        this.state = state;
    }
    public String getName() {
        return name;
    }

    public short getState() {
        return state;
    }

    public long getId() {
        return id;
    }
}
