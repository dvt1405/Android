package model;

public class Practice_PrGroup {
    private Practice practice;
    private PracticeGroup practiceGroup;

    public Practice_PrGroup(Practice practice, PracticeGroup practiceGroup) {
        this.practice = practice;
        this.practiceGroup = practiceGroup;
    }

    public Practice getPractice() {
        return practice;
    }

    public void setPractice(Practice practice) {
        this.practice = practice;
    }

    public PracticeGroup getPracticeGroup() {
        return practiceGroup;
    }

    public void setPracticeGroup(PracticeGroup practiceGroup) {
        this.practiceGroup = practiceGroup;
    }
}
