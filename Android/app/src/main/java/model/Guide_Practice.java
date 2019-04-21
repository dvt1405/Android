package model;

import java.util.List;

public class Guide_Practice {
    private Practice practice;
    private Guide guide;

    public Guide_Practice(Practice practice, Guide guide) {
        this.practice = practice;
        this.guide = guide;
    }

    public Practice getPractice() {
        return practice;
    }

    public void setPractice(Practice practice) {
        this.practice = practice;
    }

    public Guide getGuide() {
        return guide;
    }

    public void setGuide(Guide guide) {
        this.guide = guide;
    }
}
