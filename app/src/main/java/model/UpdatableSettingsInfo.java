package model;

public class UpdatableSettingsInfo {

    private long quorum;
    private long votingPeriod;
    private long votingDelay;

    public UpdatableSettingsInfo(long quorum, long votingPeriod, long votingDelay) {
        this.quorum = quorum;
        this.votingPeriod = votingPeriod;
        this.votingDelay = votingDelay;
    }

    public long getQuorum() {
        return quorum;
    }

    public long getVotingPeriod() {
        return votingPeriod;
    }

    public long getVotingDelay() {
        return votingDelay;
    }
}
