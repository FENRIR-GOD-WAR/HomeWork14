import java.util.Comparator;

public class TicketTimeComparator implements Comparator<Ticket> {

    @Override
    public int compare(Ticket o1, Ticket o2) {
        int timeFirst = o1.getTimeTo() - o1.getTimeFrom();
        int timeSecond = o2.getTimeTo() - o1.getTimeFrom();
        return timeFirst - timeSecond;
    }
}


