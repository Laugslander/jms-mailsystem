package nl.mailsystem.server.ui.listener;

import nl.mailsystem.common.domain.Correspondence;

/**
 * @author Robin Laugs
 */
public interface InternalCorrespondenceEventListener {

    void onInternalCorrespondenceEvent(Correspondence correspondence);

}
