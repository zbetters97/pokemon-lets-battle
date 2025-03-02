package event;

import java.awt.*;
import java.io.Serial;

public class EventRect extends Rectangle {
    @Serial
    private static final long serialVersionUID = -5792031433632402979L;
    protected int eventRectDefaultX, eventRectDefaultY;
    protected int eventRectDefaultWidth, eventRectDefaultHeight;
    protected boolean eventDone = false;
}