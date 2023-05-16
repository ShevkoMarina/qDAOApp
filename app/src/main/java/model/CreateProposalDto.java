package model;

public class CreateProposalDto {

    private final String name;
    private final String description;
    private final int type;
    private final int userId;
    private final int newValue;

    public CreateProposalDto(
            String name,
            String description,
            int type,
            int userId,
            int newValue) {
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

    public int getUserId() {
        return userId;
    }

    public int getNewValue() {
        return newValue;
    }
}
