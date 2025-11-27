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
        Ticket t2 = new Ticket("A", "B", 100, 10,12);

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
        Ticket t1 = new Ticket("MOW", "SPB", 4000, 9,18);
        Ticket t2 = new Ticket("MVD", "SPB", 8000, 12, 17 );

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
        Ticket ticket = new Ticket("MOW", "SPB", 5000,10,15);

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

         assertEquals(2, found.length); assertEquals(5000, found[0].getPrice());
        assertEquals(3000, found[1].getPrice());
    }

    @Test
    public void testSearchSortingDefault() {
        AviaSouls manager = new AviaSouls();
        Ticket ticket1 = new Ticket("MOW", "SPB", 5000, 10, 18);
        Ticket ticket2 = new Ticket("MOW", "SPB", 3000, 10,20);

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


}
