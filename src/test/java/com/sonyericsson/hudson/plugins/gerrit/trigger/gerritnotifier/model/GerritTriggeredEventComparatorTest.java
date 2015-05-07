package com.sonyericsson.hudson.plugins.gerrit.trigger.gerritnotifier.model;

import com.sonyericsson.hudson.plugins.gerrit.trigger.mock.Setup;
import com.sonymobile.tools.gerrit.gerritevents.dto.events.ChangeMerged;
import com.sonymobile.tools.gerrit.gerritevents.dto.events.GerritTriggeredEvent;
import com.sonymobile.tools.gerrit.gerritevents.dto.events.PatchsetCreated;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.Comparator;

import static org.junit.Assert.*;

/**
 * Tests {@link com.sonyericsson.hudson.plugins.gerrit.trigger.gerritnotifier.model.BuildMemory.GerritTriggeredEventComparator}.
 *
 * @author Robert Sandell &lt;rsandell@cloudbees.com&gt;.
 */
@RunWith(Parameterized.class)
public class GerritTriggeredEventComparatorTest {

    private final GerritTriggeredEvent o1;
    private final GerritTriggeredEvent o2;
    private final int expected;
    private final Comparator<GerritTriggeredEvent> comparator;

    @Parameterized.Parameters(name= "{index}: {0}, {1} == {2}")
    public static Iterable<Object[]> permutations() {
        PatchsetCreated event = Setup.createPatchsetCreated();
        ChangeMerged merged = Setup.createChangeMerged();
        return Arrays.asList(new Object[][]{
                {null, null, 0},
                {null, event, -1},
                {event, null, 1},
                {event, event, 0},
                {event, merged, new Integer(event.hashCode()).compareTo(merged.hashCode())},
                {merged, event, new Integer(merged.hashCode()).compareTo(event.hashCode())}
        });
    }

    public GerritTriggeredEventComparatorTest(GerritTriggeredEvent o1, GerritTriggeredEvent o2, int expected) {
        this.o1 = o1;
        this.o2 = o2;
        this.expected = expected;
        comparator = new BuildMemory.GerritTriggeredEventComparator();
    }

    @Test
    public void testCompare() throws Exception {
        assertEquals(expected, comparator.compare(o1, o2));
    }
}