import java.sql.Timestamp;
import java.util.Comparator;
import java.util.PriorityQueue;

import org.junit.Test;

import cn.com.spdb.uds.db.bean.UdsJobBean;

public class testDispatcher {
	private static PriorityQueue<UdsJobBean> priQueue = new PriorityQueue<UdsJobBean>(150,
			new Comparator<UdsJobBean>() {
				@Override
				public int compare(UdsJobBean o1, UdsJobBean o2) {
					if (o1.getPriority() == o2.getPriority()) {
						if (o1.getDispatcher_time() != null && o2.getDispatcher_time() != null) {
							if (o1.getDispatcher_time().equals(o2.getDispatcher_time())) {
								return o1.getJob().compareTo(o2.getJob());
							}
							return o1.getDispatcher_time().compareTo(o2.getDispatcher_time());
						}
					}
					return o2.getPriority() - o1.getPriority();
				}
			});

	@Test
	public void test() {
		UdsJobBean A = new UdsJobBean();
		A.setJob("A");
		A.setDispatcher_time(new Timestamp(1000));
		A.setPriority((short) 10);
		UdsJobBean B = new UdsJobBean();
		B.setJob("B");
		B.setDispatcher_time(new Timestamp(1000));
		B.setPriority((short) 15);
		UdsJobBean C = new UdsJobBean();
		C.setJob("C");
		C.setDispatcher_time(new Timestamp(999));
		C.setPriority((short) 10);
		UdsJobBean D = new UdsJobBean();
		D.setJob("D");
		D.setDispatcher_time(new Timestamp(1000));
		D.setPriority((short) 10);
		priQueue.offer(D);
		priQueue.offer(A);
		priQueue.offer(B);
		priQueue.offer(C);
		System.out.println(priQueue.peek());
		System.out.println(priQueue.poll());
		System.out.println(priQueue.peek());
		System.out.println(priQueue.poll());
		System.out.println(priQueue.peek());
		System.out.println(priQueue.poll());
		System.out.println(priQueue.peek());
		System.out.println(priQueue.poll());
	}

}
