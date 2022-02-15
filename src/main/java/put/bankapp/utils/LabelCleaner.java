package put.bankapp.utils;

import javafx.animation.PauseTransition;
import javafx.scene.control.Label;
import javafx.util.Duration;

public class LabelCleaner {
    private final int seconds;
    private final Label label;

    public LabelCleaner(int seconds, Label label) {
        this.seconds = seconds;
        this.label = label;
    }

    public void startCountdown() {
        PauseTransition pause = new PauseTransition(Duration.millis(1000 * seconds));
        pause.setOnFinished(e -> {
            label.setText("");
        });
        pause.play();
    }
}
