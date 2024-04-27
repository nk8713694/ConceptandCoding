package LLDCricbuzz.Inning;


import java.util.ArrayList;
import java.util.List;

public class BallDetails {

    public int ballNumber;
    public LowLevelDesign.LLDCricbuzz.Inning.BallType ballType;
    public LowLevelDesign.LLDCricbuzz.Inning.RunType runType;
    public LowLevelDesign.LLDCricbuzz.Team.Player.PlayerDetails playedBy;
    public LowLevelDesign.LLDCricbuzz.Team.Player.PlayerDetails bowledBy;
    public LowLevelDesign.LLDCricbuzz.Team.Wicket wicket;
    List<LowLevelDesign.LLDCricbuzz.ScoreUpdater.ScoreUpdaterObserver> scoreUpdaterObserverList = new ArrayList<>();

    public BallDetails(int ballNumber) {
        this.ballNumber = ballNumber;
        scoreUpdaterObserverList.add(new BowlingScoreUpdater());
        scoreUpdaterObserverList.add(new BattingScoreUpdater());
    }

    public void startBallDelivery(Team battingTeam, Team bowlingTeam, OverDetails over) {

        playedBy = battingTeam.getStriker();
        this.bowledBy = over.bowledBy;
        //THROW BALL AND GET THE BALL TYPE, assuming here that ball type is always NORMAL
        ballType = BallType.NORMAL;

        //wicket or no wicket
        if (isWicketTaken()) {
            runType = LowLevelDesign.LLDCricbuzz.Inning.RunType.ZERO;
            //considering only BOLD
            wicket = new LowLevelDesign.LLDCricbuzz.Team.Wicket(LowLevelDesign.LLDCricbuzz.Team.WicketType.BOLD, bowlingTeam.getCurrentBowler(), over,this);
            //making only striker out for now
            battingTeam.setStriker(null);
        } else {
            runType = getRunType();

            if(runType == RunType.ONE || runType == LowLevelDesign.LLDCricbuzz.Inning.RunType.THREE) {
                //swap striket and non striker
                PlayerDetails temp = battingTeam.getStriker();
                battingTeam.setStriker(battingTeam.getNonStriker());
                battingTeam.setNonStriker(temp);
            }
        }

        //update player scoreboard
        notifyUpdaters(this);
    }

    private void notifyUpdaters(BallDetails ballDetails){

        for(ScoreUpdaterObserver observer : scoreUpdaterObserverList) {
            observer.update(ballDetails);
        }
    }

    private RunType getRunType() {

        double val = Math.random();
        if (val <= 0.2) {
            return RunType.ONE;
        } else if (val >= 0.3 && val <= 0.5) {
            return RunType.TWO;
        } else if (val >= 0.6 && val <= 0.8) {
            return RunType.FOUR;
        } else {
            return RunType.SIX;
        }
    }

    private boolean isWicketTaken() {
        //random function return value between 0 and 1
        if (Math.random() < 0.2) {
            return true;
        } else {
            return false;
        }
    }

}
