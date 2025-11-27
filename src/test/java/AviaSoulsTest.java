import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.*;

public class AviaSoulsTest {

    @Test
    public void testCompareTo_LowerPrice() {
        Ticket t1 = new Ticket("A", "B", 100, 10, 12);
        Ticket t2 = new Ticket("A", "B", 200, 10, 12);

        assertTrue(t1.compareTo(t2) < 0);
    }

    @Test
    public void testCompareTo_EqualPrice() {
        Ticket t1 = new Ticket("A", "B", 150, 10, 12);
        Ticket t2 = new Ticket("A", "B", 150, 10, 12);

        assertEquals(0, t1.compareTo(t2));
    }

    @Test
    public void testCompareTo_HigherPrice() {
        Ticket t1 = new Ticket("A", "B", 300, 10, 12);
        Ticket t2 = new Ticket("A", "B", 100, 10, 12);

        assertTrue(t1.compareTo(t2) > 0);
    }

    @Test
    public void testSearchShortByPrice() {
        AviaSouls manager = new AviaSouls();

        Ticket t1 = new Ticket("MOW", "SPB", 3000, 10, 12);
        Ticket t2 = new Ticket("MOW", "SPB", 2000, 11, 13);
        Ticket t3 = new Ticket("MOW", "SPB", 4000, 12, 14);

        manager.add(t1);
        manager.add(t2);
        manager.add(t3);

        Ticket[] result = manager.search("MOW", "SPB");

        assertArrayEquals(new Ticket[]{t2, t1, t3}, result);
    }

    @Test
    public void testSearchNoMatchers() {
        AviaSouls manager = new AviaSouls();

        manager.add(new Ticket("MOW", "SPB", 1000, 10, 12));

        Ticket[] result = manager.search("NY", "LA");

        assertEquals(0, result.length);
    }

    @Test
    public void testTimeComparator() {
        Ticket t1 = new Ticket("A", "B", 100, 10, 20);
        Ticket t2 = new Ticket("A", "B", 100, 10, 15);

        Comparator<Ticket> comp = new TicketTimeComparator();

        assertTrue(comp.compare(t1, t2) > 0);
        assertTrue(comp.compare(t2, t1) < 0);
        assertEquals(0, comp.compare(t1, new Ticket("A", "B", 100, 10, 20)));
    }

    @Test
    public void testSearchAndSortByTime() {
        AviaSouls manager = new AviaSouls();

        Ticket t1 = new Ticket("MOW", "SPB", 2000, 10, 20);
        Ticket t2 = new Ticket("MOW", "SPB", 2000, 10, 15);
        Ticket t3 = new Ticket("MOW", "SPB", 2000, 10, 25);

        manager.add(t1);
        manager.add(t2);
        manager.add(t3);

        TicketTimeComparator comp = new TicketTimeComparator();
        Ticket[] result = manager.searchAndSortBy("MOW", "SPB", comp);

        assertArrayEquals(new Ticket[]{t2, t1, t3}, result);
    }

    @Test
    public void testFindAllEmpty() {
        AviaSouls manager = new AviaSouls();
        assertEquals(0, manager.findAll().length);
    }

    @Test
    public void testFindAllWithOneTicket() {
        AviaSouls manager = new AviaSouls();
        Ticket t = new Ticket("MOW", "SPB", 5000, 11, 24);

        manager.add(t);

        assertEquals(1, manager.findAll().length);
    }

    @Test
    public void testFindAllWithMultipleTicket() {
        AviaSouls manager = new AviaSouls();
        Ticket t1 = new Ticket("MOW", "SPB", 4000, 9, 18);
        Ticket t2 = new Ticket("MVD", "SPB", 8000, 12, 17);

        manager.add(t1);
        manager.add(t2);

        assertEquals(2, manager.findAll().length);

        assertTrue(Arrays.asList(manager.findAll()).contains(t1));

        assertTrue(Arrays.asList(manager.findAll()).contains(t2));
    }

    @Test
    public void testAddOneTicket() {
        AviaSouls manager = new AviaSouls();
        Ticket t = new Ticket("MOW", "SPB", 5000, 8, 10);

        manager.add(t);

        assertEquals(1, manager.findAll().length);
        assertTrue(Arrays.asList(manager.findAll()).contains(t));
    }

    @Test
    public void testAddTwoTickets() {
        AviaSouls manager = new AviaSouls();
        Ticket t1 = new Ticket("MOW", "SPB", 5000, 10, 20);
        Ticket t2 = new Ticket("KUF", "SVX", 8000, 10, 18);

        manager.add(t1);
        manager.add(t2);

        assertEquals(2, manager.findAll().length);
        assertTrue(Arrays.asList(manager.findAll()).contains(t1));
        assertTrue(Arrays.asList(manager.findAll()).contains(t2));
    }

    @Test
    public void testDuplicateTicket() {
        AviaSouls manager = new AviaSouls();
        Ticket ticket = new Ticket("MOW", "SPB", 5000, 10, 15);

        manager.add(ticket);
        manager.add(ticket);

        assertEquals(2, manager.findAll().length);
    }

