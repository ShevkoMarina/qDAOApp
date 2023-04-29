package model;

public class CreateProposalDto {

    private final String name;
    private final String description;
    private final int type;
    private final long userId;
    private final long newValue;

    public CreateProposalDto(
            String name,
            String description,
            int type,
            long userId,
            long newValue) {
        this.name = name;
        this.description = description;
        this.type = type;
        this.userId = userId;
        this.newValue = newValue;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getType() {
        return type;
    }

    public long getUserId() {
        return userId;
    }

    public long getNewValue() {
        return newValue;
    }
}
