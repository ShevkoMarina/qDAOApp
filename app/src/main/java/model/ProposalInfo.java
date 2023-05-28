package model;

public class ProposalInfo {
    private long id;
    private String name;
    private String description;
    private long votesFor;
    private long votesAgainst;
    private short state;

    public ProposalInfo(
            long id,
            String name,
            String description,
            long votesFor,
            long votesAgainst,
            short state) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.votesFor = votesFor;
        this.votesAgainst = votesAgainst;
        this.state = state;
    }

    public ProposalInfo(
            long id,
            String name,
            String description,
            long votesFor,
            long votesAgainst) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.votesFor = votesFor;
        this.votesAgainst = votesAgainst;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public long getVotesFor() {
        return votesFor;
    }

    public long getVotesAgainst() {
        return votesAgainst;
    }

    public String getState(){
        switch (state){
            case 0: return "Создано";
            case 1: return "Актитвно";
            case 2: return "Отменено";
            case 3: return "Отвергнуто";
            case 4: return "Нет кворума";
            case 5: return "Принято";
            case 6: return "В очереди";
            case 7: return "Выполнено";
            default: return "Неизвестно";

        }
    }

    public short getStateNumber() {
        return state;
    }
}
