package edu.wpi.cs.wpisuitetng.modules.defecttracker.toolbar;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyBoolean;

import java.awt.Component;

import javax.swing.event.ChangeEvent;

import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import edu.wpi.cs.wpisuitetng.janeway.gui.container.toolbar.DefaultToolbarView;
import edu.wpi.cs.wpisuitetng.janeway.gui.container.toolbar.ToolbarGroupView;
import edu.wpi.cs.wpisuitetng.modules.defecttracker.defect.DefectView;
import edu.wpi.cs.wpisuitetng.modules.defecttracker.tabs.MainTabController;
import edu.wpi.cs.wpisuitetng.modules.defecttracker.tabs.MainTabView;

public class TestToolbarController {

    DefaultToolbarView mockDefaultToolbarView;
    MainTabController mockMainTabController;

    ToolbarController toolbarController;

    @Before
    public void setup() {
        mockDefaultToolbarView = mock(DefaultToolbarView.class);
        mockMainTabController = mock(MainTabController.class);

        toolbarController = new ToolbarController(mockDefaultToolbarView, mockMainTabController);
    }

    @Test
    public void testToolbarController() {
        assertNotNull(toolbarController);

        verify(mockMainTabController, times(1)).addChangeListener(toolbarController);
    }

    @Test
    public void testStateChanged() {
        ChangeEvent mockChangeEvent = mock(ChangeEvent.class);
        MainTabView mockMainTabView = mock(MainTabView.class);
        DefectView mockDefectView = mock(DefectView.class);
        ToolbarGroupView mockToolbarGroupView = mock(ToolbarGroupView.class);

        when(mockChangeEvent.getSource()).thenReturn(mockMainTabView);
        when(mockMainTabView.getSelectedComponent()).thenReturn(mockDefectView);
        when(mockDefectView.getGroup()).thenReturn(mockToolbarGroupView);

        toolbarController = spy(toolbarController);
        doNothing().when(toolbarController).setRelevantTabGroup(any(ToolbarGroupView.class));

        toolbarController.stateChanged(mockChangeEvent);

        verify(mockChangeEvent, times(2)).getSource();
        verify(mockMainTabView, times(1)).getSelectedComponent();
        verify(mockDefectView, times(1)).getGroup();
        verify(toolbarController, times(1)).setRelevantTabGroup(mockToolbarGroupView);
    }

    @Test
    public void testStateChanged_notIToolbarGroupProvider() {
        ChangeEvent mockChangeEvent = mock(ChangeEvent.class);
        MainTabView mockMainTabView = mock(MainTabView.class);
        Component mockComponent = mock(Component.class);

        when(mockChangeEvent.getSource()).thenReturn(null);
        when(mockChangeEvent.getSource()).thenReturn(mockMainTabView);
        when(mockMainTabView.getSelectedComponent()).thenReturn(mockComponent);

        toolbarController = spy(toolbarController);
        doNothing().when(toolbarController).setRelevantTabGroup(any(ToolbarGroupView.class));

        toolbarController.stateChanged(mockChangeEvent);

        verify(mockChangeEvent, times(2)).getSource();
        verify(mockMainTabView, times(1)).getSelectedComponent();
        verify(toolbarController, times(1)).setRelevantTabGroup(null);
    }

    @Test
    public void testSetRelevantTabGroup() {
        ToolbarGroupView mockToolbarGroupView = mock(ToolbarGroupView.class);

        toolbarController = spy(toolbarController);
        doNothing().when(toolbarController).setRelevant(any(ToolbarGroupView.class), anyBoolean());

        toolbarController.setRelevantTabGroup(mockToolbarGroupView);

        verify(toolbarController).setRelevant(mockToolbarGroupView, true);
        assertEquals(mockToolbarGroupView, toolbarController.getRelevantTabGroup());
    }

    @Test
    public void testSetRelevantTabGroup_existingRelevantGroup() {
        ToolbarGroupView mockToolbarGroupView = mock(ToolbarGroupView.class);
        ToolbarGroupView mockToolbarGroupView2 = mock(ToolbarGroupView.class);

        toolbarController = spy(toolbarController);
        doNothing().when(toolbarController).setRelevant(any(ToolbarGroupView.class), anyBoolean());

        toolbarController.setRelevantTabGroup(mockToolbarGroupView);

        assertEquals(mockToolbarGroupView, toolbarController.getRelevantTabGroup());
        verify(toolbarController).setRelevant(mockToolbarGroupView, true);

        toolbarController.setRelevantTabGroup(mockToolbarGroupView2);

        assertEquals(mockToolbarGroupView2, toolbarController.getRelevantTabGroup());
        verify(toolbarController).setRelevant(mockToolbarGroupView, false);
        verify(toolbarController).setRelevant(mockToolbarGroupView2, true);
    }

}
