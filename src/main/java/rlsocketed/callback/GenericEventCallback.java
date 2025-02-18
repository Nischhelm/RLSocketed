package rlsocketed.callback;

import socketed.common.socket.gem.effect.activatable.callback.IEffectCallback;
import net.minecraftforge.fml.common.eventhandler.Event;

public class GenericEventCallback implements IEffectCallback {
    public Event event;
    public GenericEventCallback(Event event) {
        this.event = event;
    }
}
