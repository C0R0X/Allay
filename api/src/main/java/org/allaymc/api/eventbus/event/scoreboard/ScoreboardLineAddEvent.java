package org.allaymc.api.eventbus.event.scoreboard;

import org.allaymc.api.eventbus.event.CancellableEvent;
import org.allaymc.api.scoreboard.Scoreboard;
import org.allaymc.api.scoreboard.ScoreboardLine;

/**
 * @author daoge_cmd
 */
public class ScoreboardLineAddEvent extends ScoreboardLineEvent implements CancellableEvent {
    public ScoreboardLineAddEvent(Scoreboard scoreboard, ScoreboardLine line) {
        super(scoreboard, line);
    }
}
