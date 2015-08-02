package edu.wpi.cs.wpisuitetng.modules.defecttracker.toolbar;

import java.awt.event.KeyEvent;

import javax.swing.Action;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import edu.wpi.cs.wpisuitetng.modules.defecttracker.search.views.SearchDefectsView;
import edu.wpi.cs.wpisuitetng.modules.defecttracker.tabs.MainTabController;
import edu.wpi.cs.wpisuitetng.modules.defecttracker.tabs.Tab;

public class TestSearchDefectsAction {
    MainTabController mockMainTabController;

    SearchDefectsAction searchDefectsAction;

    @Before
    public void setup() {
        mockMainTabController = mock(MainTabController.class);

        searchDefectsAction = new SearchDefectsAction(mockMainTabController);
    }

    @Test
    public void testSearchDefectsAction() {
        assertNotNull(searchDefectsAction);

        assertEquals(mockMainTabController, searchDefectsAction.getController());
        assertEquals(KeyEvent.VK_D, searchDefectsAction.getValue(Action.MNEMONIC_KEY));
    }

    @Test
    public void testActionPerformed() {
        Tab mockTab = mock(Tab.class);
        SearchDefectsView mockSearchDefectsView = mock(SearchDefectsView.class);
        searchDefectsAction = spy(searchDefectsAction);

        when(mockMainTabController.addTab()).thenReturn(mockTab);
        doReturn(mockSearchDefectsView).when(searchDefectsAction).createSearchDefectsView(mockMainTabController, mockTab);

        searchDefectsAction.actionPerformed(null);//arg is ignored

        verify(mockMainTabController, times(1)).addTab();
        verify(mockTab, times(1)).setComponent(mockSearchDefectsView);
        verify(mockSearchDefectsView, times(1)).requestFocus();
    }
}
