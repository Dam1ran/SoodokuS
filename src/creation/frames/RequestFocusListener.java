package creation.frames;

import javax.swing.*;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

class RequestFocusListener implements AncestorListener {

    private boolean removeListener;

    RequestFocusListener()
    {
        this(true);
    }

    private RequestFocusListener(boolean removeListener)
    {
        this.removeListener = removeListener;
    }

    @Override
    public void ancestorAdded(AncestorEvent e)
    {
        JComponent component = e.getComponent();
        component.requestFocusInWindow();

        if (removeListener)
            component.removeAncestorListener( this );
    }

    @Override
    public void ancestorMoved(AncestorEvent e) {}
    @Override
    public void ancestorRemoved(AncestorEvent e) {}

}