    @Test
    public void testSearchAndSortByCustomComparator() {
        AviaSouls manager = new AviaSouls();
        Ticket t1 = new Ticket("MOW", "SPB", 5000, 10, 15);
        Ticket t2 = new Ticket("MOW", "SPB", 3000, 10, 13);


        manager.add(t1);
        manager.add(t2);


        Comparator<Ticket> customComparator = Comparator.comparingInt(Ticket::getPrice).reversed();

        Ticket[] found = manager.searchAndSortBy("MOW", "SPB", customComparator);

        assertEquals(2, found.length);
        assertEquals(5000, found[0].getPrice());
        assertEquals(3000, found[1].getPrice());
    }

    @Test
    public void testSearchSortingDefault() {
        AviaSouls manager = new AviaSouls();
        Ticket ticket1 = new Ticket("MOW", "SPB", 5000, 10, 18);
        Ticket ticket2 = new Ticket("MOW", "SPB", 3000, 10, 20);

        manager.add(ticket1);
        manager.add(ticket2);

        Ticket[] found = manager.search("MOW", "SPB");

        assertEquals(2, found.length);
        assertEquals(3000, found[0].getPrice());
        assertEquals(5000, found[1].getPrice());
    }

    @Test
    public void testSearchInvalidRoute() {
        AviaSouls manager = new AviaSouls();
        Ticket t1 = new Ticket("MOW", "SPB", 5000, 10, 14);
        Ticket t2 = new Ticket("SAM", "EKB", 8000, 10, 18);

        manager.add(t1);
        manager.add(t2);

        Ticket[] found = manager.search("NOV", "VLD");
        assertEquals(0, found.length);
    }


    @Test
    public void testSearchEmptyResult() {
        AviaSouls manager = new AviaSouls();

        manager.add(new Ticket("A", "B", 100, 10, 12));

        Ticket[] expected = {};
        Ticket[] actual = manager.search("X", "Y");

        Assertions.assertArrayEquals(expected, actual);
    }

    @Test
    public void testSearchAndSortByComparator() {
        AviaSouls manager = new AviaSouls();
        Comparator<Ticket> comparator = new TicketTimeComparator();

        Ticket t1 = new Ticket("A", "B", 100, 10, 20); // duration 10
        Ticket t2 = new Ticket("A", "B", 100, 5, 8);   // duration 3
        Ticket t3 = new Ticket("A", "B", 100, 15, 25); // duration 10
        Ticket t4 = new Ticket("C", "D", 100, 1, 2);

        manager.add(t1);
        manager.add(t2);
        manager.add(t3);
        manager.add(t4);

        Ticket[] expected = {t2, t1, t3}; // сортировка по длительности
        Ticket[] actual = manager.searchAndSortBy("A", "B", comparator);

        Assertions.assertArrayEquals(expected, actual);
    }

    @Test
    public void testSearchAndSortByEmpty() {
        AviaSouls manager = new AviaSouls();
        Comparator<Ticket> comparator = new TicketTimeComparator();

        manager.add(new Ticket("X", "Y", 100, 10, 12));

        Ticket[] expected = {};
        Ticket[] actual = manager.searchAndSortBy("A", "B", comparator);

        Assertions.assertArrayEquals(expected, actual);
    }


    @Test
    public void testAddToArrayWhenEmpty() {
        AviaSouls manager = new AviaSouls();
        Ticket t = new Ticket("A", "B", 100, 10, 12);

        manager.add(t);

        Assertions.assertArrayEquals(new Ticket[]{t}, manager.findAll());
    }

    @Test
    public void testAddAndFindAll() {
        AviaSouls manager = new AviaSouls();

        Ticket t1 = new Ticket("A", "B", 100, 10, 12);
        Ticket t2 = new Ticket("C", "D", 200, 11, 14);

        manager.add(t1);
        manager.add(t2);

        Ticket[] expected = {t1, t2};
        Ticket[] actual = manager.findAll();

        Assertions.assertArrayEquals(expected, actual);
    }

    @Test
    public void testAddToArrayInternalMethod() {
        AviaSouls manager = new AviaSouls();

        Ticket t1 = new Ticket("A", "B", 100, 10, 12);
        Ticket t2 = new Ticket("C", "D", 200, 11, 13);

        manager.add(t1);
        manager.add(t2);

        Ticket[] expected = {t1, t2};
        Assertions.assertArrayEquals(expected, manager.findAll());
    }

    // -----------------------------------------------------------
    //  Test: search()
    // -----------------------------------------------------------

    @Test
    public void testSearchWhenManagerEmpty() {
        AviaSouls manager = new AviaSouls();
        Assertions.assertArrayEquals(new Ticket[0], manager.search("A", "B"));
    }

    @Test
    public void testSearchOnlyFromMatches() {
        AviaSouls manager = new AviaSouls();
        manager.add(new Ticket("A", "X", 100, 10, 12));
        Assertions.assertArrayEquals(new Ticket[0], manager.search("A", "B"));
    }

    @Test
    public void testSearchOnlyToMatches() {
        AviaSouls manager = new AviaSouls();
        manager.add(new Ticket("X", "B", 100, 10, 12));
        Assertions.assertArrayEquals(new Ticket[0], manager.search("A", "B"));
    }

    @Test
    public void testSearchSingleResult() {
        AviaSouls manager = new AviaSouls();
        Ticket t = new Ticket("A", "B", 100, 10, 12);
        manager.add(t);
        Assertions.assertArrayEquals(new Ticket[]{t}, manager.search("A", "B"));
    }

    @Test
    public void testSearchFoundAndSortedByPrice() {
        AviaSouls manager = new AviaSouls();

        Ticket t1 = new Ticket("A", "B", 300, 10, 12);
        Ticket t2 = new Ticket("A", "B", 100, 9, 11);
        Ticket t3 = new Ticket("A", "B", 200, 15, 17);
        Ticket t4 = new Ticket("X", "Y", 500, 8, 10);

        manager.add(t1);
        manager.add(t2);
        manager.add(t3);
        manager.add(t4);

        Ticket[] expected = {t2, t3, t1};
        Assertions.assertArrayEquals(expected, manager.search("A", "B"));
    }


    @Test
    public void testCompareToEquals() {
        Ticket t1 = new Ticket("A", "B", 100, 10, 12);
        Ticket t2 = new Ticket("A", "B", 100, 11, 13);
        Assertions.assertEquals(0, t1.compareTo(t2));
    }

    @Test
    public void testCompareToLess() {
        Ticket t1 = new Ticket("A", "B", 100, 10, 12);
        Ticket t2 = new Ticket("A", "B", 200, 10, 12);
        Assertions.assertTrue(t1.compareTo(t2) < 0);
    }

    @Test
    public void testCompareToGreater() {
        Ticket t1 = new Ticket("A", "B", 300, 10, 12);
        Ticket t2 = new Ticket("A", "B", 100, 10, 12);
        Assertions.assertTrue(t1.compareTo(t2) > 0);
    }


    @Test
    public void testSearchAndSortBySingle() {
        AviaSouls manager = new AviaSouls();
        Ticket t = new Ticket("A", "B", 100, 10, 12);
        manager.add(t);

        Ticket[] result = manager.searchAndSortBy("A", "B", new TicketTimeComparator());
        Assertions.assertArrayEquals(new Ticket[]{t}, result);
    }


    @Test
    public void testComparatorEqualDurations() {
        AviaSouls manager = new AviaSouls();
        TicketTimeComparator comparator = new TicketTimeComparator();

        Ticket t1 = new Ticket("A", "B", 100, 10, 20); // duration 10
        Ticket t2 = new Ticket("A", "B", 200, 15, 25); // duration 10

        manager.add(t1);
        manager.add(t2);

        Ticket[] expected = {t1, t2};
        Ticket[] actual = manager.searchAndSortBy("A", "B", comparator);

        Assertions.assertArrayEquals(expected, actual);
    }

    @Test
    public void testIfConditionFalseTrue() {
        AviaSouls manager = new AviaSouls();

        manager.add(new Ticket("X", "B", 100, 10, 12));

        Ticket[] actual = manager.search("A", "B");

        Assertions.assertEquals(0, actual.length);
    }

    @Test
    public void testIfConditionTrueFalse() {
        AviaSouls manager = new AviaSouls();

        manager.add(new Ticket("A", "X", 100, 10, 12));

        Ticket[] actual = manager.search("A", "B");

        Assertions.assertEquals(0, actual.length);
    }

    @Test
    public void testIfConditionFalseFalse() {
        AviaSouls manager = new AviaSouls();

        manager.add(new Ticket("X", "Y", 100, 10, 12));

        Ticket[] actual = manager.search("A", "B");

        Assertions.assertEquals(0, actual.length);
    }

    @Test
    public void testAndSortIfTrueTrue() {
        AviaSouls manager = new AviaSouls();

        Ticket t = new Ticket("A", "B", 100, 10, 12);
        manager.add(t);

        Ticket[] result = manager.searchAndSortBy("A", "B", new TicketTimeComparator());

        Assertions.assertArrayEquals(new Ticket[]{t}, result);
    }

    @Test
    public void testAndSortIfFalseTrue() {
        AviaSouls manager = new AviaSouls();

        manager.add(new Ticket("X", "B", 100, 10, 12));

        Ticket[] result = manager.searchAndSortBy("A", "B", new TicketTimeComparator());

        Assertions.assertEquals(0, result.length);
    }

    @Test
    public void testAndSortIfTrueFalse() {
        AviaSouls manager = new AviaSouls();

        manager.add(new Ticket("A", "X", 100, 10, 12));

        Ticket[] result = manager.searchAndSortBy("A", "B", new TicketTimeComparator());

        Assertions.assertEquals(0, result.length);
    }

    @Test
    public void testAndSortIfFalseFalse() {
        AviaSouls manager = new AviaSouls();

        manager.add(new Ticket("X", "Y", 100, 10, 12));

        Ticket[] result = manager.searchAndSortBy("A", "B", new TicketTimeComparator());

        Assertions.assertEquals(0, result.length);
    }


}
